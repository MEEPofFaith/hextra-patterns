package com.meepoffaith.hextrapats.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.*
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3
import com.meepoffaith.hextrapats.casting.arithmetic.operators.bool.OperatorInRangeVec
import com.meepoffaith.hextrapats.casting.arithmetic.operators.bool.OperatorOutRangeVec
import com.meepoffaith.hextrapats.registry.HextrapatsActions.IN_RANGE
import com.meepoffaith.hextrapats.registry.HextrapatsActions.LEN_EQ
import com.meepoffaith.hextrapats.registry.HextrapatsActions.LEN_NEQ
import com.meepoffaith.hextrapats.registry.HextrapatsActions.OUT_RANGE
import com.meepoffaith.hextrapats.util.HextraUtils
import com.meepoffaith.hextrapats.util.MultiPreds
import net.minecraft.util.math.Vec3d
import net.minecraft.world.phys.Vec3
import java.util.function.BiFunction

class Vec3BoolArithmetic : Arithmetic {
    private val OPS = listOf(
        GREATER,
        LESS,
        GREATER_EQ,
        LESS_EQ,
        LEN_EQ,
        LEN_NEQ,
        IN_RANGE,
        OUT_RANGE
    )

    override fun arithName() = "vec3_bool_math"

    override fun opTypes() = OPS

    override fun getOperator(pattern: HexPattern): Operator = when(pattern){
        GREATER -> makeComp{ a, b -> a.length() > b.length() }
        LESS -> makeComp{ a, b -> a.length() < b.length() }
        GREATER_EQ -> makeComp { a, b -> HextraUtils.greaterEq(a.length(), b.length()) }
        LESS_EQ -> makeComp { a, b -> HextraUtils.lessEq(a.length(), b.length()) }
        LEN_EQ -> makeComp{ a, b -> DoubleIota.tolerates(a.length(), b.length()) }
        LEN_NEQ -> makeComp{ a, b -> !DoubleIota.tolerates(a.length(), b.length()) }
        IN_RANGE -> OperatorInRangeVec
        OUT_RANGE -> OperatorOutRangeVec
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")
    }

    fun makeComp(op: BiFunction<Vec3, Vec3, Boolean>) = OperatorBinary(MultiPreds.all(VEC3.get()))
        { i: Iota, j: Iota -> BooleanIota(op.apply(Operator.downcast(i, VEC3.get()).vec3, Operator.downcast(j, VEC3.get()).vec3)) }
}
