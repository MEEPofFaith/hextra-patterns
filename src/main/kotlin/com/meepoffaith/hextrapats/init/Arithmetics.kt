package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.common.lib.hex.HexArithmetics
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.arithmetic.*
import net.minecraft.registry.Registry

object Arithmetics {
    fun init(){
        registerArithmetic("bool", BoolArithmetic())
        registerArithmetic("vec3bool", Vec3BoolArithmetic())
        registerArithmetic("nummath", NumArithmetic())
        registerArithmetic("vecmath", Vec3Arithmetic())
        registerArithmetic("setops", SetArithmetic())
    }

    private fun registerArithmetic(name: String, a: Arithmetic){
        Registry.register(HexArithmetics.REGISTRY, HextraPats.modLoc(name), a)
    }
}
