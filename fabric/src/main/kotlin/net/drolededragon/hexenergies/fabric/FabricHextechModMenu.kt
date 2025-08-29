package net.drolededragon.hexenergies.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.drolededragon.hexenergies.HexenergiesClient

object FabricHexenergiesModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexenergiesClient::getConfigScreen)
}
