package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.*
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import com.meepoffaith.hextrapats.casting.iota.*
import net.minecraft.entity.Entity


object OpListToSet : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val iotas = args.getList(0, argc).toMutableList()

        if(iotas.isEmpty()) throw MishapInvalidIota.of(ListIota(iotas), 0, "hextrapats:set_list")
        val out = if(iotas[0] is DoubleIota){
            toNumSet(iotas)
        }else if(iotas[0] is Vec3Iota){
            toVecSet(iotas)
        }else if(iotas[0] is EntityIota){
            toEntSet(iotas)
        }else{
            throw MishapInvalidIota.of(ListIota(iotas), 0, "hextrapats:set_list")
        }

        return listOf(out)
    }

    fun toNumSet(iotas: MutableList<Iota>): DoubleSetIota {
        val set = DoubleSet()
        for(i in iotas.indices){
            val iota = iotas[i]
            if(iota is DoubleIota){
                set.add(iota.double)
            }else{
                throw MishapInvalidIota.of(ListIota(iotas), 0, "hextrapats:set_list")
            }
        }
        return DoubleSetIota(set)
    }

    fun toVecSet(iotas: MutableList<Iota>): VecSetIota {
        val set = VecSet()
        for(i in iotas.indices){
            val iota = iotas[i]
            if(iota is Vec3Iota){
                set.add(iota.vec3)
            }else{
                throw MishapInvalidIota.of(ListIota(iotas), 0, "hextrapats:set_list")
            }
        }
        return VecSetIota(set)
    }

    fun toEntSet(iotas: MutableList<Iota>): EntitySetIota {
        val set = HashSet<Entity>()
        for (i in iotas.indices) {
            val iota = iotas[i]
            if (iota is EntityIota) {
                set.add(iota.entity)
            } else {
                throw MishapInvalidIota.of(ListIota(iotas), 0, "hextrapats:set_list")
            }
        }
        return EntitySetIota(set)
    }
}
