package net.drolededragon.hexenergies.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import net.drolededragon.hexenergies.Hexenergies
import net.drolededragon.hexenergies.networking.HexenergiesNetworking
import net.drolededragon.hexenergies.networking.handler.applyOnClient
import net.drolededragon.hexenergies.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface HexenergiesMessage

sealed interface HexenergiesMessageC2S : HexenergiesMessage {
    fun sendToServer() {
        HexenergiesNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface HexenergiesMessageS2C : HexenergiesMessage {
    fun sendToPlayer(player: ServerPlayer) {
        HexenergiesNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        HexenergiesNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface HexenergiesMessageCompanion<T : HexenergiesMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Hexenergies.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is HexenergiesMessageC2S -> msg.applyOnServer(ctx)
                    else -> Hexenergies.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Hexenergies.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is HexenergiesMessageS2C -> msg.applyOnClient(ctx)
                    else -> Hexenergies.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
