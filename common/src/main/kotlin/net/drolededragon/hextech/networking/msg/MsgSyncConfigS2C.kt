package net.drolededragon.hextech.networking.msg

import net.drolededragon.hextech.config.HextechConfig
import net.minecraft.network.FriendlyByteBuf

data class MsgSyncConfigS2C(val serverConfig: HextechConfig.ServerConfig) : HextechMessageS2C {
    companion object : HextechMessageCompanion<MsgSyncConfigS2C> {
        override val type = MsgSyncConfigS2C::class.java

        override fun decode(buf: FriendlyByteBuf) = MsgSyncConfigS2C(
            serverConfig = HextechConfig.ServerConfig.decode(buf),
        )

        override fun MsgSyncConfigS2C.encode(buf: FriendlyByteBuf) {
            serverConfig.encode(buf)
        }
    }
}
