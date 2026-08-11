package com.meepoffaith.hextrapats.casting.actions.stack

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import kotlin.math.abs
import kotlin.math.roundToInt

object OpDuplicateAt : Action{
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val depth = let {
            val x = stack.last()
            stack.removeLast()
            val maxIdx = stack.size - 1
            if (x is DoubleIota) {
                val double = x.double
                val rounded = double.roundToInt()
                if (abs(double - rounded) <= DoubleIota.TOLERANCE && rounded in -maxIdx..maxIdx) {
                    return@let rounded
                }
            }
            throw MishapInvalidIota.of(x, 0, "int.between", -maxIdx, maxIdx)
        }

        if(depth >= 0){
            val index = stack.size - depth - 1
            stack.add(index, stack[index])
        }else{
            stack.add(-depth, stack[-depth])
        }

        val image2 = image.withUsedOp().copy(stack = stack)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }
}
