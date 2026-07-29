package com.meepoffaith.hextrapats.casting.actions.logic

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota

class OpNoConsEquality(val invert: Boolean) : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val left = args[0]
        val right = args[1]

        return listOf(left, right, BooleanIota(Iota.tolerates(left, right) != invert))
    }
}
