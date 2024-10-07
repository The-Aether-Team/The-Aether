package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/**
 * A data type to store the Developer Glow perk's color.
 */
public record DeveloperGlow(String hexColor) {
    public static final StreamCodec<FriendlyByteBuf, DeveloperGlow> STREAM_CODEC = new StreamCodec<>() {
        /**
         * Reads a {@link DeveloperGlow} from a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer The {@link FriendlyByteBuf} buffer.
         * @return A {@link DeveloperGlow}.
         */
        @Override
        public DeveloperGlow decode(FriendlyByteBuf buffer) {
            String hexColor = buffer.readUtf();
            return new DeveloperGlow(hexColor);
        }

        /**
         * Writes a {@link DeveloperGlow} to a {@link FriendlyByteBuf} network buffer.
         *
         * @param buffer        The {@link FriendlyByteBuf} buffer.
         * @param developerGlow A {@link DeveloperGlow}.
         */
        @Override
        public void encode(FriendlyByteBuf buffer, DeveloperGlow developerGlow) {
            buffer.writeUtf(developerGlow.hexColor());
        }
    };
}
