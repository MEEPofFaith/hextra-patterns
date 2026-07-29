package com.meepoffaith.hextrapats.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.getList
import at.petrak.hexcasting.api.utils.serializeToNBT
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.server.world.ServerWorld

data class FrameMainForEach(
    val data: SpellList,
    val code: SpellList,
    val index: Int
) : ContinuationFrame {
    constructor(data: SpellList, code: SpellList, withIndex: Boolean) : this(data, code, if(withIndex) 0 else -1)

    override fun breakDownwards(stack: List<Iota>): Pair<Boolean, List<Iota>> {
        return Pair(true, stack)
    }

    /** Step the Thoth computation, enqueueing one code evaluation. */
    override fun evaluate(
        continuation: SpellContinuation,
        level: ServerWorld,
        harness: CastingVM
    ): CastResult {
        val stack = harness.image.stack.toList() //Always use the current stack.

        // If we still have data to process...
        val tStack = stack.toMutableList()
        val (newImage, newCont) = if (data.nonEmpty) {
            // push the next datum to the top of the stack,
            val cont2 = continuation
                // put the next Thoth object back on the stack for the next Thoth cycle,
                .pushFrame(FrameMainForEach(data.cdr, code, if(index == -1) -1 else (index + 1)))
                // and prep the Thoth'd code block for evaluation.
                .pushFrame(FrameEvaluate(code, true))
            tStack.add(data.car)
            if(index != -1) tStack.add(DoubleIota(index.toDouble()))
            Pair(harness.image.withUsedOp(), cont2)
        } else {
            // Else, dump our final list onto the stack.
            Pair(harness.image, continuation)
        }

        return CastResult(
            ListIota(code),
            newCont,
            // reset escapes so they don't carry over to other iterations or out of thoth
            newImage.withResetEscape().copy(stack = tStack),
            listOf(),
            ResolvedPatternType.EVALUATED,
            HexEvalSounds.THOTH,
        )
    }

    override fun serializeToNBT(): NbtCompound {
        val tag = NbtCompound()
        tag.put("data", data.serializeToNBT())
        tag.put("code", code.serializeToNBT())
        tag.putInt("index", index)
        return tag
    }

    override fun size() = data.size() + code.size()

    override val type: ContinuationFrame.Type<*> = TYPE

    companion object {
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameMainForEach> = object : ContinuationFrame.Type<FrameMainForEach> {
            override fun deserializeFromNBT(tag: NbtCompound, world: ServerWorld): FrameMainForEach {
                return FrameMainForEach(
                    HexIotaTypes.LIST.deserialize(tag.getList("data", NbtElement.COMPOUND_TYPE), world)!!.list,
                    HexIotaTypes.LIST.deserialize(tag.getList("code", NbtElement.COMPOUND_TYPE), world)!!.list,
                    tag.getInt("index")
                )
            }

        }
    }
}
