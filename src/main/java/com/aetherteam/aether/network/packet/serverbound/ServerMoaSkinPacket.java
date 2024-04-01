package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ServerPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class ServerMoaSkinPacket {
    /**
     * Applies a Moa Skin for a player on the server.
     */
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "apply_moa_skin_server");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
            MoaData.write(buf, this.moaSkinData());
        }

        public static Apply decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            MoaData moaSkinData = MoaData.read(buf);
            return new Apply(uuid, moaSkinData);
        }

        @Override
        public void execute(@Nullable Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null && this.moaSkinData() != null) {
                ServerPerkData.MOA_SKIN_INSTANCE.applyPerkWithVerification(playerEntity.getServer(), this.playerUUID(), this.moaSkinData());
            }
        }
    }

    /**
     * Removes a Moa Skin for a player on the server.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "remove_moa_skin_server");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeUUID(this.playerUUID());
        }

        public static Remove decode(FriendlyByteBuf buf) {
            UUID uuid = buf.readUUID();
            return new Remove(uuid);
        }

        @Override
        public void execute(@Nullable Player playerEntity) {
            if (playerEntity != null && playerEntity.getServer() != null && this.playerUUID() != null) {
                ServerPerkData.MOA_SKIN_INSTANCE.removePerk(playerEntity.getServer(), this.playerUUID());
            }
        }
    }
}
