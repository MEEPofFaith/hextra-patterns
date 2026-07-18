package com.meepoffaith.hextrapats.casting.arithmetic.operators.num

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import com.meepoffaith.hextrapats.util.MultiPreds
import kotlin.math.abs

object OperatorApproach : OperatorBasic(3, MultiPreds.all(DOUBLE)) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        var from = it.nextDouble(arity)
        val to = it.nextDouble(arity)
        val speed = it.nextDouble(arity)

        if(abs(from - to) < speed) return to.asActionResult

        if(to < from){
            from -= speed
        }else{
            from += speed
        }

        return from.asActionResult
    }
}
