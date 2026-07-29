package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.utils.*
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtLongArray
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

class VecSetIota(payload: VecSet) : Iota(TYPE, payload) {
    fun getSet(): VecSet = payload as VecSet

    fun copySet(): VecSet = VecSet(getSet())

    override fun isTruthy(): Boolean = getSet().isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is VecSetIota && that.getSet() == getSet()
    }

    override fun size(): Int = getSet().size

    override fun serialize(): NbtElement {
        val list = NbtList()
        for(key in getSet()){
            list.add(key.serializeToNBT())
        }
        return list
    }

    override fun getType(): IotaType<VecSetIota> = TYPE

    companion object {
        @JvmField
        var TYPE: IotaType<VecSetIota> = object : IotaType<VecSetIota>() {
            @Throws(IllegalArgumentException::class)
            override fun deserialize(tag: NbtElement, world: ServerWorld): VecSetIota {
                val list: NbtList = tag.downcast(NbtList.TYPE)
                val set = VecSet()
                for (i in list.indices) {
                    set.add(deserializeVec(list[i]))
                }
                return VecSetIota(set)
            }

            override fun display(tag: NbtElement): Text {
                val comp = "{vecs: ".darkRed
                val list = tag.downcast(NbtList.TYPE)
                for (i in list.indices) {
                    comp.append(Vec3Iota.display(deserializeVec(list[i])))
                    if (i + 1 < list.size) {
                        comp.append(" | ".darkRed)
                    }
                }
                comp.append("}".darkRed)

                return comp
            }

            override fun color(): Int {
                return 0xAA0000
            }
        }

        fun deserializeVec(tag: NbtElement): Vec3d = if(tag.nbtType == NbtLongArray.TYPE){
            vecFromNBT(tag.downcast(NbtLongArray.TYPE).asLongArray)
        }else{
            vecFromNBT(tag.downcast(NbtCompound.TYPE))
        }
    }
}
