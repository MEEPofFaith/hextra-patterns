package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.EntityMap
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.meepoffaith.hextrapats.util.HextraUtils.getEntitySet
import com.meepoffaith.hextrapats.util.HextraUtils.getNumSet
import com.meepoffaith.hextrapats.util.HextraUtils.getSet
import com.meepoffaith.hextrapats.util.HextraUtils.getVecSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorDisjunction : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val stack = iotas.toList()
        val set = stack.getSet(0, arity)
        return set.operate(
            { dSet ->
                val set2 = stack.getNumSet(1, arity)
                val disjunction = DoubleSet()
                for(key in dSet){
                    if(!set2.contains(key)){
                        disjunction.add(key)
                    }else{
                        set2.remove(key)
                    }
                }
                disjunction.addAll(set2).asActionResult
            },
            { vSet ->
                val set2 = stack.getVecSet(1, arity)
                val disjunction = VecSet()
                for(key in vSet){
                    if(!set2.contains(key)){
                        disjunction.add(key)
                    }else{
                        set2.remove(key)
                    }
                }
                disjunction.addAll(set2).asActionResult
            },
            { eSet ->
                val set2 = stack.getEntitySet(1, arity)
                val disjunction = EntityMap()
                for(entry in eSet){
                    if(!set2.containsKey(entry.key)){
                        disjunction[entry.key] = entry.value
                    }else{
                        set2.remove(entry.key)
                    }
                }
                disjunction.putAll(set2)
                disjunction.asActionResult()
            }
        )
    }
}
