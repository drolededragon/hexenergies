@file:JvmName("HexenergiesAbstractionsImpl")

package net.drolededragon.hexenergies.forge

import net.drolededragon.hexenergies.registry.HexenergiesRegistrar
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.energy.IEnergyStorage
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HexenergiesRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}

// Energy system implementations for Forge
fun canReceiveEnergy(level: Level, pos: BlockPos): Boolean {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return false
    
    // Try all possible directions (sides) to find energy capability
    val directions = listOf(null) + Direction.values().toList()
    
    for (direction in directions) {
        val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction).resolve()
        if (forgeEnergy.isPresent) {
            val storage = forgeEnergy.get()
            if (storage.canReceive()) {
                return true
            }
        }
    }
    
    return false
}

fun insertEnergy(level: Level, pos: BlockPos, energy: Long): Boolean {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return false
    
    // Try all possible directions (sides) to find energy capability
    val directions = listOf(null) + Direction.values().toList()
    val energyToInsert = energy.coerceAtMost(Integer.MAX_VALUE.toLong()).toInt()
    
    for (direction in directions) {
        val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction).resolve()
        if (forgeEnergy.isPresent) {
            val storage = forgeEnergy.get()
            if (storage.canReceive()) {
                val inserted = storage.receiveEnergy(energyToInsert, false)
                if (inserted > 0) {
                    return true
                }
            }
        }
    }
    
    return false
}

fun getEnergyStored(level: Level, pos: BlockPos): Long {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return 0
    
    // Try all possible directions (sides) to find energy capability
    val directions = listOf(null) + Direction.values().toList()
    
    for (direction in directions) {
        val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction).resolve()
        if (forgeEnergy.isPresent) {
            return forgeEnergy.get().energyStored.toLong()
        }
    }
    
    return 0
}

fun getMaxEnergyStored(level: Level, pos: BlockPos): Long {
    val blockEntity: BlockEntity = level.getBlockEntity(pos) ?: return 0
    
    // Try all possible directions (sides) to find energy capability
    val directions = listOf(null) + Direction.values().toList()
    
    for (direction in directions) {
        val forgeEnergy = blockEntity.getCapability(ForgeCapabilities.ENERGY, direction).resolve()
        if (forgeEnergy.isPresent) {
            return forgeEnergy.get().maxEnergyStored.toLong()
        }
    }
    
    return 0
}
