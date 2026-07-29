package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.HextraUtils.nextSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorAddSets : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val map = it.nextSet(arity).copy()
        map.putAll(it.nextSet(arity))
        return map.asActionResult()
    }
}
