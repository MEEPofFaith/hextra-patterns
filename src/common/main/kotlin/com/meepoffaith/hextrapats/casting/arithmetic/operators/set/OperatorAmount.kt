package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorAmount : OperatorBasic(1, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val set = iotas.toList().getSet(0, arity)
        return set.operate(
            { d -> d.size.asActionResult },
            { v -> v.size.asActionResult },
            { e -> e.size.asActionResult }
        )
    }
}
