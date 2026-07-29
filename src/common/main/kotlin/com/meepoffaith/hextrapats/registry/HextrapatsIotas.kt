package com.meepoffaith.hextrapats.registry

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import com.meepoffaith.hextrapats.casting.iota.SetIota
import java.util.function.Supplier

object HextrapatsIotas : HextrapatsRegistrar<IotaType<*>>(
    HexRegistries.IOTA_TYPE,
    { HexIotaTypes.REGISTRY },
) {
    val SET = make("set", SetIota.TYPE)

    private fun <U: Iota, T: IotaType<U>> make(name: String, type: T): T {
        register(name) { type }
        return type
    }
}
