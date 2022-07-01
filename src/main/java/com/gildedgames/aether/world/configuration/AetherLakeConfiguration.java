package com.gildedgames.aether.world.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AetherLakeConfiguration(BlockStateProvider fluid, BlockStateProvider top) implements FeatureConfiguration {
    public static final Codec<AetherLakeConfiguration> CODEC = RecordCodecBuilder.create((p_190962_) -> p_190962_.group(BlockStateProvider.CODEC.fieldOf("fluid").forGetter(AetherLakeConfiguration::fluid), BlockStateProvider.CODEC.fieldOf("top").forGetter(AetherLakeConfiguration::top)).apply(p_190962_, AetherLakeConfiguration::new));
}
