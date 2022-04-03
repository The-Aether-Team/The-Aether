package com.gildedgames.aether.common.world.gen.feature;

import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import com.mojang.serialization.Codec;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Arrays;

public class CrystalIslandFeature extends Feature<NoneFeatureConfiguration> {
    public CrystalIslandFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        for (int i = 0; i < 3; i++) {
            BlockState state;
            if (i == 0) {
                state = AetherFeatures.States.AETHER_GRASS_BLOCK;
            } else {
                state = AetherFeatures.States.HOLYSTONE;
            }
            int offset = i;
            this.setBlock(context.level(), context.origin().below(offset), state);
            Arrays.stream(Direction.values()).toList().subList(2, 6).forEach(direction -> {
                this.setBlock(context.level(), context.origin().relative(direction).below(offset), state);
                if (offset != 2) {
                    this.setBlock(context.level(), context.origin().relative(direction, 2).below(offset), state);
                    this.setBlock(context.level(), context.origin().relative(direction).relative(direction.getClockWise()).below(offset), state);
                }
            });
        }

        AetherFeatures.ConfiguredFeatures.CRYSTAL_TREE_CONFIGURED_FEATURE.value().place(context.level(), context.chunkGenerator(), context.random(), context.origin().above());

        return true;
    }
}
