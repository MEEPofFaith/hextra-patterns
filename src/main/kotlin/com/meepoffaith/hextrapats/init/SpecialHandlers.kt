package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.handlers.*
import net.minecraft.registry.Registry

object SpecialHandlers {
    val VEC_X = registerSpecialHandler("vec_x", SpecialHandlerVectorX.Factory())
    val VEC_Y = registerSpecialHandler("vec_y", SpecialHandlerVectorY.Factory())
    val VEC_Z = registerSpecialHandler("vec_z", SpecialHandlerVectorZ.Factory())
    val VEC_1 = registerSpecialHandler("vec_1", SpecialHandlerVector1.Factory())
    val SCI_EXP = registerSpecialHandler("scientific_exp", SpecialHandlerScientificExponent.Factory())
    val DUPLICATE_AT = registerSpecialHandler("duplicate_at", SpecialHandlerDuplicateAt.Factory())
    val VEC_SWIZZLE = registerSpecialHandler("vec_swizzle", SpecialHandlerVecSwizzling.Factory())

    fun init(){}

    private fun registerSpecialHandler(name: String, handler: SpecialHandler.Factory<*>) : SpecialHandler.Factory<*>{
        Registry.register(IXplatAbstractions.INSTANCE.specialHandlerRegistry, HextraPats.modLoc(name), handler)
        return handler
    }
}
