package com.meepoffaith.hextrapats.networking.msg

import com.meepoffaith.hextrapats.Hextrapats
import com.meepoffaith.hextrapats.networking.HextrapatsNetworking
import com.meepoffaith.hextrapats.networking.handler.applyOnClient
import com.meepoffaith.hextrapats.networking.handler.applyOnServer
import io.wispforest.owo.network.ClientAccess
import io.wispforest.owo.network.OwoNetChannel
import io.wispforest.owo.network.ServerAccess
import net.minecraft.server.level.ServerPlayer

sealed interface HextrapatsMessage

sealed interface HextrapatsMessageC2S : HextrapatsMessage {
    fun <T> T.sendToServer() where T : Record {
        HextrapatsNetworking.CHANNEL.clientHandle().send(this)
    }
}

sealed interface HextrapatsMessageS2C : HextrapatsMessage {
}

fun <T> T.sendToPlayer(player: ServerPlayer) where T : Record {
    HextrapatsNetworking.CHANNEL.serverHandle(player).send( this)
}

fun <T> T.sendToPlayers(players: Iterable<ServerPlayer>) where T : Record {
    players.forEach { sendToPlayer(it) }
}

sealed interface HextrapatsMessageCompanion<T> where T : HextrapatsMessage, T : Record {
    val type: Class<T>

    fun apply(msg: T, access: ServerAccess): Unit {
        Hextrapats.LOGGER.debug("Server received packet from {}: {}", access.player().name.string, this)
        when (msg) {
            is HextrapatsMessageC2S -> msg.applyOnServer(access)
            else -> Hextrapats.LOGGER.warn("Message not handled on server: {}", msg::class)
        }
    }

    fun apply(msg: T, access: ClientAccess): Unit {
        Hextrapats.LOGGER.debug("Client received packet: {}", this)
        when (msg) {
            is HextrapatsMessageS2C -> msg.applyOnClient(access)
            else -> Hextrapats.LOGGER.warn("Message not handled on client: {}", msg::class)
        }
    }

    fun register(channel: OwoNetChannel) {
        channel.registerServerbound(type) { msg, access -> apply(msg, access) }
    }
}
