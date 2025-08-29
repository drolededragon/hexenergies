package net.drolededragon.hexenergies

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import net.drolededragon.hexenergies.config.HexenergiesConfig
import net.drolededragon.hexenergies.networking.HexenergiesNetworking
import net.drolededragon.hexenergies.registry.HexenergiesActions

object Hexenergies {
    const val MODID = "hexenergies"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        HexenergiesConfig.init()
        initRegistries(
            HexenergiesActions,
        )
        HexenergiesNetworking.init()
    }
}
