package com.meepoffaith.hextrapats.casting.eval.vm

import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.server.level.ServerLevel

data class FrameStackForEach(
    val data: TreeList<Iota>,
    val code: TreeList<Iota>,
    val index: Int
) : ContinuationFrame {
    constructor(data: TreeList<Iota>, code: TreeList<Iota>, withIndex: Boolean) : this(data, code, if(withIndex) 0 else -1)

    override fun breakDownwards(stack: TreeList<Iota>): Pair<Boolean, TreeList<Iota>> {
        return Pair(true, stack)
    }

    override fun evaluate(
        continuation: SpellContinuation,
        level: ServerLevel,
        harness: CastingVM
    ): CastResult {
        // If we still have data to process...
        val (newStack, newImage, newCont) = if(!data.isEmpty()){
            // Push the next datum onto the stack
            var stack = harness.image.stack.appended(data.head()) // Always use main stack
            if(index >= 0) stack = stack.appended(DoubleIota(index.toDouble()))
            val cont2 = continuation
                // Place the next cycle onto the stack
                .pushFrame(FrameStackForEach(data.tail(), code, if(index == -1) -1 else (index + 1)))
                // Prep the code to be evaluated
                .pushFrame(FrameEvaluate(code, true))
            Triple(stack, harness.image.withUsedOp(), cont2)
        }else{
            Triple(harness.image.stack, harness.image, continuation)
        }
        return CastResult(
            ListIota(code),
            newCont,
            // reset escapes so they don't carry over to other iterations or out of thoth
            newImage.withResetEscape().copy(stack = newStack),
            listOf(),
            ResolvedPatternType.EVALUATED,
            HexEvalSounds.THOTH.get(),
        )
    }

    override fun size(): Int = data.size + code.size

    override val type: ContinuationFrame.Type<*> = TYPE

    companion object{
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameStackForEach> = object : ContinuationFrame.Type<FrameStackForEach> {
            val CODEC = RecordCodecBuilder.mapCodec<FrameStackForEach> { inst ->
                inst.group(
                    TreeList.codecOf(IotaType.TYPED_CODEC).fieldOf("data").forGetter { it.data },
                    TreeList.codecOf(IotaType.TYPED_CODEC).fieldOf("code").forGetter { it.code },
                    Codec.INT.fieldOf("index").forGetter { it.index }
                ).apply(inst) { a, b, c ->
                    FrameStackForEach(a, b, c)
                }
            }
            val STREAM_CODEC = StreamCodec.composite(
                IotaType.TYPED_STREAM_CODEC.apply(TreeList.streamCodecOp()), FrameStackForEach::data,
                IotaType.TYPED_STREAM_CODEC.apply(TreeList.streamCodecOp()), FrameStackForEach::code,
                ByteBufCodecs.INT, FrameStackForEach::index
            ) { a, b, c ->
                FrameStackForEach(a, b, c)
            }

            override fun codec(): MapCodec<FrameStackForEach> =
                CODEC

            override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, FrameStackForEach> =
                STREAM_CODEC
        }
    }
}
