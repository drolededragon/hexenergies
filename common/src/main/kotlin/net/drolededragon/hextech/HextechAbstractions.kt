@file:JvmName("HextechAbstractions")

package net.drolededragon.hextech

import dev.architectury.injectables.annotations.ExpectPlatform
import net.drolededragon.hextech.registry.HextechRegistrar
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level

fun initRegistries(vararg registries: HextechRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HextechRegistrar<T>) {
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
