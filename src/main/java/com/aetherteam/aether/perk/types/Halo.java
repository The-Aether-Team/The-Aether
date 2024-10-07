package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * A data type to store the Halo perk's color.
 */
public record Halo(String hexColor) {
    public static final StreamCodec<FriendlyByteBuf, Halo> STREAM_CODEC = new StreamCodec<>() {
        /**
         * Reads a {@link Halo} from a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @return A {@link Halo}.
         */
        @Override
        public Halo decode(FriendlyByteBuf buffer) {
            String hexColor = buffer.readUtf();
            return new Halo(hexColor);
        }

        /**
         * Writes a {@link Halo} to a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @param halo   A {@link Halo}.
         */
        @Override
        public void encode(FriendlyByteBuf buffer, Halo halo) {
            buffer.writeUtf(halo.hexColor());
        }
    };
}
