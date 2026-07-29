package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.HextraUtils.getEntitySet
import com.meepoffaith.hextrapats.util.HextraUtils.getNumSet
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.HextraUtils.getVecSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorAddSets : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet ->
                val set2 = stack.getNumSet(1, arity)
                dSet.addAll(set2)
                dSet.asActionResult()
            },
            { vSet ->
                val set2 = stack.getVecSet(1, arity)
                vSet.addAll(set2)
                vSet.asActionResult()
            },
            { eSet ->
                val set2 = stack.getEntitySet(1, arity)
                eSet.putAll(set2)
                eSet.asActionResult()
            }
        )
    }
}
