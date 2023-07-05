package com.aetherteam.aether.network.packet.server;

import com.aetherteam.aether.network.AetherPacket;
import com.aetherteam.aether.perk.data.ServerMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public abstract class ServerMoaSkinPacket {
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements AetherPacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            MoaData.write(buf, this.moaSkinData());
        }

        public static Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            MoaData moaSkinData = MoaData.read(buf);
            return new Apply(uuid, moaSkinData);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null && this.moaSkinData() != null) {
                ServerMoaSkinPerkData.INSTANCE.applyPerkWithVerification(playerEntity.getServer(), this.playerUUID(), this.moaSkinData());
            }
        }
    }

    public record Remove(UUID playerUUID) implements AetherPacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new Remove(uuid);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null) {
                ServerMoaSkinPerkData.INSTANCE.removePerk(playerEntity.getServer(), this.playerUUID());
            }
        }
    }
}
