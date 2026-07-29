package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.utils.darkGreen
import at.petrak.hexcasting.api.utils.green
import com.meepoffaith.hextrapats.util.HextrapatsCodecs
import com.mojang.serialization.MapCodec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec

class DoubleSetIota(val doubleSet: DoubleSet) : Iota({ TYPE }) {
    fun copySet(): DoubleSet = DoubleSet(doubleSet)

    override fun isTruthy(): Boolean = doubleSet.isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is DoubleSetIota && that.doubleSet == doubleSet
    }

    override fun size(): Int = doubleSet.size

    override fun display(): Component {
        val out = "{nums: ".darkGreen

        var first = true
        for(num in doubleSet){
            if(!first) out.append(" | ".darkGreen)
            out.append("$num".green)
            first = false
        }
        out.append(" }".darkGreen)

        return out
    }

    override fun hashCode(): Int = doubleSet.hashCode()

    companion object {
        val CODEC: MapCodec<DoubleSetIota> =
            HextrapatsCodecs.DOUBLE_SET.xmap({ set -> DoubleSetIota(set) }, DoubleSetIota::doubleSet).fieldOf("double_set")
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, DoubleSetIota> =
            HextrapatsCodecs.DOUBLE_SET_STREAM.map({ set -> DoubleSetIota(set) }, DoubleSetIota::doubleSet).mapStream { buf -> buf }

        var TYPE: IotaType<DoubleSetIota> = object : IotaType<DoubleSetIota>(){
            override fun codec(): MapCodec<DoubleSetIota> = CODEC
            override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, DoubleSetIota> = STREAM_CODEC
            override fun color(): Int = 0x00AA00
        }
    }
}
