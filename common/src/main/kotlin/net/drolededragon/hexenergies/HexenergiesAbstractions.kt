@file:JvmName("HexenergiesAbstractions")

package net.drolededragon.hexenergies

import dev.architectury.injectables.annotations.ExpectPlatform
import net.drolededragon.hexenergies.registry.HexenergiesRegistrar
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

fun initRegistries(vararg registries: HexenergiesRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HexenergiesRegistrar<T>) {
    throw AssertionError()
}

// Energy system abstractions
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
