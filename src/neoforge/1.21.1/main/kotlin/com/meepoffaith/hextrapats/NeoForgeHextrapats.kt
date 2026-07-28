package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.client.NeoForgeHextrapatsClient
import com.meepoffaith.hextrapats.datagen.NeoForgeHextrapatsDatagen
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.fml.common.Mod

@Mod(Hextrapats.MODID)
class NeoForgeHextrapats(modBus: IEventBus, container: ModContainer) {
    init {
        modBus.apply {
            addListener(NeoForgeHextrapatsClient::init)
            addListener(NeoForgeHextrapatsDatagen::init)
            addListener(NeoForgeHextrapatsServer::init)
        }
        Hextrapats.init()
    }

    companion object {
        internal val container: ModContainer
            get() = ModList.get().getModContainerById(Hextrapats.MODID).get()
    }
}
