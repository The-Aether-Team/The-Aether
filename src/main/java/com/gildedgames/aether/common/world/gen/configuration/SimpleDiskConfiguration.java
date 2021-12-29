package com.gildedgames.aether.common.world.gen.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SimpleDiskConfiguration(float radius, BlockStateProvider block, int clearanceRadius) implements FeatureConfiguration {
    public static final Codec<SimpleDiskConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.floatRange(1, 12).fieldOf("radius").forGetter(SimpleDiskConfiguration::radius),
            BlockStateProvider.CODEC.fieldOf("block").forGetter(SimpleDiskConfiguration::block),
            Codec.intRange(1, 12).fieldOf("clearance_radius").forGetter(SimpleDiskConfiguration::clearanceRadius)
    ).apply(instance, SimpleDiskConfiguration::new));
}
