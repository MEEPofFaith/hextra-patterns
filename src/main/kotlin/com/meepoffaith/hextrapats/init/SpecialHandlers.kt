package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.handlers.*
import net.minecraft.registry.Registry

object SpecialHandlers {
    val VEC_X = registerSpecialHandler("vec_x", VectorX.Factory())
    val VEC_Y = registerSpecialHandler("vec_y", VectorY.Factory())
    val VEC_Z = registerSpecialHandler("vec_z", VectorZ.Factory())
    val VEC_1 = registerSpecialHandler("vec_1", Vector1.Factory())
    val SCI_EXP = registerSpecialHandler("scientific_exp", ScientificExponent.Factory())
    val DUPLICATE_AT = registerSpecialHandler("duplicate_at", DuplicateAt.Factory())

    fun init(){}

    private fun registerSpecialHandler(name: String, handler: SpecialHandler.Factory<*>) : SpecialHandler.Factory<*>{
        Registry.register(IXplatAbstractions.INSTANCE.specialHandlerRegistry, HextraPats.modLoc(name), handler)
        return handler
    }
}
