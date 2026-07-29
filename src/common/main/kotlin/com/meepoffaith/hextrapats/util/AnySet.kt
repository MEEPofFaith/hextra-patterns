package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import com.meepoffaith.hextrapats.casting.iota.*
import net.minecraft.entity.Entity
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import java.util.UUID
import java.util.function.Function

class AnySet{
    var doubleSetIota: DoubleSetIota? = null
    var vecSetIota: VecSetIota? = null
    var entitySetIota: EntitySetIota? = null

    constructor(iota: Iota, reversedIdx: Int){
        when (iota) {
            is DoubleSetIota -> doubleSetIota = iota
            is VecSetIota -> vecSetIota = iota
            is EntitySetIota -> entitySetIota = iota
            else -> throw MishapInvalidIota.ofType(iota, reversedIdx, "set")
        }
    }

    fun operate(
        doubles: Function<DoubleSet, List<Iota>>,
        vecs: Function<VecSet, List<Iota>>,
        entites: Function<EntityMap, List<Iota>>
    ) : List<Iota> {
        return if(doubleSetIota != null){
            doubles.apply(doubleSetIota!!.copySet())
        }else if(vecSetIota != null){
            vecs.apply(vecSetIota!!.copySet())
        }else if(entitySetIota != null){
            entites.apply(entitySetIota!!.copyMap())
        }else{
            throw IllegalStateException("wtf did you do to get here")
        }
    }
}
