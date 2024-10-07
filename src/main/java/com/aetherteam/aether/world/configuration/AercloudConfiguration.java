package com.aetherteam.aether.world.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record AercloudConfiguration(int bounds, BlockStateProvider block) implements FeatureConfiguration {
    public static final Codec<AercloudConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("bounds").forGetter(AercloudConfiguration::bounds),
            BlockStateProvider.CODEC.fieldOf("blocks").forGetter(AercloudConfiguration::block)
    ).apply(instance, AercloudConfiguration::new));
}
