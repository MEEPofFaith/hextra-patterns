package com.meepoffaith.hextrapats.casting.arithmetic.operators.vec

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3
import com.meepoffaith.hextrapats.util.MultiPreds

object OperatorApproachVec : OperatorBasic(3, MultiPreds.triple(VEC3, VEC3, DOUBLE)) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator()
        val from = downcast(it.next(), VEC3).vec3
        val to = downcast(it.next(), VEC3).vec3
        val speed = downcast(it.next(), DOUBLE).double

        var step = to.subtract(from)
        val stepLen = step.length()

        if(stepLen < speed) return to.asActionResult

        step = step.multiply(speed / stepLen)
        return from.add(step).asActionResult
    }
}
