package com.meepoffaith.hextrapats.casting.arithmetic.operators.set

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.IotaMap
import com.meepoffaith.hextrapats.util.HextraUtils.nextSet
import com.meepoffaith.hextrapats.util.MultiPreds.ALL_SETS

object OperatorIntersection : OperatorBasic(2, ALL_SETS) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val map1 = it.nextSet(arity)
        val map2 = it.nextSet(arity)
        val intersection = IotaMap()
        for(entry in map1){
            if(map2.containsKey(entry.key)){
                intersection[entry.key] = entry.value
            }
        }
        return intersection.asActionResult()
    }
}
