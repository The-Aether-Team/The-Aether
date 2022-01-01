package com.gildedgames.aether.common.world.gen.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record BronzeDungeonConfiguration(int recursionDepth) implements FeatureConfiguration {
    public static final Codec<BronzeDungeonConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("recursion_depth").forGetter(BronzeDungeonConfiguration::recursionDepth)
    ).apply(inst, BronzeDungeonConfiguration::new));
}
