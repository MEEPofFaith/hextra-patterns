package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.meepoffaith.hextrapats.util.HextraUtils.asActionResult
import com.meepoffaith.hextrapats.util.HextraUtils.getEntitySet
import com.meepoffaith.hextrapats.util.HextraUtils.getNumSet
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.HextraUtils.getVecSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS
import net.minecraft.entity.Entity

object OperatorIntersection : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet: DoubleSet ->
                val set2 = stack.getNumSet(1, arity)
                val intersection = DoubleSet()
                for(key in dSet){
                    if(set2.contains(key)) intersection.add(key)
                }
                intersection.asActionResult()
            },
            { vSet: VecSet ->
                val set2 = stack.getVecSet(1, arity)
                val intersection = VecSet()
                for(key in vSet){
                    if(set2.contains(key)) intersection.add(key)
                }
                intersection.asActionResult()
            },
            { eSet: MutableSet<Entity> ->
                val set2 = stack.getEntitySet(1, arity)
                val intersection = HashSet<Entity>()
                for(key in eSet){
                    if(set2.contains(key)) intersection.add(key)
                }
                intersection.asActionResult()
            }
        )
    }
}
