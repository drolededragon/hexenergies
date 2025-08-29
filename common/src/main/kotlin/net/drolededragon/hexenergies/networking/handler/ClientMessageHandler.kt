package net.drolededragon.hexenergies.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import net.drolededragon.hexenergies.config.HexenergiesConfig
import net.drolededragon.hexenergies.networking.msg.*

fun HexenergiesMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            HexenergiesConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
