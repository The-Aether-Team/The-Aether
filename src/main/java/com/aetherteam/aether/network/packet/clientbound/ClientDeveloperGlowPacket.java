package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class ClientDeveloperGlowPacket {
    public record Apply(UUID playerUUID, DeveloperGlow developerGlow) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            DeveloperGlow.write(buf, this.developerGlow());
        }

        public static ClientDeveloperGlowPacket.Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            DeveloperGlow developerGlow = DeveloperGlow.read(buf);
            return new ClientDeveloperGlowPacket.Apply(uuid, developerGlow);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null && this.developerGlow() != null) {
                ClientDeveloperGlowPerkData.INSTANCE.applyPerk(this.playerUUID(), this.developerGlow());
            }
        }
    }

    public record Remove(UUID playerUUID) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static ClientDeveloperGlowPacket.Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new ClientDeveloperGlowPacket.Remove(uuid);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null) {
                ClientDeveloperGlowPerkData.INSTANCE.removePerk(this.playerUUID());
            }
        }
    }

    public record Sync(Map<UUID, DeveloperGlow> developerGlows) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
            buf.writeMap(this.developerGlows(), FriendlyByteBuf::writeUUID, DeveloperGlow::write);
        }

        public static ClientDeveloperGlowPacket.Sync decode(FriendlyByteBuf buf) {
            Map<UUID, DeveloperGlow> halos = buf.readMap(FriendlyByteBuf::readUUID, DeveloperGlow::read);
            return new ClientDeveloperGlowPacket.Sync(halos);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.developerGlows() != null && !this.developerGlows().isEmpty()) {
                for (Map.Entry<UUID, DeveloperGlow> developerGlowEntry : this.developerGlows().entrySet()) {
                    ClientDeveloperGlowPerkData.INSTANCE.applyPerk(developerGlowEntry.getKey(), developerGlowEntry.getValue());
                }
            }
        }
    }
}
