package com.meepoffaith.hextrapats.networking

import com.meepoffaith.hextrapats.Hextrapats
import com.meepoffaith.hextrapats.networking.msg.HextrapatsMessageCompanion
import io.wispforest.owo.network.OwoNetChannel

object HextrapatsNetworking {
    val CHANNEL: OwoNetChannel = OwoNetChannel.create(Hextrapats.id("networking_channel"))

    fun init() {
        for (subclass in HextrapatsMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
