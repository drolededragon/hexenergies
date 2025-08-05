package net.drolededragon.hextech.fabric

import net.drolededragon.hextech.HextechClient
import net.fabricmc.api.ClientModInitializer

object FabricHextechClient : ClientModInitializer {
    override fun onInitializeClient() {
        HextechClient.init()
    }
}
