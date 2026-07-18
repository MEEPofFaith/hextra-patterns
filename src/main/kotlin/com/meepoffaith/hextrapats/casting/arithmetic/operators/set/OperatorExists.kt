package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.*
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.MultiPreds.SET_OP
import net.minecraft.entity.Entity

object OperatorExists : OperatorBasic(2, SET_OP) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet: DoubleSet ->
                val num = stack.getDouble(1, arity)
                listOf(DoubleSetIota(dSet), BooleanIota(dSet.contains(num)))
            },
            { vSet: VecSet ->
                val vec = stack.getVec3(1, arity)
                listOf(VecSetIota(vSet), BooleanIota(vSet.contains(vec)))
            },
            { eSet: MutableSet<Entity> ->
                val entity = stack.getEntity(1, arity)
                listOf(EntitySetIota(eSet), BooleanIota(eSet.contains(entity)))
            }
        )
    }
}
