package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.config.HextrapatsConfigs
import com.meepoffaith.hextrapats.networking.HextrapatsNetworking
import com.meepoffaith.hextrapats.registry.HextraActions
import com.meepoffaith.hextrapats.registry.HextraContinuationTypes
import com.meepoffaith.hextrapats.registry.HextraIotas
import com.meepoffaith.hextrapats.registry.HextraSpecialHandlers
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Hextrapats {
    const val MODID = "hextrapats"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)

    fun init() {
        initRegistries(
            HextraIotas,
            HextraContinuationTypes,
            HextraActions,
            HextraSpecialHandlers
        )
        HextrapatsNetworking.init()
        HextrapatsConfigs.init()
    }

    fun initServer() {
    }
}
