package com.meepoffaith.hextrapats.casting.actions.lists

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.getLong
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import it.unimi.dsi.fastutil.longs.LongArrayList

object OpListSwindle : ConstMediaAction {
    override val argc = 2
    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val list = args.getList(0, argc).toMutableList()
        val code = args.getLong(1, argc)

        //Copied from OpAlwinfyHasAscendedToABeingOfPureMath.kt. Don't ask me how it works because I don't know.
        val strides = LongArrayList()
        for (f in FactorialIter()) {
            if (f <= code)
                strides.add(f)
            else
                break
        }

        if (strides.size > list.size)
            throw MishapNotEnoughArgs(strides.size + 1, list.size + 1)
        var editTarget = list.subList(list.size - strides.size, list.size)
        val swap = editTarget.toMutableList()
        var radix = code
        for (divisor in strides.asReversed()) {
            val index = radix / divisor
            radix %= divisor
            editTarget[0] = swap.removeAt(index.toInt())
            // i hope this isn't O(n)
            editTarget = editTarget.subList(1, editTarget.size)
        }

        return list.asActionResult
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
