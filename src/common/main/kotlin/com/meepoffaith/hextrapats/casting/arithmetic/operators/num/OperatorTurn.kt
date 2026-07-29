package com.meepoffaith.hextrapats.casting.arithmetic.operators.num

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import com.meepoffaith.hextrapats.util.MathUtils
import com.meepoffaith.hextrapats.util.MultiPreds
import kotlin.math.abs

object OperatorTurn : OperatorBasic(3, MultiPreds.all(DOUBLE)) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        var from = it.nextDouble(arity)
        var to = it.nextDouble(arity)
        val speed = it.nextDouble(arity)

        if(MathUtils.angleDist(from, to) < speed) return to.asActionResult

        from = MathUtils.mod(from, MathUtils.TAU)
        to = MathUtils.mod(to, MathUtils.TAU)

        val fwdDist = abs(from - to)
        val backDst = MathUtils.TAU - fwdDist

        if (from > to == backDst > fwdDist) {
            from -= speed
        } else {
            from += speed
        }

        return from.asActionResult
    }
}
