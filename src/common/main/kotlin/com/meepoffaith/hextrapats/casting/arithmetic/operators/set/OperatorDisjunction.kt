package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.IotaSet
import com.meepoffaith.hextrapats.util.HextraUtils.nextSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorDisjunction : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val set1 = it.nextSet(arity)
        val set2 = it.nextSet(arity).copy()
        val disjunction = IotaSet()
        for(iota in set1){
            if(!set2.contains(iota)){
                disjunction.add(iota)
            }else{
                set2.remove(iota)
            }
        }
        disjunction.addAll(set2)
        return disjunction.asActionResult()
    }
}
