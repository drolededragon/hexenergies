@file:JvmName("HextechAbstractionsImpl")

package net.drolededragon.hextech.fabric

import net.drolededragon.hextech.registry.HextechRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HextechRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
