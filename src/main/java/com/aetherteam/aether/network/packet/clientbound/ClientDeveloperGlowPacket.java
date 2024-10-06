package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientDeveloperGlowPerkData;
import com.aetherteam.aether.perk.types.DeveloperGlow;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class ClientDeveloperGlowPacket {
    /**
     * Applies the Developer Glow perk to a player on the client.
     */
    public record Apply(UUID playerUUID, DeveloperGlow developerGlow) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_developer_glow");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
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

    /**
     * Removes the Developer Glow perk from a player on the client.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_developer_glow");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
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

    /**
     * Syncs Developer Glow perk data for all players to the client.
     */
    public record Sync(Map<UUID, DeveloperGlow> developerGlows) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_developer_glow");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
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
