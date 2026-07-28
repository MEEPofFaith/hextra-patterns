package com.meepoffaith.hextrapats

import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent

object NeoForgeHextrapatsServer {
    @Suppress("UNUSED_PARAMETER")
    fun init(event: FMLDedicatedServerSetupEvent) {
        Hextrapats.initServer()
    }
}

