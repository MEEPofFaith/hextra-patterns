package com.meepoffaith.hextrapats.casting.actions.eval

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getEvaluatable
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import com.meepoffaith.hextrapats.casting.eval.vm.FrameStackForEach

//1:1 copy of OpForEach but it calls FrameMainForEach instead
class OpStackForEach(val withIndex: Boolean) : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val datums = stack.getList(stack.lastIndex - 1, stack.size)
        val instrs = stack.getEvaluatable(stack.lastIndex, stack.size)
        val newStack = stack.slice(0, stack.length() - 2)

        val instrList = instrs.map({ TreeList.from(listOf(it)) }, { it })

        val frame = FrameStackForEach(datums, instrList, withIndex)
        val image2 = image.withUsedOp().copy(stack = newStack)

        return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
    }
}
