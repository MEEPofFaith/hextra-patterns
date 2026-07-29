package com.meepoffaith.hextrapats.casting.actions.sets

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.casting.iota.IotaMap

object OpEmptySet : ConstMediaAction {
    override val argc = 0
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        return IotaMap().asActionResult()
    }
}
