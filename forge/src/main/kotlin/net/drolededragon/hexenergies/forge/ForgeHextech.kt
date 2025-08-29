package net.drolededragon.hexenergies.forge

import dev.architectury.platform.forge.EventBuses
import net.drolededragon.hexenergies.Hexenergies
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexenergies.MODID)
class HexenergiesForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexenergies.MODID, this)
            addListener(ForgeHexenergiesClient::init)
            addListener(::gatherData)
        }
        Hexenergies.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
