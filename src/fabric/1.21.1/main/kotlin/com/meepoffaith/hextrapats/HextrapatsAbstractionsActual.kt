@file:JvmName("HextrapatsAbstractionsActual")

package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.registry.HextrapatsRegistrar
import net.minecraft.core.Registry
import net.msrandom.multiplatform.annotations.Actual

actual fun <T : Any> initRegistry(registrar: HextrapatsRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
