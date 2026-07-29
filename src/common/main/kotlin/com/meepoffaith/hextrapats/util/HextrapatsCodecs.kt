package com.meepoffaith.hextrapats.util

import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.utils.TreeList
import com.ibm.icu.text.PluralRules
import com.meepoffaith.hextrapats.casting.iota.DoubleSet
import com.meepoffaith.hextrapats.casting.iota.EntityMap
import com.meepoffaith.hextrapats.casting.iota.VecSet
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import jdk.incubator.vector.VectorShuffle.iota
import net.minecraft.core.UUIDUtil
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.phys.Vec3
import java.util.*
import kotlin.jvm.optionals.getOrNull

object HextrapatsCodecs {
    val DOUBLE_SET: Codec<DoubleSet> = Codec.DOUBLE.listOf().xmap(
        { list: List<Double> ->
            val set = DoubleSet()
            for(num in list){
                set.add(num)
            }
            set
        },
        { set ->
            set.toList()
        }
    )

    val DOUBLE_SET_STREAM = object : StreamCodec<ByteBuf, DoubleSet> {
        override fun decode(buffer: ByteBuf): DoubleSet {
            val count = buffer.readInt() - 1
            val set = DoubleSet()
            for(i in 0..count){
                set.add(buffer.readDouble())
            }
            return set
        }

        override fun encode(buffer: ByteBuf, set: DoubleSet) {
            buffer.writeInt(set.size)
            set.forEach{ buffer.writeDouble(it) }
        }
    }

    val VEC_SET: Codec<VecSet> = Vec3.CODEC.listOf().xmap(
        { list: List<Vec3> ->
            val set = VecSet()
            for(vec in list){
                set.add(vec)
            }
            set
        },
        { set ->
           set.toList()
        }
    )

    val VEC_SET_STREAM = object : StreamCodec<ByteBuf, VecSet> {
        override fun decode(buffer: ByteBuf): VecSet {
            val count = buffer.readInt() - 1
            val set = VecSet()
            for(i in 0..count){
                set.add(Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()))
            }
            return set
        }

        override fun encode(buffer: ByteBuf, set: VecSet) {
            buffer.writeInt(set.size)
            set.forEach{
                buffer.writeDouble(it.x)
                buffer.writeDouble(it.y)
                buffer.writeDouble(it.z)
            }
        }
    }

    val ENTITY_SET: Codec<EntityMap> = // I have no idea send help

    val ENTITY_SET_STREAM = object : StreamCodec<RegistryFriendlyByteBuf, EntityMap> {
        override fun decode(buffer: RegistryFriendlyByteBuf): EntityMap {
            val count = buffer.readInt() - 1
            val map = EntityMap()
            for(num in 0..count){
                map[UUIDUtil.STREAM_CODEC.decode(buffer)] =
                    ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC).decode(buffer).getOrNull()
            }
            return map
        }

        override fun encode(buffer: RegistryFriendlyByteBuf, map: EntityMap) {
            buffer.writeInt(map.size)
            map.forEach{ (uuid, name) ->
                UUIDUtil.STREAM_CODEC.encode(buffer, uuid)
                ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC).encode(buffer, Optional.ofNullable(name))
            }
        }
    }
}
