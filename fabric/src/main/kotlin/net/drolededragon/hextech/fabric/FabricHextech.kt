package net.drolededragon.hextech.fabric

import net.drolededragon.hextech.Hextech
import net.fabricmc.api.ModInitializer

object FabricHextech : ModInitializer {
    override fun onInitialize() {
        Hextech.init()
    }
}
