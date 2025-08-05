package net.drolededragon.hextech.networking

import dev.architectury.networking.NetworkChannel
import net.drolededragon.hextech.Hextech
import net.drolededragon.hextech.networking.msg.HextechMessageCompanion

object HextechNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Hextech.id("networking_channel"))

    fun init() {
        for (subclass in HextechMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
