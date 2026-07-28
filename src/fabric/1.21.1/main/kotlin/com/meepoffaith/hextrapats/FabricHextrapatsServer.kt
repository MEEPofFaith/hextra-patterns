package com.meepoffaith.hextrapats

import net.fabricmc.api.DedicatedServerModInitializer

object FabricHextrapatsServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        Hextrapats.initServer()
    }
}
