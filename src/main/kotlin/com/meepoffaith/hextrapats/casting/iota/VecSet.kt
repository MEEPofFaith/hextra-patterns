package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.MathUtils.roundToTolerance
import net.minecraft.util.math.Vec3d

class VecSet : HashSet<Vec3d> {
    constructor(): super()
    constructor(set: VecSet) : super(set)

    override fun add(e: Vec3d): Boolean {
        return super.add(e.roundToTolerance())
    }

    override fun contains(o: Vec3d): Boolean {
        return super.contains(o.roundToTolerance())
    }

    override fun remove(o: Vec3d): Boolean {
        return super.remove(o.roundToTolerance())
    }

    fun asActionResult(): List<Iota> = listOf(VecSetIota(this))
}
