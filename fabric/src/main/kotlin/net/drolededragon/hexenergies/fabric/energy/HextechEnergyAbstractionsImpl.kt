@file:JvmName("HexenergiesEnergyAbstractions")

package net.drolededragon.hexenergies.fabric.energy

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

fun canReceiveEnergy(level: Level, pos: BlockPos): Boolean {
    // TODO: Implement Fabric energy systems when needed
    // Most tech mods with energy systems are Forge-focused
    // This can be implemented later for Fabric-specific energy APIs
    return false
}

fun insertEnergy(level: Level, pos: BlockPos, energy: Long): Boolean {
    // TODO: Implement Fabric energy systems when needed
    return false
}

fun getEnergyStored(level: Level, pos: BlockPos): Long {
    // TODO: Implement Fabric energy systems when needed
    return 0
}

fun getMaxEnergyStored(level: Level, pos: BlockPos): Long {
    // TODO: Implement Fabric energy systems when needed
    return 0
}