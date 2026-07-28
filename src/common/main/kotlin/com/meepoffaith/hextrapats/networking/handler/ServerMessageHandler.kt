package com.meepoffaith.hextrapats.networking.handler

import com.meepoffaith.hextrapats.networking.msg.*
import io.wispforest.owo.network.ServerAccess

fun HextrapatsMessageC2S.applyOnServer(access: ServerAccess) = access.player().server.execute {
    // NOTE: this is commented out because otherwise it fails to compile if there's nothing inside of the when expression
    /*
    when (this) {
        is MsgExampleNameC2S -> {
           handleMessage(...)
        }
        // add server-side message handlers here
    }
    */
}
