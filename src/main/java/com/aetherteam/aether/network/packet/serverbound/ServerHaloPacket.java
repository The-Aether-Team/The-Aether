package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.aether.perk.types.Halo;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class ServerHaloPacket {
    /**
     * Applies the Halo perk to a player on the server.
     */
    public record Apply(UUID playerUUID, Halo halo) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "add_halo_server");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            Halo.write(buf, this.halo());
        }

        public static ServerHaloPacket.Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            Halo halo = Halo.read(buf);
            return new ServerHaloPacket.Apply(uuid, halo);
        }

        @Override
        public void execute(@Nullable Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null && this.halo() != null) {
                ServerPerkData.HALO_INSTANCE.applyPerkWithVerification(playerEntity.getServer(), this.playerUUID(), this.halo());
            }
        }
    }

    /**
     * Removes the Halo perk from a player on the server.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remove_halo_server");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static ServerHaloPacket.Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new ServerHaloPacket.Remove(uuid);
        }

        @Override
        public void execute(@Nullable Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null) {
                ServerPerkData.HALO_INSTANCE.removePerk(playerEntity.getServer(), this.playerUUID());
            }
        }
    }
}
