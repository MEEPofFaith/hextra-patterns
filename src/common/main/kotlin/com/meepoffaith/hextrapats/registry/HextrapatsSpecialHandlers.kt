package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.casting.handlers.ScientificExponent
import com.meepoffaith.hextrapats.casting.handlers.Vector1
import com.meepoffaith.hextrapats.casting.handlers.VectorX
import com.meepoffaith.hextrapats.casting.handlers.VectorY
import com.meepoffaith.hextrapats.casting.handlers.VectorZ

object HextrapatsSpecialHandlers : HextrapatsRegistrar<SpecialHandler.Factory<*>>(
    HexRegistries.SPECIAL_HANDLER,
    { IXplatAbstractions.INSTANCE.specialHandlerRegistry }
) {
    val VEC_X = make("vec_x", VectorX.Factory())
    val VEC_Y = make("vec_y", VectorY.Factory())
    val VEC_Z = make("vec_z", VectorZ.Factory())
    val VEC_1 = make("vec_1", Vector1.Factory())
    val SCI_EXP = make("scientific_exp", ScientificExponent.Factory())

    private fun make(name: String, handler: SpecialHandler.Factory<*>): SpecialHandler.Factory<*> {
        register(name) { handler }
        return handler
    }
}
