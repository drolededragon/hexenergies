@file:JvmName("HextechAbstractions")

package net.drolededragon.hextech

import dev.architectury.injectables.annotations.ExpectPlatform
import net.drolededragon.hextech.registry.HextechRegistrar

fun initRegistries(vararg registries: HextechRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HextechRegistrar<T>) {
    throw AssertionError()
}
