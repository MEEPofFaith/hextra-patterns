package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.darkGreen
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.api.utils.green
import net.minecraft.nbt.NbtDouble
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtList
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.MutableText
import net.minecraft.text.Text


class DoubleSetIota(payload: DoubleSet) : Iota(TYPE, payload) {
    fun getSet(): DoubleSet = payload as DoubleSet

    fun copySet(): DoubleSet = DoubleSet(getSet())

    override fun isTruthy(): Boolean = getSet().isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is DoubleSetIota && that.getSet() == getSet()
    }

    override fun size(): Int = getSet().size

    override fun serialize(): NbtElement {
        val list = NbtList()
        for(key in getSet()){
            list.add(NbtDouble.of(key))
        }
        return list
    }

    override fun getType(): IotaType<DoubleSetIota> = TYPE

    companion object {
        @JvmField
        var TYPE: IotaType<DoubleSetIota> = object : IotaType<DoubleSetIota>() {
            @Throws(IllegalArgumentException::class)
            override fun deserialize(tag: NbtElement, world: ServerWorld): DoubleSetIota {
                val list: NbtList = tag.downcast(NbtList.TYPE)
                val set = DoubleSet()
                for (i in list.indices) {
                    set.add(list.getDouble(i))
                }
                return DoubleSetIota(set)
            }

            override fun display(tag: NbtElement): Text {
                val comp: MutableText = "{nums: ".darkGreen
                val list: NbtList = tag.downcast(NbtList.TYPE)
                for (i in list.indices) {
                    val num = list.getDouble(i).toString()
                    comp.append(num.green)
                    if (i + 1 < list.size) {
                        comp.append(" | ".darkGreen)
                    }
                }
                comp.append("}".darkGreen)

                return comp
            }

            override fun color(): Int {
                return 0x00AA00
            }
        }
    }
}
