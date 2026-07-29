package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.darkAqua
import at.petrak.hexcasting.api.utils.darkGreen
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.meepoffaith.hextrapats.util.HextraUtils.getEntityNameWithInline
import com.meepoffaith.hextrapats.util.HextrapatsCodecs
import com.mojang.serialization.MapCodec
import com.samsthenerd.inline.api.InlineAPI
import com.samsthenerd.inline.api.data.EntityInlineData
import com.samsthenerd.inline.api.data.PlayerHeadData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.world.entity.Entity
import java.util.UUID

class EntitySetIota(val entityMap: EntityMap) : Iota({ TYPE }) {
    fun copyMap(): EntityMap = EntityMap(entityMap)

    override fun isTruthy(): Boolean = entityMap.isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is EntitySetIota && that.entityMap == entityMap
    }

    //For transgress others mishap
    override fun subIotas(): Iterable<Iota> {
        val list = mutableListOf<Iota>();
        for(entity in entityMap){
            list.add(EntityIota(entity))
        }
        return list
    }

    override fun display(): Component {
        val out = "{entities ".darkAqua

        var first = true
        for(name in entityMap.values){
            if(!first) out.append(" | ".darkAqua)
            out.append(name)
            first = false
        }
        out.append(" }".darkAqua)

        return out
    }

    override fun hashCode(): Int = entityMap.hashCode()

    companion object {
        val CODEC: MapCodec<EntitySetIota> =
            HextrapatsCodecs.ENTITY_SET.xmap({ map -> EntitySetIota(map) }, EntitySetIota::entityMap).fieldOf("entity_set")
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, EntitySetIota> =
            HextrapatsCodecs.ENTITY_SET_STREAM.map({ map -> EntitySetIota(map) }, EntitySetIota::entityMap)

        @JvmField
        var TYPE: IotaType<EntitySetIota> = object : IotaType<EntitySetIota>() {
            override fun codec(): MapCodec<EntitySetIota> = CODEC
            override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, EntitySetIota> = STREAM_CODEC
            override fun color(): Int = 0x0000AA
        }
    }
}
