package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.*
import com.meepoffaith.hextrapats.init.IotaTypes.ENTITY_SET
import com.meepoffaith.hextrapats.init.IotaTypes.NUM_SET
import com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET


object MultiPreds {
    val SET_OP = Either3(pair(NUM_SET, DOUBLE), pair(VEC_SET, VEC3), pair(ENTITY_SET, ENTITY))
    val ALL_SETS = Either3(all(NUM_SET), all(VEC_SET), all(ENTITY_SET))

    fun all(type: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.all(IotaPredicate.ofType(type))

    fun pair(first: IotaType<*>, second: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.pair(IotaPredicate.ofType(first), IotaPredicate.ofType(second))

    fun triple(first: IotaType<*>, second: IotaType<*>, third: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.triple(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third))

    fun quad(first: IotaType<*>, second: IotaType<*>, third: IotaType<*>, fourth: IotaType<*>): IotaMultiPredicate =
        Quad(IotaPredicate.ofType(first), IotaPredicate.ofType(second), IotaPredicate.ofType(third), IotaPredicate.ofType(fourth))

    fun any(needs: IotaType<*>, fallback: IotaType<*>): IotaMultiPredicate =
        IotaMultiPredicate.any(IotaPredicate.ofType(needs), IotaPredicate.ofType(fallback))

    data class Quad(
        val first: IotaPredicate,
        val second: IotaPredicate,
        val third: IotaPredicate,
        val fourth: IotaPredicate
    ) : IotaMultiPredicate {
        override fun test(iotas: Iterable<Iota?>): Boolean {
            val it = iotas.iterator()
            return it.hasNext() && first.test(it.next()) &&
                    it.hasNext() && second.test(it.next()) &&
                    it.hasNext() && third.test(it.next()) &&
                    it.hasNext() && fourth.test(it.next()) && !it.hasNext()
        }
    }

    data class Either3(
        val first: IotaMultiPredicate,
        val second: IotaMultiPredicate,
        val third: IotaMultiPredicate
    ) : IotaMultiPredicate {
        override fun test(iotas: Iterable<Iota?>?): Boolean {
            return first.test(iotas) || second.test(iotas) || third.test(iotas)
        }
    }
}
