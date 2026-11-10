package com.meepoffaith.hextrapats.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.api.utils.getList
import at.petrak.hexcasting.api.utils.hasList
import at.petrak.hexcasting.api.utils.serializeToNBT
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.server.world.ServerWorld

data class FrameForEachIndex(
    val data: SpellList,
    val code: SpellList,
    val baseStack: List<Iota>?,
    val index: Int,
    val immutableAcc: TreeList<Iota>
) : ContinuationFrame {
    constructor(data: SpellList, code: SpellList) : this(data, code, null, 0, TreeList.empty())

    //Directly copied from FrameForEach, except for the extra bit of logic to include the index

    /** When halting, we add the stack state at halt to the stack accumulator, then return the original pre-Thoth stack, plus the accumulator. */
    override fun breakDownwards(stack: List<Iota>): Pair<Boolean, List<Iota>> {
        val newStack = baseStack?.toMutableList() ?: mutableListOf()
        newStack.add(ListIota(immutableAcc.appendedAll(stack)))
        return true to newStack
    }

    /** Step the Thoth computation, enqueueing one code evaluation. */
    override fun evaluate(
        continuation: SpellContinuation,
        level: ServerWorld,
        harness: CastingVM
    ): CastResult {
        // If this is the very first Thoth step (i.e. no Thoth computations run yet)...
        val (stack, newAcc) = if (baseStack == null) {
            // init stack to the harness stack...
            harness.image.stack.toList() to immutableAcc
        } else {
            // else save the stack to the accumulator and reuse the saved base stack.
            baseStack to immutableAcc.appendedAll(harness.image.stack)
        }

        // If we still have data to process...
        val (stackTop, newImage, newCont) = if (data.nonEmpty) {
            // push the next datum to the top of the stack,
            val cont2 = continuation
                // put the next Thoth object back on the stack for the next Thoth cycle,
                .pushFrame(FrameForEachIndex(data.cdr, code, stack, index + 1, newAcc))
                // and prep the Thoth'd code block for evaluation.
                .pushFrame(FrameEvaluate(code, true))
            Triple(data.car, harness.image, cont2)
        } else {
            // Else, dump our final list onto the stack.
            Triple(ListIota(newAcc), harness.image, continuation)
        }
        val tStack = stack.toMutableList()
        tStack.add(stackTop)
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

    /*
    override fun serializeToNBT() = NBTBuilder {
        "data" %= data.serializeToNBT()
        "code" %= code.serializeToNBT()
        if (baseStack != null)
            "base" %= baseStack.serializeToNBT()
        "index" %= index
        "accumulator" %= acc.serializeToNBT()
    }
     */

    override fun serializeToNBT(): NbtCompound {
        val tag = NbtCompound()
        tag.put("data", data.serializeToNBT())
        tag.put("code", code.serializeToNBT())
        if(baseStack != null)
            tag.put("base", baseStack.serializeToNBT())
        tag.putInt("index", index)
        tag.put("accumulator", immutableAcc.serializeToNBT())
        return tag
    }

    override fun size() = data.size() + code.size() + immutableAcc.size + (baseStack?.size ?: 0)

    override val type: ContinuationFrame.Type<*> = TYPE

    companion object {
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameForEachIndex> = object : ContinuationFrame.Type<FrameForEachIndex> {
            override fun deserializeFromNBT(tag: NbtCompound, world: ServerWorld): FrameForEachIndex {
                return FrameForEachIndex(
                    HexIotaTypes.LIST.deserialize(tag.getList("data", NbtElement.COMPOUND_TYPE), world)!!.list,
                    HexIotaTypes.LIST.deserialize(tag.getList("code", NbtElement.COMPOUND_TYPE), world)!!.list,
                    if (tag.hasList("base", NbtElement.COMPOUND_TYPE))
                        HexIotaTypes.LIST.deserialize(tag.getList("base", NbtElement.COMPOUND_TYPE), world)!!.list.toList()
                    else
                        null,
                    tag.getInt("index"),
                    TreeList.from(HexIotaTypes.LIST.deserialize(
                        tag.getList("accumulator", NbtElement.COMPOUND_TYPE),
                        world
                    )!!.list)
                )
            }

        }
    }
}
