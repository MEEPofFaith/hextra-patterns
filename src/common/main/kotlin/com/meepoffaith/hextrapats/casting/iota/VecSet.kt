package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.MathUtils
import net.minecraft.util.math.Vec3d
import net.minecraft.world.phys.Vec3

class VecSet : HashSet<Vec3> {
    constructor(): super()
    constructor(set: VecSet) : super(set)

    override fun add(e: Vec3): Boolean {
        return super.add(MathUtils.roundToTolerance(e))
    }

    override fun contains(o: Vec3): Boolean {
        return super.contains(MathUtils.roundToTolerance(o))
    }

    override fun remove(o: Vec3): Boolean {
        return super.remove(MathUtils.roundToTolerance(o))
    }

    fun asActionResult(): List<Iota> = listOf(VecSetIota(this))
}
