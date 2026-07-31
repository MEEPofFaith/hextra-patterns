package com.meepoffaith.hextrapats.casting.actions.vecmath

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.util.math.Vec3d
import kotlin.math.asin
import kotlin.math.atan2


object OpToPolar : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val v: Vec3d = args.getVec3(0, argc).normalize()

        val yaw = -atan2(v.x, v.z)
        val pitch = asin(v.y)

        return listOf(DoubleIota(pitch), DoubleIota(yaw))
    }
}
