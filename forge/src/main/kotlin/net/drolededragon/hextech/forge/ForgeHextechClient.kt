package net.drolededragon.hextech.forge

import net.drolededragon.hextech.HextechClient
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeHextechClient {
    fun init(event: FMLClientSetupEvent) {
        HextechClient.init()
        LOADING_CONTEXT.registerExtensionPoint(ConfigScreenFactory::class.java) {
            ConfigScreenFactory { _, parent -> HextechClient.getConfigScreen(parent) }
        }
    }
}
