@file:JvmName("HextechAbstractionsImpl")

package net.drolededragon.hextech.forge

import net.drolededragon.hextech.registry.HextechRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HextechRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
