package com.aetherteam.aether.world.configuration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AetherLakeConfiguration(BlockStateProvider fluid, BlockStateProvider top) implements FeatureConfiguration {
    public static final MapCodec<AetherLakeConfiguration> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("fluid").forGetter(AetherLakeConfiguration::fluid),
            BlockStateProvider.CODEC.fieldOf("top").forGetter(AetherLakeConfiguration::top)
    ).apply(instance, AetherLakeConfiguration::new));
}
