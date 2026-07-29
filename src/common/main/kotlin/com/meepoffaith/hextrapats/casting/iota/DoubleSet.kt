package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.MathUtils

class DoubleSet : HashSet<Double> {
    constructor(): super()
    constructor(set: DoubleSet) : super(set)

    override fun add(e: Double): Boolean {
        return super.add(MathUtils.roundToTolerance(e))
    }

    override fun contains(o: Double): Boolean {
        return super.contains(MathUtils.roundToTolerance(o))
    }

    override fun remove(o: Double): Boolean {
        return super.remove(MathUtils.roundToTolerance(o))
    }

    fun asActionResult() : List<Iota> = listOf(DoubleSetIota(this))
}
