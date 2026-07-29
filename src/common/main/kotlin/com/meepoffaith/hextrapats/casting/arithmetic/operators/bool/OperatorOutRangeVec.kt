package com.meepoffaith.hextrapats.casting.arithmetic.operators.bool

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextPositiveIntUnderInclusive
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3
import com.meepoffaith.hextrapats.util.MultiPreds

object OperatorOutRangeVec : OperatorBasic(4, MultiPreds.quad(VEC3.get(), DOUBLE.get(), DOUBLE.get(), DOUBLE.get())) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val num = downcast(it.next().value, VEC3.get()).vec3.length()
        val a = it.nextDouble(arity)
        val b = it.nextDouble(arity)
        val op = it.nextPositiveIntUnderInclusive(3, arity)
        val min = a.coerceAtMost(b)
        val max = a.coerceAtLeast(b)
        return OperatorOutRange.outRange(min, max, num, op).asActionResult
    }
}
