@file:JvmName("HextechEnergyAbstractions")

package net.drolededragon.hextech.energy

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

@ExpectPlatform
fun canReceiveEnergy(level: Level, pos: BlockPos): Boolean {
    throw AssertionError("This method should be replaced by Architectury")
}

@ExpectPlatform
fun insertEnergy(level: Level, pos: BlockPos, energy: Long): Boolean {
    throw AssertionError("This method should be replaced by Architectury")
}

@ExpectPlatform
fun getEnergyStored(level: Level, pos: BlockPos): Long {
    throw AssertionError("This method should be replaced by Architectury")
}

@ExpectPlatform
fun getMaxEnergyStored(level: Level, pos: BlockPos): Long {
    throw AssertionError("This method should be replaced by Architectury")
}