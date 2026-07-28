package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.config.HextrapatsConfigs
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import com.meepoffaith.hextrapats.networking.HextrapatsNetworking
import com.meepoffaith.hextrapats.registry.HextrapatsActions

object Hextrapats {
    const val MODID = "hextrapats"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)



    @JvmStatic
    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MODID, path)

    fun init() {
        initRegistries(
            HextrapatsActions,
        )
        HextrapatsNetworking.init()
        HextrapatsConfigs.init()
    }

    fun initServer() {
    }
}
