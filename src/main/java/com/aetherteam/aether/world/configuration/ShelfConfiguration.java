package com.aetherteam.aether.world.configuration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record ShelfConfiguration(BlockStateProvider block, FloatProvider radius, UniformInt yRange, HolderSet<Block> validBlocks) implements FeatureConfiguration {
    public static final MapCodec<ShelfConfiguration> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BlockStateProvider.CODEC.fieldOf("block").forGetter(ShelfConfiguration::block),
            FloatProvider.CODEC.fieldOf("radius").forGetter(ShelfConfiguration::radius),
            UniformInt.CODEC.fieldOf("y_range").forGetter(ShelfConfiguration::yRange),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(ShelfConfiguration::validBlocks)
    ).apply(instance, ShelfConfiguration::new));
}
