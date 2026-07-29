package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.SetIota

object OpListToSet : ConstMediaAction {
    override val argc = 1
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val iotas = args.getList(0, argc).toMutableList()
        return SetIota(iotas, -1).asActionResult()
    }
}
