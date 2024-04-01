package com.aetherteam.aether.api.registers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record MoaType(ItemStack egg, int maxJumps, float speed, int spawnChance, ResourceLocation moaTexture, ResourceLocation saddleTexture) {
    public static final Codec<MoaType> CODEC =
        RecordCodecBuilder.create(in -> in.group(
            ItemStack.SINGLE_ITEM_CODEC.fieldOf("egg").forGetter(MoaType::egg),
            Codec.INT.fieldOf("max_jumps").forGetter(MoaType::maxJumps),
            Codec.FLOAT.fieldOf("speed").forGetter(MoaType::speed),
            Codec.INT.fieldOf("spawn_chance").forGetter(MoaType::spawnChance),
            ResourceLocation.CODEC.fieldOf("moa_texture").forGetter(MoaType::moaTexture),
            ResourceLocation.CODEC.fieldOf("saddle_texture").forGetter(MoaType::saddleTexture)
        ).apply(in, MoaType::new));
}
