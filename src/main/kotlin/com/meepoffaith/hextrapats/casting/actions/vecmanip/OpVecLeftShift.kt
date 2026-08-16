package com.meepoffaith.hextrapats.casting.actions.vecmanip

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.util.math.Vec3d

object OpVecLeftShift : ConstMediaAction{
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val vec = args.toMutableList().getVec3(0)
        return Vec3d(vec.y, vec.z, vec.x).asActionResult
    }
}
