@file:JvmName("HextrapatsAbstractionsActual")

package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.registry.HextrapatsRegistrar
import net.msrandom.multiplatform.annotations.Actual
import net.neoforged.neoforge.registries.RegisterEvent

actual fun <T : Any> initRegistry(registrar: HextrapatsRegistrar<T>) {
        NeoForgeHextrapats.container.eventBus!!.addListener { event: RegisterEvent ->
            event.register(registrar.registryKey) { helper ->
                registrar.init(helper::register)
            }
        }
}