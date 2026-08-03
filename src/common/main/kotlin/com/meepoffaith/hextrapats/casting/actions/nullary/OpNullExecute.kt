package com.meepoffaith.hextrapats.casting.actions.nullary

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getEvaluatable
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.casting.actions.eval.OpEval
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

object OpNullExecute : Action {
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        var stack = image.stack
        if(stack.size < 2) throw MishapNotEnoughArgs(2, stack.size)

        val instrs = stack.getEvaluatable(stack.lastIndex, stack.size)
        val iota = stack[stack.lastIndex - 1]
        stack = stack.init()

        if(iota is NullIota){
            return OpEval.exec(env, image, continuation, stack.init(), instrs)
        }else{
            val image2 = image.withUsedOp().copy(stack = stack)
            return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE.get())
        }
    }
}
