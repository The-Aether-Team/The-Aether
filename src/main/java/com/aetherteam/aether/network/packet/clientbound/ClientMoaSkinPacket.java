package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public abstract class ClientMoaSkinPacket {
    public record Apply(UUID playerUUID, MoaData moaSkinData) implements BasePacket {
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
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null && this.moaSkinData() != null) {
                ClientMoaSkinPerkData.INSTANCE.applyPerk(this.playerUUID(), this.moaSkinData());
            }
        }
    }

    public record Remove(UUID playerUUID) implements BasePacket {
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
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && this.playerUUID() != null) {
                ClientMoaSkinPerkData.INSTANCE.removePerk(this.playerUUID());
            }
        }
    }

    public record Sync(Map<UUID, MoaData> moaSkinsData) implements BasePacket {
        @Override
        public void encode(FriendlyByteBuf buf) {
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
