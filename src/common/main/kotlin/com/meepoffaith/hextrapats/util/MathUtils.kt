package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.iota.DoubleIota
import net.minecraft.util.math.Vec3d
import net.minecraft.world.phys.Vec3
import kotlin.math.acos
import kotlin.math.min
import kotlin.math.roundToInt


object MathUtils {
    const val TAU = Math.PI * 2.0

    /** Modulo that works properly for negative numbers. Taken from Anuken/Arc.  */
    fun mod(a: Double, b: Double): Double{
        return ((a % b) + b) % b
    }

    fun angleDist(a: Double, b: Double): Double{
        var a = a
        var b = b
        a = mod(a, TAU)
        b = mod(b, TAU)

        val distBack = if ((a - b) < 0) a - b + TAU else a - b
        val distFwd = if ((b - a) < 0) b - a + TAU else b - a

        return min(distBack, distFwd)
    }

    fun vecAngleDist(a: Vec3, b: Vec3): Double{
        val dot = a.dot(b)
        val len2 = a.length() * b.length()

        return acos(dot / len2)
    }

    fun roundToTolerance(num: Double): Double{
        return (num / DoubleIota.TOLERANCE).roundToInt() * DoubleIota.TOLERANCE
    }

    fun roundToTolerance(v: Vec3): Vec3{
        return Vec3(
            roundToTolerance(v.x),
            roundToTolerance(v.y),
            roundToTolerance(v.z)
        )
    }
}
