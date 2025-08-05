package net.drolededragon.hextech

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import net.drolededragon.hextech.config.HextechConfig
import net.drolededragon.hextech.networking.HextechNetworking
import net.drolededragon.hextech.registry.HextechActions

object Hextech {
    const val MODID = "hextech"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        HextechConfig.init()
        initRegistries(
            HextechActions,
        )
        HextechNetworking.init()
    }
}
