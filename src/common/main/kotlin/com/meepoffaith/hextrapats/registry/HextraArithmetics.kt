package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexArithmetics
import com.meepoffaith.hextrapats.casting.arithmetic.*

object HextraArithmetics : HextrapatsRegistrar<Arithmetic>(
    HexRegistries.ARITHMETIC,
    { HexArithmetics.REGISTRY }
) {
    val BOOL = make("bool", BoolArithmetic)
    val VEC3BOOL = make("vec3_bool", Vec3BoolArithmetic)
    val DOUBLE = make("double", NumArithmetic)
    val VEC3 = make("vec3", Vec3Arithmetic)
    val SET = make("set", SetArithmetic)

    private fun make(name: String, arith: Arithmetic) =
        register(name) {arith}
}
