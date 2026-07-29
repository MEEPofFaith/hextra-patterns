package com.meepoffaith.hextrapats.casting.actions.vecmath

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import net.minecraft.util.math.Vec3d
import net.minecraft.world.phys.Vec3
import kotlin.math.cos
import kotlin.math.sin

object OpFromPolar : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val pitch: Double = args.getDouble(0, argc)
        val yaw: Double = -args.getDouble(1, argc)
        //Replicates Vec3.fromPolar, but with radian doubles instead of degree floats as input.
        val f = cos(yaw - Math.PI)
        val g = sin(yaw - Math.PI)
        val h = -cos(pitch)
        val i = sin(pitch)
        return Vec3(g * h, i, f * h).asActionResult
    }
}
