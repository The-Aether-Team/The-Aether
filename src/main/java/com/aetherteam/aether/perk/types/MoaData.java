package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

/**
 * A data type tying together a {@link com.aetherteam.aether.entity.passive.Moa}'s {@link UUID} and a {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}.
 */
public record MoaData(UUID moaUUID, MoaSkins.MoaSkin moaSkin) {
    /**
     * Reads {@link MoaData} from a {@link FriendlyByteBuf} network buffer.
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @return The {@link MoaData}.
     */
    public static MoaData read(FriendlyByteBuf buffer) {
        UUID uuid = null;
        if (buffer.readBoolean()) {
            uuid = buffer.readUUID();
        }
        MoaSkins.MoaSkin moaSkin = MoaSkins.MoaSkin.read(buffer);
        return new MoaData(uuid, moaSkin);
    }

    /**
     * Writes {@link MoaData} to a {@link FriendlyByteBuf} network buffer.
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @param moaData The {@link MoaData}.
     */
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
