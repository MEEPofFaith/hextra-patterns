package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.SetIota
import com.meepoffaith.hextrapats.util.HextraUtils.nextSet
import com.meepoffaith.hextrapats.util.MultiPreds.SET_OP

class OperatorRemove(
    val returnBool: Boolean
) : OperatorBasic(2, SET_OP) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val map = it.nextSet(arity).copy()
        val iota = it.next().value
        val removed = map.removeIota(iota)
        return if(returnBool) listOf(SetIota(map), BooleanIota(removed)) else map.asActionResult()
    }
}
