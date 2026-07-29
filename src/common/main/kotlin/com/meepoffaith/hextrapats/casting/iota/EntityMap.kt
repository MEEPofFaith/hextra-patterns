package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import com.meepoffaith.hextrapats.util.HextraUtils
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import java.util.UUID

class EntityMap : HashMap<UUID, Component?> {
    constructor(): super()
    constructor(map: EntityMap) : super(map)

    fun putEntity(entity: Entity): Boolean{
        return put(entity.uuid, HextraUtils.getEntityNameWithInline(entity)) == null
    }

    fun containsEntity(entity: Entity): Boolean {
        return containsKey(entity.uuid)
    }

    fun removeEntity(entity: Entity): Boolean {
        return remove(entity.uuid) != null
    }

    fun removeAll(map: EntityMap) {
        for(key in map.keys){
            remove(key)
        }
    }

    fun asActionResult() : List<Iota> = listOf(EntitySetIota(this))
}
