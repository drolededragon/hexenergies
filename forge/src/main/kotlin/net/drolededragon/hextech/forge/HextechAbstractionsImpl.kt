@file:JvmName("HextechAbstractionsImpl")

package net.drolededragon.hextech.forge

import net.drolededragon.hextech.registry.HextechRegistrar
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HextechRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}

// Energy system implementations for Forge
fun canReceiveEnergy(level: Level, pos: BlockPos): Boolean {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return false
    
    // Check for Forge Energy capability
    val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve()
    if (forgeEnergy.isPresent) {
        return forgeEnergy.get().canReceive()
    }
    
    return false
}

fun insertEnergy(level: Level, pos: BlockPos, energy: Long): Boolean {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return false
    
    // Try Forge Energy
    val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve()
    if (forgeEnergy.isPresent) {
        val storage: IEnergyStorage = forgeEnergy.get()
        if (storage.canReceive()) {
            // Cap at Integer.MAX_VALUE for Forge Energy
            val energyToInsert = energy.coerceAtMost(Integer.MAX_VALUE.toLong()).toInt()
            val inserted = storage.receiveEnergy(energyToInsert, false)
            return inserted > 0
        }
    }
    
    return false
}

fun getEnergyStored(level: Level, pos: BlockPos): Long {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return 0
    
    // Try Forge Energy
    val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve()
    if (forgeEnergy.isPresent) {
        return forgeEnergy.get().energyStored.toLong()
    }
    
    return 0
}

fun getMaxEnergyStored(level: Level, pos: BlockPos): Long {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return 0
    
    // Try Forge Energy
    val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY).resolve()
    if (forgeEnergy.isPresent) {
        return forgeEnergy.get().maxEnergyStored.toLong()
    }
    
    return 0
}
