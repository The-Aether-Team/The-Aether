package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;

import java.util.OptionalLong;

public abstract class AetherWorldProvider extends WorldProvider {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));

    public AetherWorldProvider(DataGenerator generator) {
        super(generator);
    }

    private static SurfaceRules.RuleSource makeStateRule(BlockState block) {
        return SurfaceRules.state(block);
    }

    protected DimensionType aetherDimensionType() {
        return DimensionType.create(
                OptionalLong.empty(), // fixed_time
                true, // has_skylight
                false, // has_ceiling
                false, // ultrawarm
                true, // natural
                1.0D, // coordinate_scale
                false, // createDragonFight
                false, // piglin_safe
                true, // bed_works
                false, // respawn_anchor_works
                false, // has_raids
                0, // min_y
                256, // height [This is min_y + max_y. This value is the total height of the building space going from the minimum height to the desired maximum build height]
                256, // logical_height [Ditto, except for processing - ticking and such]
                BlockTags.INFINIBURN_OVERWORLD,
                new ResourceLocation(Aether.MODID, "the_aether"), // effects
                0.1F // ambient_light
        );
    }

    public static NoiseGeneratorSettings aetherNoiseSettings() {
        return new NoiseGeneratorSettings(
                //new StructureSettings(Optional.empty(), Map.of(
                //        //AetherStructures.BRONZE_DUNGEON_INSTANCE, new StructureFeatureConfiguration(6, 4, 16811681)//,
                //        //AetherStructures.GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420)
                //)),
                new NoiseSettings(
                        0,
                        128,
                        new NoiseSamplingSettings(2, 1, 80, 160),
                        new NoiseSlider(-23.4375D, 64, -46),
                        new NoiseSlider(-0.234375D, 7, 1),
                        2,
                        1,
                        TerrainProvider.floatingIslands()
                ),
                AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true),
                Blocks.WATER.defaultBlockState(),
                NoiseRouterData.overworldWithoutCaves(NoiseSettings.FLOATING_ISLANDS_NOISE_SETTINGS),
                aetherSurfaceRules(),
                Integer.MIN_VALUE, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // We want to use that fancy faster algorithm [Xoroshiro]
        );
    }

    protected static SurfaceRules.RuleSource aetherSurfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        );
    }
}
