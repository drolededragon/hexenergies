package net.drolededragon.hextech.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.drolededragon.hextech.HextechClient

object FabricHextechModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HextechClient::getConfigScreen)
}
