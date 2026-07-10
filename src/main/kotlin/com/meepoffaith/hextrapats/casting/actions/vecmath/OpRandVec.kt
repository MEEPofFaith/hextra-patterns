package com.meepoffaith.hextrapats.casting.actions.vecmath

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.util.math.Vec3d

object OpRandVec : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val rand = env.world.random
        return Vec3d(rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian()).normalize().asActionResult
    }
}
