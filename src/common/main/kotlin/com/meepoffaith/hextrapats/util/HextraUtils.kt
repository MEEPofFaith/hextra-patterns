package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.TreeList
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hextrapats.casting.iota.*
import com.mojang.datafixers.util.Either
import com.samsthenerd.inline.api.InlineAPI
import com.samsthenerd.inline.api.data.EntityInlineData
import com.samsthenerd.inline.api.data.PlayerHeadData
import it.unimi.dsi.fastutil.doubles.DoubleSet
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.component.ResolvableProfile
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

    // Taken from EntityIota
    fun getEntityNameWithInline(entity: Entity): Component {
        val baseName = entity.name.copy()
        var inlineEnt: Component
        if(entity is Player){
            inlineEnt = PlayerHeadData(ResolvableProfile(entity.gameProfile)).asText(false)
            inlineEnt = inlineEnt.plainCopy().withStyle(InlineAPI.INSTANCE.withSizeModifier(inlineEnt.style, 1.5))
        }else{
            inlineEnt = EntityInlineData.fromType(entity.type).asText(false)
        }
        return baseName.append(": ").append(inlineEnt)
    }

    fun List<Iota>.getSet(idx: Int, argc: Int ): IotaMap{
        val x = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, argc) }
        if(x is SetIota){
            return x.iotaMap
        }else{
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "hextrapats:set")
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

    fun Iterator<IndexedValue<Iota>>.nextSet(argc: Int = 0): IotaMap {
        val (idx, x) = this.next()
        if (x is SetIota) {
            return x.iotaMap
        } else {
            throw MishapInvalidIota.ofType(x, if (argc == 0) idx else argc - (idx + 1), "hextrapats:set")
        }
    }
}
