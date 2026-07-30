package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getLong
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.utils.TreeList
import it.unimi.dsi.fastutil.longs.LongArrayList
import kotlin.math.abs

object OpListSwindle : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc)
        val code = args.getLong(1, argc)
        var radix = abs(code)

        //Copied from OpAlwinfyHasAscendedToABeingOfPureMath.kt. Don't ask me how it works because I don't know.
        val strides = LongArrayList()
        for (f in FactorialIter()) {
            if (f <= radix)
                strides.add(f)
            else
                break
        }

        if (strides.size > list.size)
            throw MishapInvalidIota.of(args[0], 1, "hextrapats:too_short_to_swindle", strides.size + 1)
        val newOrder = TreeList.TreeListBuilder<Iota>()
        val oldOrder = (
                if (code >= 0) list.slice(list.size - strides.size, list.size)
                else list.slice(0, strides.size - 1)
            ).toMutableList()
        for (divisor in strides.asReversed()) {
            val index = radix / divisor
            radix %= divisor
            newOrder.addOne(oldOrder.removeAt(index.toInt()))
        }
        return if(code >= 0){
            list.dropRight(strides.size).appendedAll(newOrder.result()).asActionResult
        }else{
            newOrder.result().appendedAll(list.drop(strides.size)).asActionResult
        }
    }

    private class FactorialIter : Iterator<Long> {
        var acc = 1L
        var n = 1L
        override fun hasNext(): Boolean = true

        override fun next(): Long {
            val out = this.acc
            this.acc *= this.n
            this.n++
            return out
        }
    }
}
