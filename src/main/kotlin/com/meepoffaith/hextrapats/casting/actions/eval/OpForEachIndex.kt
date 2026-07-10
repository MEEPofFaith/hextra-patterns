package com.meepoffaith.hextrapats.casting.actions.eval

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import com.meepoffaith.hextrapats.casting.eval.vm.FrameForEachIndex

//1:1 copy of OpForEach but it calls FrameForEachIndex instead
object OpForEachIndex : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val instrs = stack.getList(stack.lastIndex - 1, stack.size)
        val datums = stack.getList(stack.lastIndex, stack.size)
        stack.removeLastOrNull()
        stack.removeLastOrNull()

        val frame = FrameForEachIndex(datums, instrs)
        val image2 = image.withUsedOp().copy(stack = stack)

        return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
    }
}
