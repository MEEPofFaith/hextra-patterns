package com.meepoffaith.hextrapats.client

import com.meepoffaith.hextrapats.client.HextrapatsClient
import net.fabricmc.api.ClientModInitializer

object FabricHextrapatsClient : ClientModInitializer {
    override fun onInitializeClient() {
        HextrapatsClient.init()
    }
}