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

class OperatorInRange : OperatorBasic(4, MultiPreds.all(DOUBLE.get())) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val num = it.nextDouble(arity)
        val a = it.nextDouble(arity)
        val b = it.nextDouble(arity)
        val op = it.nextPositiveIntUnderInclusive(3, arity)
        val min = a.coerceAtMost(b)
        val max = a.coerceAtLeast(b)
        return inRange(min, max, num, op).asActionResult
    }

    companion object{
        fun inRange(min: Double, max: Double, num: Double, op: Int) : Boolean = when(op){
            0 -> min < num && num < max
            1 -> HextraUtils.lessEq(min, num) && num < max
            2 -> min < num && HextraUtils.lessEq(num, max)
            3 -> HextraUtils.lessEq(min, num) && HextraUtils.lessEq(num, max)
            else -> throw IllegalStateException("How the fuck")
        }
    }
}
