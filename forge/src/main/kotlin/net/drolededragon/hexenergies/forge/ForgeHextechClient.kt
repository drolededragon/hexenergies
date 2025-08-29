package net.drolededragon.hexenergies.forge

import net.drolededragon.hexenergies.HexenergiesClient
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeHexenergiesClient {
    fun init(event: FMLClientSetupEvent) {
        HexenergiesClient.init()
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { _, parent -> HexenergiesClient.getConfigScreen(parent) }
        }
    }
}
