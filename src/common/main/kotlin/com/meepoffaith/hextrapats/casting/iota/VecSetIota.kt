package com.meepoffaith.hextrapats.casting.iota

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.utils.darkRed
import com.meepoffaith.hextrapats.util.HextrapatsCodecs
import com.mojang.serialization.MapCodec
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.StreamCodec

class VecSetIota(val vecSet: VecSet) : Iota({ TYPE }) {
    fun copySet(): VecSet = VecSet(vecSet)

    override fun isTruthy(): Boolean = vecSet.isNotEmpty()

    override fun toleratesOther(that: Iota?): Boolean {
        return that is VecSetIota && that.vecSet == vecSet
    }

    override fun size(): Int = vecSet.size

    override fun display(): Component {
        val out = "{vecs: ".darkRed

        var first = true
        for(vec in vecSet){
            if(!first) out.append(" | ".darkRed)
            out.append(Vec3Iota.display(vec))
            first = false
        }
        out.append("}".darkRed)

        return out
    }

    override fun hashCode(): Int = vecSet.hashCode()

    companion object {
        val CODEC: MapCodec<VecSetIota> =
            HextrapatsCodecs.VEC_SET.xmap({ set -> VecSetIota(set) }, VecSetIota::vecSet).fieldOf("vec_set")
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, VecSetIota> =
            HextrapatsCodecs.VEC_SET_STREAM.map({ set -> VecSetIota(set) }, VecSetIota::vecSet).mapStream { buf -> buf }

        var TYPE: IotaType<VecSetIota> = object : IotaType<VecSetIota>() {
            override fun codec(): MapCodec<VecSetIota> = CODEC
            override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, VecSetIota> = STREAM_CODEC
            override fun color(): Int = 0xAA0000
        }
    }
}
