package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.network.AetherPacket;
import com.aetherteam.aether.perk.data.ServerHaloPerkData;
import com.aetherteam.aether.perk.types.Halo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ServerHaloPacket {
    public record Apply(UUID playerUUID, Halo halo) implements AetherPacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            Halo.write(buf, this.halo());
        }

        public static ServerHaloPacket.Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            Halo halo = Halo.read(buf);
            return new ServerHaloPacket.Apply(uuid, halo);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null && this.halo() != null) {
                ServerHaloPerkData.INSTANCE.applyPerkWithVerification(playerEntity.getServer(), this.playerUUID(), this.halo());
            }
        }
    }

    public record Remove(UUID playerUUID) implements AetherPacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static ServerHaloPacket.Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new ServerHaloPacket.Remove(uuid);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null) {
                ServerHaloPerkData.INSTANCE.removePerk(playerEntity.getServer(), this.playerUUID());
            }
        }
    }
}
