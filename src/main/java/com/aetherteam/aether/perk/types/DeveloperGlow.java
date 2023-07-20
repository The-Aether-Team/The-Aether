package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

/**
 * A data type to store the Developer Glow perk's color.
 */
public record DeveloperGlow(String hexColor) {
    /**
     * Reads a {@link DeveloperGlow} from a {@link FriendlyByteBuf} network buffer.
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @return A {@link DeveloperGlow}.
     */
    public static DeveloperGlow read(FriendlyByteBuf buffer) {
        String hexColor = buffer.readUtf();
        return new DeveloperGlow(hexColor);
    }

    /**
     * Writes a {@link DeveloperGlow} to a {@link FriendlyByteBuf} network buffer.
     * @param buffer The {@link FriendlyByteBuf} buffer.
     * @param developerGlow A {@link DeveloperGlow}.
     */
    public static void write(FriendlyByteBuf buffer, DeveloperGlow developerGlow) {
        buffer.writeUtf(developerGlow.hexColor());
    }
}
