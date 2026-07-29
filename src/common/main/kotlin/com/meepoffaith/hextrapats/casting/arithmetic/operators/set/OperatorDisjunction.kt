package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.IotaMap
import com.meepoffaith.hextrapats.util.HextraUtils.nextSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorDisjunction : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val map1 = it.nextSet(arity)
        val map2 = it.nextSet(arity).copy()
        val disjunction = IotaMap()
        for(entry in map1){
            if(!map2.containsKey(entry.key)){
                disjunction[entry.key] = entry.value
            }else{
                map2.remove(entry.key)
            }
        }
        disjunction.putAll(map2)
        return disjunction.asActionResult()
    }
}
