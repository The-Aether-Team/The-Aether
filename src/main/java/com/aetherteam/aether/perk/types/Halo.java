package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

/**
 * A data type to store the Halo perk's color.
 */
public record Halo(String hexColor) {
    /**
     * Reads a {@link Halo} from a {@link FriendlyByteBuf} network buffer.
     *
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @return A {@link Halo}.
     */
    public static Halo read(FriendlyByteBuf buffer) {
        String hexColor = buffer.readUtf();
        return new Halo(hexColor);
    }

    /**
     * Writes a {@link Halo} to a {@link FriendlyByteBuf} network buffer.
     *
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @param halo   A {@link Halo}.
     */
    public static void write(FriendlyByteBuf buffer, Halo halo) {
        buffer.writeUtf(halo.hexColor());
    }
}
