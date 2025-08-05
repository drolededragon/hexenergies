package net.drolededragon.hextech

import net.drolededragon.hextech.config.HextechConfig
import net.drolededragon.hextech.config.HextechConfig.GlobalConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object HextechClient {
    fun init() {
        HextechConfig.initClient()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(GlobalConfig::class.java, parent).get()
    }
}
