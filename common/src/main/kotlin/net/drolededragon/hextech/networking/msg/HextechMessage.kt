package net.drolededragon.hextech.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import net.drolededragon.hextech.Hextech
import net.drolededragon.hextech.networking.HextechNetworking
import net.drolededragon.hextech.networking.handler.applyOnClient
import net.drolededragon.hextech.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface HextechMessage

sealed interface HextechMessageC2S : HextechMessage {
    fun sendToServer() {
        HextechNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface HextechMessageS2C : HextechMessage {
    fun sendToPlayer(player: ServerPlayer) {
        HextechNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        HextechNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface HextechMessageCompanion<T : HextechMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Hextech.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is HextechMessageC2S -> msg.applyOnServer(ctx)
                    else -> Hextech.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Hextech.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is HextechMessageS2C -> msg.applyOnClient(ctx)
                    else -> Hextech.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
