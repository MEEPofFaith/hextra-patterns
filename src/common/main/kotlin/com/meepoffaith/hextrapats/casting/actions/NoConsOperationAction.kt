package com.meepoffaith.hextrapats.casting.actions

import at.petrak.hexcasting.api.casting.arithmetic.engine.NoOperatorCandidatesException
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidOperatorArgs
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexArithmetics
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

class NoConsOperationAction(val pattern: HexPattern, val argc: Int) : Action {
    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val oldStack = image.stack.toMutableList()
        val size = oldStack.size
        if(oldStack.size < argc){
            throw MishapNotEnoughArgs(argc, size)
        }

        try{
            val result = HexArithmetics.getEngine().run(pattern, env, image, continuation)
            val resultStack = result.newImage.stack
            oldStack.addAll(resultStack.subList(size - argc, resultStack.size))
            return OperationResult(image.copy(stack = oldStack), result.sideEffects, continuation, HexEvalSounds.NORMAL_EXECUTE)
        } catch (e: NoOperatorCandidatesException) {
            throw MishapInvalidOperatorArgs(e.args)
        }
    }
}
