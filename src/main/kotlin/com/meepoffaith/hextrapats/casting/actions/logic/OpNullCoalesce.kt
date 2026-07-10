package com.meepoffaith.hextrapats.casting.actions.logic;

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.NullIota

import java.util.*;

class OpNullCoalesce : ConstMediaAction {
    override val argc = 2

    override fun execute(args:List<Iota>, env: CastingEnvironment): List<Iota> {
        if (args[0] is NullIota)
            return listOf(args[1])
        return listOf(args[0])
    }
}
