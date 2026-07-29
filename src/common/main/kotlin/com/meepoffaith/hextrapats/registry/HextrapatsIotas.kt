package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.meepoffaith.hextrapats.casting.iota.DoubleSetIota
import com.meepoffaith.hextrapats.casting.iota.EntitySetIota
import com.meepoffaith.hextrapats.casting.iota.VecSetIota

object HextrapatsIotas : HextrapatsRegistrar<IotaType<*>>(
    HexRegistries.IOTA_TYPE,
    { HexIotaTypes.REGISTRY },
) {
    val NUM_SET = make("num_set", DoubleSetIota.TYPE)
    val VEC_SET = make("vec_set", VecSetIota.TYPE)
    val ENTITY_SET = make("entity_set", EntitySetIota.TYPE)

    private fun make(name: String, type: IotaType<*>) =
        register(name) {type}
}
