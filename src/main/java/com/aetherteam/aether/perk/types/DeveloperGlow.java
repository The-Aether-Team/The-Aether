package com.aetherteam.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

public record DeveloperGlow(String hexColor) {
    public static DeveloperGlow read(FriendlyByteBuf buffer) {
        String hexColor = buffer.readUtf();
        return new DeveloperGlow(hexColor);
    }

    public static void write(FriendlyByteBuf buffer, DeveloperGlow developerGlow) {
        buffer.writeUtf(developerGlow.hexColor());
    }
}
