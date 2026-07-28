@file:JvmName("HextrapatsAbstractions")

package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.registry.HextrapatsRegistrar

fun initRegistries(vararg registries: HextrapatsRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

expect fun <T : Any> initRegistry(registrar: HextrapatsRegistrar<T>)
