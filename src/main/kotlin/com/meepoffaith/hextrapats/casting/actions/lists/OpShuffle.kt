package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota

object OpShuffle : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val newList = mutableListOf<Iota>()

        while(!list.isEmpty()){
            newList.add(list.removeAt(env.world.random.nextBetweenExclusive(0, list.size)))
        }

        return newList.asActionResult
    }
}
