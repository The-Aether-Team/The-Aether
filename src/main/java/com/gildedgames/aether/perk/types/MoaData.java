package com.gildedgames.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public record MoaData(UUID moaUUID, MoaSkins.MoaSkin moaSkin) {
    public static MoaData read(FriendlyByteBuf buffer) {
        UUID uuid = null;
        if (buffer.readBoolean()) {
            uuid = buffer.readUUID();
        }
        MoaSkins.MoaSkin moaSkin = MoaSkins.MoaSkin.read(buffer);
        return new MoaData(uuid, moaSkin);
    }

    public static void write(FriendlyByteBuf buffer, MoaData moaData) {
        if (moaData.moaUUID() == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeUUID(moaData.moaUUID());
        }
        MoaSkins.MoaSkin.write(buffer, moaData.moaSkin());
    }
}
