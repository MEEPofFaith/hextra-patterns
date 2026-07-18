package com.meepoffaith.hextrapats.casting.arithmetic

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorUnary
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3
import com.meepoffaith.hextrapats.casting.arithmetic.operators.vec.OperatorApproachVec
import com.meepoffaith.hextrapats.casting.arithmetic.operators.vec.OperatorTurnVec
import com.meepoffaith.hextrapats.init.Patterns.ANGLE_APPROACH
import com.meepoffaith.hextrapats.init.Patterns.ANGLE_DIST
import com.meepoffaith.hextrapats.init.Patterns.APPROACH
import com.meepoffaith.hextrapats.init.Patterns.CONSTRUCT_ABOUT_X
import com.meepoffaith.hextrapats.init.Patterns.CONSTRUCT_ABOUT_Y
import com.meepoffaith.hextrapats.init.Patterns.CONSTRUCT_ABOUT_Z
import com.meepoffaith.hextrapats.init.Patterns.DECREMENT
import com.meepoffaith.hextrapats.init.Patterns.INCREMENT
import com.meepoffaith.hextrapats.init.Patterns.INVERT
import com.meepoffaith.hextrapats.init.Patterns.NORMALIZE
import com.meepoffaith.hextrapats.init.Patterns.ROT_ABOUT_X
import com.meepoffaith.hextrapats.init.Patterns.ROT_ABOUT_Y
import com.meepoffaith.hextrapats.init.Patterns.ROT_ABOUT_Z
import com.meepoffaith.hextrapats.init.Patterns.VEC_GET_X
import com.meepoffaith.hextrapats.init.Patterns.VEC_GET_Y
import com.meepoffaith.hextrapats.init.Patterns.VEC_GET_Z
import com.meepoffaith.hextrapats.init.Patterns.VEC_SET_X
import com.meepoffaith.hextrapats.init.Patterns.VEC_SET_Y
import com.meepoffaith.hextrapats.init.Patterns.VEC_SET_Z
import com.meepoffaith.hextrapats.util.MathUtils
import com.meepoffaith.hextrapats.util.MultiPreds
import net.minecraft.util.math.Vec3d
import java.util.function.BiFunction
import java.util.function.Function
import java.util.function.UnaryOperator
import kotlin.math.cos
import kotlin.math.sin

class Vec3Arithmetic : Arithmetic {
    private val OPS = listOf(
        ROT_ABOUT_X,
        ROT_ABOUT_Y,
        ROT_ABOUT_Z,
        CONSTRUCT_ABOUT_X,
        CONSTRUCT_ABOUT_Y,
        CONSTRUCT_ABOUT_Z,
        NORMALIZE,
        INVERT,
        INCREMENT,
        DECREMENT,
        APPROACH,
        ANGLE_DIST,
        ANGLE_APPROACH,
        VEC_GET_X,
        VEC_GET_Y,
        VEC_GET_Z,
        VEC_SET_X,
        VEC_SET_Y,
        VEC_SET_Z
    )

    override fun arithName() = "hextrapats_vec3_math"

    override fun opTypes() = OPS

    override fun getOperator(pattern: HexPattern): Operator = when(pattern){
        ROT_ABOUT_X -> makeVecNumToVec{ v, x ->  //Already clockwise
            val c = cos(x)
            val s = sin(x)
            Vec3d(v.x, c * v.y + s * v.z, c * v.z - s * v.y)
        }
        ROT_ABOUT_Y -> makeVecNumToVec{ v, x ->
            var x = x
            x = -x //Negate to make it a clockwise rotation
            val c = cos(x)
            val s = sin(x)
            Vec3d(c * v.x + s * v.z, v.y, c * v.z - s * v.x)
        }
        ROT_ABOUT_Z -> makeVecNumToVec{ v, x ->  //Already clockwise
            val c = cos(x)
            val s = sin(x)
            Vec3d(c * v.x + s * v.y, c * v.y - s * v.x, v.z)
        }
        CONSTRUCT_ABOUT_X -> makeNumToVec{ a -> Vec3d(0.0, sin(a), cos(a)) } //+Z is 0 rad
        CONSTRUCT_ABOUT_Y -> makeNumToVec{ a -> Vec3d(-sin(a), 0.0, cos(a)) } //+Z is 0 rad. Matches player yaw in F3.
        CONSTRUCT_ABOUT_Z -> makeNumToVec{ a -> Vec3d(-cos(a), sin(a), 0.0) } //-X is 0 rad
        NORMALIZE -> makeVecToVec{ v -> v.normalize() }
        INVERT -> makeVecToVec{ v -> v.multiply(-1.0) }
        INCREMENT -> makeVecToVec{ v ->
            val len = v.length()
            if (DoubleIota.tolerates(len, 0.0)) v else v.multiply((len + 1) / len)
        }
        DECREMENT -> makeVecToVec{ v ->
            val len = v.length()
            if (DoubleIota.tolerates(len, 0.0)) v else v.multiply((len - 1) / len)
        }
        APPROACH -> OperatorApproachVec
        ANGLE_DIST -> makeVecVecToNum{ v1, v2 -> MathUtils.vecAngleDist(v1, v2) }
        ANGLE_APPROACH -> OperatorTurnVec
        VEC_GET_X -> makeVecToNum{ v -> v.x }
        VEC_GET_Y -> makeVecToNum{ v -> v.y }
        VEC_GET_Z -> makeVecToNum{ v -> v.z }
        VEC_SET_X -> makeVecNumToVec{ v, x -> Vec3d(x, v.y, v.z) }
        VEC_SET_Y -> makeVecNumToVec{ v, y -> Vec3d(v.x, y, v.z) }
        VEC_SET_Z -> makeVecNumToVec{ v, z -> Vec3d(v.x, v.y, z) }
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")
    }

    fun makeVecToVec(op: UnaryOperator<Vec3d>) = OperatorUnary(MultiPreds.all(VEC3))
    { i: Iota -> Vec3Iota(op.apply(Operator.downcast(i, VEC3).vec3)) }

    fun makeNumToVec(op: Function<Double, Vec3d>) = OperatorUnary(MultiPreds.all(DOUBLE))
    { i: Iota -> Vec3Iota(op.apply(Operator.downcast(i, DOUBLE).double)) }

    fun makeVecToNum(op: Function<Vec3d, Double>) = OperatorUnary(MultiPreds.all(VEC3))
    { i: Iota -> DoubleIota(op.apply(Operator.downcast(i, VEC3).vec3)) }

    fun makeVecNumToVec(op: BiFunction<Vec3d, Double, Vec3d>) = OperatorBinary(MultiPreds.pair(VEC3, DOUBLE))
    { i: Iota, j: Iota -> Vec3Iota(op.apply(Operator.downcast(i, VEC3).vec3, Operator.downcast(j, DOUBLE).double)) }

    fun makeVecVecToNum(op: BiFunction<Vec3d, Vec3d, Double>) = OperatorBinary(MultiPreds.all(VEC3))
    { i: Iota, j: Iota -> DoubleIota(op.apply(Operator.downcast(i, VEC3).vec3, Operator.downcast(j, VEC3).vec3)) }
}
