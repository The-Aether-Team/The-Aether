package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * A data type tying together a {@link com.aetherteam.aether.entity.passive.Moa}'s {@link UUID} and a {@link com.aetherteam.aether.perk.types.MoaSkins.MoaSkin}.
 */
public record MoaData(@Nullable UUID moaUUID, @Nullable MoaSkins.MoaSkin moaSkin) {
    public static final StreamCodec<FriendlyByteBuf, MoaData> STREAM_CODEC = new StreamCodec<>() {
        /**
         * Reads {@link MoaData} from a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @return The {@link MoaData}.
         */
        @Override
        public MoaData decode(FriendlyByteBuf buffer) {
            UUID uuid = null;
            if (buffer.readBoolean()) {
                uuid = buffer.readUUID();
            }
            MoaSkins.MoaSkin moaSkin = MoaSkins.MoaSkin.read(buffer);
            return new MoaData(uuid, moaSkin);
        }

        /**
         * Writes {@link MoaData} to a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer  The {@link FriendlyByteBuf} buffer.
         * @param moaData The {@link MoaData}.
         */
        @Override
        public void encode(FriendlyByteBuf buffer, MoaData moaData) {
            if (moaData.moaUUID() == null) {
                buffer.writeBoolean(false);
            } else {
                buffer.writeBoolean(true);
                buffer.writeUUID(moaData.moaUUID());
            }
            MoaSkins.MoaSkin.write(buffer, moaData.moaSkin());
        }
    };
}
