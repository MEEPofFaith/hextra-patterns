package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.MathUtils.roundToTolerance

class DoubleSet : HashSet<Double> {
    constructor(): super()
    constructor(set: DoubleSet) : super(set)

    override fun add(e: Double): Boolean {
        return super.add(e.roundToTolerance())
    }

    override fun contains(o: Double): Boolean {
        return super.contains(o.roundToTolerance())
    }

    override fun remove(o: Double): Boolean {
        return super.remove(o.roundToTolerance())
    }

    fun asActionResult() : List<Iota> = listOf(DoubleSetIota(this))
}
