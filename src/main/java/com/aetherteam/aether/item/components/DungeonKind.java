package com.aetherteam.aether.item.components;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record DungeonKind(ResourceLocation id) {
    public static final Codec<DungeonKind> CODEC = ResourceLocation.CODEC.xmap(DungeonKind::new, DungeonKind::id);

    public static final StreamCodec<RegistryFriendlyByteBuf, DungeonKind> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        DungeonKind::id,
        DungeonKind::new);
}
