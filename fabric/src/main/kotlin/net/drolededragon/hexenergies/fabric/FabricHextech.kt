package net.drolededragon.hexenergies.fabric

import net.drolededragon.hexenergies.Hexenergies
import net.fabricmc.api.ModInitializer

object FabricHexenergies : ModInitializer {
    override fun onInitialize() {
        Hexenergies.init()
    }
}
