package net.drolededragon.hexenergies.forge.energy

import at.petrak.hexcasting.api.addldata.ADMediaHolder
import net.drolededragon.hexenergies.config.HexenergiesConfig
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.energy.IEnergyStorage

class ForgeEnergyMediaHolder(private val itemStack: ItemStack) : ADMediaHolder {
    private val energyStorage: IEnergyStorage? = itemStack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null)
    
    companion object {
        /**
         * Determines if an ItemStack can provide media through its energy storage
         * Phase 1: Simple canExtract() check
         */
        fun canProvideMedia(stack: ItemStack): Boolean {
            return try {
                // Skip empty or invalid stacks
                if (stack.isEmpty) return false
                
                val energy = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null) ?: return false
                energy.canExtract()
            } catch (e: Exception) {
                // If anything fails, assume it can't provide media
                false
            }
        }
    }
    
    override fun getMedia(): Long {
        val energy = energyStorage ?: return 0L
        val energyAmount = energy.energyStored.toLong()
        val conversionRatio = HexenergiesConfig.server.mediaToEnergyRatio.toLong()
        return energyAmount / conversionRatio
    }
    
    override fun getMaxMedia(): Long {
        val energy = energyStorage ?: return 0L
        val maxEnergyAmount = energy.maxEnergyStored.toLong()
        val conversionRatio = HexenergiesConfig.server.mediaToEnergyRatio.toLong()
        return maxEnergyAmount / conversionRatio
    }
    
    override fun setMedia(newMedia: Long) {
        val energy = energyStorage ?: return
        if (!energy.canExtract()) return
        
        val conversionRatio = HexenergiesConfig.server.mediaToEnergyRatio
        val targetEnergyAmount = (newMedia * conversionRatio).coerceAtLeast(0L)
        val currentEnergy = energy.energyStored.toLong()
        
        if (targetEnergyAmount < currentEnergy) {
            // Need to consume energy (extract it)
            val energyToConsume = (currentEnergy - targetEnergyAmount).coerceAtMost(Int.MAX_VALUE.toLong()).toInt()
            energy.extractEnergy(energyToConsume, false)
        }
        // Note: We can't add energy back, this is a one-way consumption system
    }
    
    override fun canRecharge(): Boolean = false
    
    override fun canProvide(): Boolean {
        val energy = energyStorage ?: return false
        return energy.canExtract() && energy.energyStored > 0
    }
    
    override fun getConsumptionPriority(): Int {
        // Lower priority than batteries, but higher than creative sources
        return ADMediaHolder.BATTERY_PRIORITY + 10
    }
    
    override fun canConstructBattery(): Boolean = false
}