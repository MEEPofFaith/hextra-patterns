package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import com.meepoffaith.hextrapats.casting.iota.VecSetIota
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.MultiPreds.SET_OP

class OperatorRemove(
    val returnBool: Boolean
) : OperatorBasic(2, SET_OP) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet ->
                val num = stack.getDouble(1, arity)
                val removed = dSet.remove(num)
                if(returnBool) listOf(DoubleSetIota(dSet), BooleanIota(removed)) else dSet.asActionResult()
            },
            { vSet ->
                val vec = stack.getVec3(1, arity)
                val removed = vSet.remove(vec)
                if(returnBool) listOf(VecSetIota(vSet), BooleanIota(removed)) else vSet.asActionResult()
            },
            { eSet ->
                val entity = stack.getEntity(env.world, 1, arity)
                val removed = eSet.removeEntity(entity)
                if(returnBool) listOf(EntitySetIota(eSet), BooleanIota(removed)) else eSet.asActionResult()
            }
        )
    }
}
