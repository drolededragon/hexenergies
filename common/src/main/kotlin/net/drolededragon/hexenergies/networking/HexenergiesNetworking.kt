package net.drolededragon.hexenergies.networking

import dev.architectury.networking.NetworkChannel
import net.drolededragon.hexenergies.Hexenergies
import net.drolededragon.hexenergies.networking.msg.HexenergiesMessageCompanion

object HexenergiesNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Hexenergies.id("networking_channel"))

    fun init() {
        for (subclass in HexenergiesMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
