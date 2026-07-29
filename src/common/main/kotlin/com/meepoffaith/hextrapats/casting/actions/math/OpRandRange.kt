package com.meepoffaith.hextrapats.casting.actions.math

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota

object OpRandRange : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val from = args.getDouble(0, argc)
        val to = args.getDouble(1, argc)

        return (from + env.world.random.nextDouble() * (to - from)).asActionResult
    }
}
