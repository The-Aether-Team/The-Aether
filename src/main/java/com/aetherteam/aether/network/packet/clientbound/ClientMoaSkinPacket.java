package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public abstract class ClientMoaSkinPacket {
    /**
     * Applies a Moa Skin for a player on the client.
     */
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "apply_moa_skin");

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
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null && this.moaSkinData() != null) {
                ClientMoaSkinPerkData.INSTANCE.applyPerk(this.playerUUID(), this.moaSkinData());
            }
        }
    }

    /**
     * Removes a Moa Skin for a player on the client.
     */
    public record Remove(UUID playerUUID) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "remove_moa_skin");

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
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null) {
                ClientMoaSkinPerkData.INSTANCE.removePerk(this.playerUUID());
            }
        }
    }

    /**
     * Syncs Moa Skin data for all players to the client.
     */
    public record Sync(Map<UUID, MoaData> moaSkinsData) implements BasePacket {
        public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "sync_moa_skin");

        @Override
        public ResourceLocation id() {
            return ID;
        }

        @Override
        public void write(FriendlyByteBuf buf) {
            buf.writeMap(this.moaSkinsData(), FriendlyByteBuf::writeUUID, MoaData::write);
        }

        public static Sync decode(FriendlyByteBuf buf) {
            Map<UUID, MoaData> moaSkinsData = buf.readMap(FriendlyByteBuf::readUUID, MoaData::read);
            return new Sync(moaSkinsData);
        }

        @Override
        public void execute(Player playerEntity) {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.moaSkinsData() != null && !this.moaSkinsData().isEmpty()) {
                for (Map.Entry<UUID, MoaData> moaSkinsDataEntry : this.moaSkinsData().entrySet()) {
                    ClientMoaSkinPerkData.INSTANCE.applyPerk(moaSkinsDataEntry.getKey(), moaSkinsDataEntry.getValue());
                }
            }
        }
    }
}
