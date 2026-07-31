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
        val set = it.nextSet(arity).copy()
        val iota = it.next().value
        val removed = set.remove(iota)
        return if(returnBool) listOf(SetIota(set), BooleanIota(removed)) else set.asActionResult()
    }
}
