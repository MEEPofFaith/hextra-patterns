package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.getEntity
import at.petrak.hexcasting.api.casting.getVec3
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.meepoffaith.hextrapats.casting.iota.VecSetIota
import com.meepoffaith.hextrapats.util.HextraUtils.asActionResult
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.MultiPreds.SET_OP
import net.minecraft.entity.Entity

class OperatorRemove(
    val returnBool: Boolean
) : OperatorBasic(2, SET_OP) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet: DoubleSet ->
                val num = stack.getDouble(1, arity)
                val added = dSet.remove(num)
                if(returnBool) listOf(DoubleSetIota(dSet), BooleanIota(added)) else dSet.asActionResult()
            },
            { vSet: VecSet ->
                val vec = stack.getVec3(1, arity)
                val added = vSet.remove(vec)
                if(returnBool) listOf(VecSetIota(vSet), BooleanIota(added)) else vSet.asActionResult()
            },
            { eSet: MutableSet<Entity> ->
                val entity = stack.getEntity(1, arity)
                val added = eSet.remove(entity)
                if(returnBool) listOf(EntitySetIota(eSet), BooleanIota(added)) else eSet.asActionResult()
            }
        )
    }
}
