package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota

class OpPeek (val peekEnd: Boolean) : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc)
        if(list.isNotEmpty()){
            val datum = if (peekEnd) list.last() else list.first()
            return listOf(args[0], datum)
        }
        return listOf(args[0], NullIota())
    }
}
