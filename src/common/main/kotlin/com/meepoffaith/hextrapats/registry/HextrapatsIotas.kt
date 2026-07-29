package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.meepoffaith.hextrapats.casting.iota.SetIota

object HextrapatsIotas : HextrapatsRegistrar<IotaType<*>>(
    HexRegistries.IOTA_TYPE,
    { HexIotaTypes.REGISTRY },
) {
    val SET = make("set", SetIota.TYPE)

    private fun make(name: String, type: IotaType<*>) =
        register(name) {type}
}
