package net.drolededragon.hexenergies.fabric

import net.drolededragon.hexenergies.HexenergiesClient
import net.fabricmc.api.ClientModInitializer

object FabricHexenergiesClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexenergiesClient.init()
    }
}
