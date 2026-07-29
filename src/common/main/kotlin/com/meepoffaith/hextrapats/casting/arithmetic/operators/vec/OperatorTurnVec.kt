package com.meepoffaith.hextrapats.casting.arithmetic.operators.vec

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3
import com.meepoffaith.hextrapats.util.MathUtils
import com.meepoffaith.hextrapats.util.MultiPreds
import kotlin.math.cos
import kotlin.math.sin

object OperatorTurnVec : OperatorBasic(3, MultiPreds.triple(VEC3.get(), VEC3.get(), DOUBLE.get())) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator()
        val fromI = downcast(it.next(), VEC3.get())
        val toI = downcast(it.next(), VEC3.get())
        val from = fromI.vec3
        val to = toI.vec3
        val theta = downcast(it.next(), DOUBLE.get()).double
        val angDist = MathUtils.vecAngleDist(from, to)

        if(theta >= angDist){
            return to.scale(from.length() / to.length()).asActionResult
        }else if(DoubleIota.tolerates(angDist, Math.PI)){
            //From and To are facing directly away from each other. In this case, no axis of rotation can be determined (cross product returns the 0 vector).
            throw MishapInvalidIota.of(toI, 1, "hextrapats:opposite_vecs", fromI.display());
        }

        val fromN = from.normalize()
        val toN = to.normalize()

        val cross = fromN.cross(toN).cross(fromN).normalize()
        val next = fromN.scale(cos(theta)).add(cross.scale(sin(theta)))

        return next.scale(from.length()).asActionResult
    }
}
