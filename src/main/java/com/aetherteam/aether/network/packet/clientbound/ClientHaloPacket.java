package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientHaloPerkData;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class ClientHaloPacket {
    /**
     * Applies the Halo perk to a player on the client.
     */
    public record Apply(UUID playerUUID, Halo halo) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "apply_halo");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            Halo.write(buf, this.halo());
        }

        public static ClientHaloPacket.Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            Halo halo = Halo.read(buf);
            return new ClientHaloPacket.Apply(uuid, halo);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null && this.halo() != null) {
                ClientHaloPerkData.INSTANCE.applyPerk(this.playerUUID(), this.halo());
            }
        }
    }

    /**
     * Removes the Halo perk from a player on the client.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_halo");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static ClientHaloPacket.Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new ClientHaloPacket.Remove(uuid);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null) {
                ClientHaloPerkData.INSTANCE.removePerk(this.playerUUID());
            }
        }
    }

    /**
     * Syncs Halo perk data for all players to the client.
     */
    public record Sync(Map<UUID, Halo> halos) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "sync_halo");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeMap(this.halos(), FriendlyByteBuf::writeUUID, Halo::write);
        }

        public static ClientHaloPacket.Sync decode(FriendlyByteBuf buf) {
            Map<UUID, Halo> halos = buf.readMap(FriendlyByteBuf::readUUID, Halo::read);
            return new ClientHaloPacket.Sync(halos);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.halos() != null && !this.halos().isEmpty()) {
                for (Map.Entry<UUID, Halo> haloEntry : this.halos().entrySet()) {
                    ClientHaloPerkData.INSTANCE.applyPerk(haloEntry.getKey(), haloEntry.getValue());
                }
            }
        }
    }
}
