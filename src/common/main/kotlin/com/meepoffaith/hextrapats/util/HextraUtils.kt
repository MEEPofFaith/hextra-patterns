package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.meepoffaith.hextrapats.casting.iota.VecSetIota
import com.mojang.datafixers.util.Either
import net.minecraft.entity.Entity
import net.minecraft.util.math.Vec3d
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3


object HextraUtils {
    /** Simulates the accumulation process of Numerical Reflection  */
    fun numericalReflection(s: String): Double{
        var accumulator = 0.0
        for (ch in s.toCharArray()) {
            when (ch) {
                'w' -> accumulator++
                'q' -> accumulator += 5.0
                'e' -> accumulator += 10.0
                'a' -> accumulator *= 2.0
                'd' -> accumulator /= 2.0
                's' -> {}
                else -> throw IllegalStateException()
            }
        }
        return accumulator
    }

    fun greaterEq(a: Double, b: Double): Boolean {
        return a >= b || DoubleIota.tolerates(a, b)
    }

    fun lessEq(a: Double, b: Double): Boolean {
        return a <= b || DoubleIota.tolerates(a, b)
    }

    fun specialHandlerLang(handler: SpecialHandler.Factory<*>): String {
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getKey(handler).get()
        return HexAPI.instance().getSpecialHandlerI18nKey(key)
    }

    fun Set<Entity>.asActionResult(): List<Iota> = listOf(EntitySetIota(this))

    fun List<Iota>.getSet(index: Int, argc: Int ): AnySet = AnySet(get(index), argc - (index + 1))

    fun List<Iota>.getNumSet(idx: Int, argc: Int = 0): DoubleSet {
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        if (x is DoubleSetIota) {
            return x.copySet()
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "vector")
        }
    }

    fun List<Iota>.getVecSet(idx: Int, argc: Int = 0): VecSet {
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        if (x is VecSetIota) {
            return x.copySet()
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "vector")
        }
    }

    fun List<Iota>.getEntitySet(idx: Int, argc: Int = 0): MutableSet<Entity> {
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        if (x is EntitySetIota) {
            return x.copySet()
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "vector")
        }
    }

    fun List<Iota>.getVecOrList(idx: Int, argc: Int = 0): Either<Vec3, TreeList<Iota>> {
        val datum = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
        return when(datum){
            is Vec3Iota -> Either.left(datum.vec3)
            is ListIota -> Either.right(datum.list)
            else -> throw MishapInvalidIota.of(
                datum,
                if (argc == 0) idx else argc - (idx + 1),
                "hextrapats:veclist"
            )
        }
    }
}
