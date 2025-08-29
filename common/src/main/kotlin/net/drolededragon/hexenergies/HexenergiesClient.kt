package net.drolededragon.hexenergies

import net.drolededragon.hexenergies.config.HexenergiesConfig
import net.drolededragon.hexenergies.config.HexenergiesConfig.GlobalConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object HexenergiesClient {
    fun init() {
        HexenergiesConfig.initClient()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(GlobalConfig::class.java, parent).get()
    }
}
