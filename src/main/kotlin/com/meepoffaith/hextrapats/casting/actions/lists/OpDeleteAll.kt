package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota

object OpDeleteAll : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val value = args[1]

        var count = 0.0
        var i = 0
        while(i < list.size){
            if(Iota.tolerates(list[i], value)){
                list.removeAt(i)
                count++
            }else{
                i++
            }
        }

        return listOf(ListIota(list), DoubleIota(count))
    }
}
