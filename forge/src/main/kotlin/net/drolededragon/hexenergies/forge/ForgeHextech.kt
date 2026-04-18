package net.drolededragon.hexenergies.forge

import dev.architectury.platform.forge.EventBuses
import net.drolededragon.hexenergies.Hexenergies
import net.drolededragon.hexenergies.forge.energy.ForgeEnergyMediaHolder
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.common.MinecraftForge
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.forge.cap.ForgeCapabilityHandler
import at.petrak.hexcasting.forge.cap.HexCapabilities

@Mod(Hexenergies.MODID)
class HexenergiesForge {
    companion object {
        private val ENERGY_MEDIA_CAPABILITY_ID = ResourceLocation(Hexenergies.MODID, "energy_media")
    }
    
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexenergies.MODID, this)
            addListener(ForgeHexenergiesClient::init)
            addListener(::gatherData)
        }
        
        // Register energy -> media capability attachment
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack::class.java, ::attachEnergyMediaCapability)
        
        Hexenergies.init()
    }

    private fun attachEnergyMediaCapability(event: AttachCapabilitiesEvent<ItemStack>) {
        try {
            val itemStack = event.`object`
            
            // Skip if ItemStack is empty or invalid
            if (itemStack.isEmpty) return
            
            // Check if this item can provide media through energy
            if (ForgeEnergyMediaHolder.canProvideMedia(itemStack)) {
                event.addCapability(
                    ENERGY_MEDIA_CAPABILITY_ID,
                    ForgeCapabilityHandler.provide(
                        itemStack,
                        HexCapabilities.MEDIA,
                        { ForgeEnergyMediaHolder(itemStack) }
                    )
                )
            }
        } catch (e: Exception) {
            // Silently ignore capability attachment failures to prevent crashes
            // This can happen with some edge case items or during world loading
        }
    }
    
    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            // TODO: add datagen providers here
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
