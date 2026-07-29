package com.meepoffaith.hextrapats.casting.arithmetic.operators.bool

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextPositiveIntUnderInclusive
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import com.meepoffaith.hextrapats.util.HextraUtils
import com.meepoffaith.hextrapats.util.MultiPreds

class OperatorOutRange : OperatorBasic(4, MultiPreds.all(DOUBLE)) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val num = it.nextDouble()
        val a = it.nextDouble()
        val b = it.nextDouble()
        val op = it.nextPositiveIntUnderInclusive(3, 4)
        val min = a.coerceAtMost(b)
        val max = a.coerceAtLeast(b)
        return outRange(min, max, num, op).asActionResult
    }

    companion object{
        fun outRange(min: Double, max: Double, num: Double, op: Int) : Boolean = when(op){
            0 -> num !in min..max
            1 -> HextraUtils.lessEq(num, min) || max < num
            2 -> num < min || HextraUtils.lessEq(max, num)
            3 -> HextraUtils.lessEq(num, min) || HextraUtils.lessEq(max, num)
            else -> throw IllegalStateException("How the fuck")
        }
    }
}
