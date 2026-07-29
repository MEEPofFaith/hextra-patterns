package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.darkAqua
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.samsthenerd.inline.api.InlineAPI
import com.samsthenerd.inline.api.data.EntityInlineData
import com.samsthenerd.inline.api.data.PlayerHeadData
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.world.entity.Entity


class EntitySetIota(payload: Set<Entity>) : Iota(TYPE, payload) {
    fun getSet(): Set<Entity> = payload as Set<Entity>

    fun copySet(): MutableSet<Entity> = HashSet(getSet())

    override fun isTruthy(): Boolean = getSet().isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is EntitySetIota && that.getSet() == getSet()
    }

    //For transgress others mishap
    override fun subIotas(): Iterable<Iota> {
        val list = mutableListOf<Iota>();
        for(entity in getSet()){
            list.add(EntityIota(entity))
        }
        return list
    }

    override fun serialize(): NbtElement {
        val list = NbtList()
        for(key in getSet()){
            val ent = NbtCompound()
            ent.putUuid("uuid", key.uuid)
            ent.putString("name", Text.Serializer.toJson(getEntityNameWithInline(key, true)))
        }
        return list
    }

    override fun getType(): IotaType<EntitySetIota> = TYPE

    companion object {
        @JvmField
        var TYPE: IotaType<EntitySetIota> = object : IotaType<EntitySetIota>() {
            @Throws(IllegalArgumentException::class)
            override fun deserialize(tag: NbtElement, world: ServerWorld): EntitySetIota {
                val list: NbtList = tag.downcast(NbtList.TYPE)
                val set = HashSet<Entity>()
                for (i in list.indices) {
                    val ctag = list.getCompound(i)
                    val uuidTag = ctag.get("uuid") ?: continue
                    val uuid = NbtHelper.toUuid(uuidTag)
                    val entity = world.getEntity(uuid) ?: continue
                    set.add(entity)
                }
                return EntitySetIota(set)
            }

            override fun display(tag: NbtElement): Text {
                val comp: MutableText = "{entites: ".darkAqua
                val list: NbtList = tag.downcast(NbtList.TYPE)
                for (i in list.indices) {
                    val ctag = list.getCompound(i)
                    comp.append(HexIotaTypes.ENTITY.display(ctag))
                    if (i + 1 < list.size) {
                        comp.append(" | ".darkAqua)
                    }
                }
                comp.append("}".darkAqua)

                return comp
            }

            override fun color(): Int {
                return 0x0000AA
            }
        }

        fun getEntityNameWithInline(entity: Entity, fearSerializer: Boolean): Text {
            val baseName = entity.name.copy()
            var inlineEnt: Text
            if (entity is PlayerEntity) {
                inlineEnt = PlayerHeadData(entity.gameProfile).asText(!fearSerializer)
                inlineEnt = inlineEnt.copyContentOnly()
                    .fillStyle(InlineAPI.INSTANCE.withSizeModifier(inlineEnt.style, 1.5))
            } else {
                if (fearSerializer) { // we don't want to have to serialize an entity just to display it
                    inlineEnt = EntityInlineData.fromType(entity.type).asText(!fearSerializer)
                } else {
                    inlineEnt = EntityInlineData.fromEntity(entity).asText(!fearSerializer)
                }
            }
            return baseName.append(Text.literal(": ")).append(inlineEnt)
        }
    }
}
