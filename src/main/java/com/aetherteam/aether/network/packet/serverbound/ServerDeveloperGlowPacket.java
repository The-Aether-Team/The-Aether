package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class ServerDeveloperGlowPacket {
    /**
     * Applies the Developer Glow perk to a player on the server.
     */
    public record Apply(UUID playerUUID, DeveloperGlow developerGlow) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            DeveloperGlow.write(buf, this.developerGlow());
        }

        public static ServerDeveloperGlowPacket.Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            DeveloperGlow developerGlow = DeveloperGlow.read(buf);
            return new ServerDeveloperGlowPacket.Apply(uuid, developerGlow);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null && this.developerGlow() != null) {
                ServerPerkData.DEVELOPER_GLOW_INSTANCE.applyPerkWithVerification(playerEntity.getServer(), this.playerUUID(), this.developerGlow());
            }
        }
    }

    /**
     * Removes the Developer Glow perk from a player on the server.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static ServerDeveloperGlowPacket.Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new ServerDeveloperGlowPacket.Remove(uuid);
        }

        @Override
        public void execute(Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null) {
                ServerPerkData.DEVELOPER_GLOW_INSTANCE.removePerk(playerEntity.getServer(), this.playerUUID());
            }
        }
    }
}
