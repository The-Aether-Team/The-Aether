package com.gildedgames.aether.world.builders;

import com.gildedgames.aether.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;

import java.util.List;

public class AetherNoiseBuilders {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));

    public static NoiseGeneratorSettings skylandsNoiseSettings() {
        BlockState holystone = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        return new NoiseGeneratorSettings(
                //new StructureSettings(Optional.empty(), Map.of(
                //        //AetherStructures.BRONZE_DUNGEON_INSTANCE, new StructureFeatureConfiguration(6, 4, 16811681)//,
                //        //AetherStructures.GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420)
                //)),
                new NoiseSettings(0, 128, 2, 1),
                holystone,
                Blocks.WATER.defaultBlockState(),
                NoiseRouterData.floatingIslands(BuiltinRegistries.DENSITY_FUNCTION),
                aetherSurfaceRules(),
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // We want to use that fancy faster algorithm [Xoroshiro]
        );
    }

    public static SurfaceRules.RuleSource aetherSurfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(BlockState block) {
        return SurfaceRules.state(block);
    }
}
