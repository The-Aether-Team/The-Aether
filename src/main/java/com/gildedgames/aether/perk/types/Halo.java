package com.gildedgames.aether.perk.types;

import net.minecraft.network.FriendlyByteBuf;

public record Halo(String hexColor) {
    public static Halo read(FriendlyByteBuf buffer) {
        String hexColor = buffer.readUtf();
        return new Halo(hexColor);
    }

    public static void write(FriendlyByteBuf buffer, Halo halo) {
        buffer.writeUtf(halo.hexColor());
    }
}
