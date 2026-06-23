package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota

class OpPeek (val peekEnd: Boolean) : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val datum = if(peekEnd) list.last() else list.first()
        return listOf(ListIota(list), datum)
    }
}
