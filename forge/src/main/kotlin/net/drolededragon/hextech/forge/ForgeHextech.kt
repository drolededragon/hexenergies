package net.drolededragon.hextech.forge

import dev.architectury.platform.forge.EventBuses
import net.drolededragon.hextech.Hextech
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hextech.MODID)
class HextechForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hextech.MODID, this)
            addListener(ForgeHextechClient::init)
            addListener(::gatherData)
        }
        Hextech.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
