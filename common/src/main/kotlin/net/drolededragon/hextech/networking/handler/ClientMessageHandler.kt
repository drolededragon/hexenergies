package net.drolededragon.hextech.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import net.drolededragon.hextech.config.HextechConfig
import net.drolededragon.hextech.networking.msg.*

fun HextechMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            HextechConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
