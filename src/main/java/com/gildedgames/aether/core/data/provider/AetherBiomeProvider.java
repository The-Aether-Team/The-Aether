package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.core.data.AetherFeatureData;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class AetherBiomeProvider {
    public static Biome makeDefaultBiome() {
        return fullDefinition(
                Biome.Precipitation.NONE,
                Biome.BiomeCategory.NONE,
                0.8f,
                0.0f,
                new BiomeSpecialEffects.Builder()
                        .fogColor(0x93_93_bc)
                        .skyColor(0xc0_c0_ff)
                        .waterColor(0x3f_76_e4)
                        .waterFogColor(0x05_05_33)
                        .grassColorOverride(0xb1_ff_cb)
                        .foliageColorOverride(0xb1_ff_cb)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(new Music(AetherSoundEvents.MUSIC_AETHER.get(), 12000, 24000, true))
                        .build(),
                new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND_TYPE, 10, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE_TYPE, 100, 4, 4))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR_TYPE, 50, 1, 1))
                        //.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SWET_TYPE, 30, 3, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT_TYPE, 29, 1, 1))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG_TYPE, 12, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY_TYPE, 11, 3, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF_TYPE, 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA_TYPE, 10, 3, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW_TYPE, 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERWHALE_TYPE, 2, 1, 1))
                        .build(),
                new BiomeGenerationSettings.Builder()
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatureData.COLD_AERCLOUD_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatureData.BLUE_AERCLOUD_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatureData.GOLDEN_AERCLOUD_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatureData.PINK_AERCLOUD_FEATURE)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome fullDefinition(
            Biome.Precipitation precipitation,
            Biome.BiomeCategory category,
            float temperature,
            float downfall,
            BiomeSpecialEffects effects,
            MobSpawnSettings spawnSettings,
            BiomeGenerationSettings generationSettings,
            Biome.TemperatureModifier temperatureModifier
    ) {
        return new Biome.BiomeBuilder()
                .precipitation(precipitation)
                .biomeCategory(category)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build()
                ;
    }


    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(AetherBlocks.AETHER_GRASS_BLOCK.get());
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(AetherBlocks.AETHER_DIRT.get());
    private static final SurfaceRules.RuleSource QUICKSOIL = makeStateRule(AetherBlocks.QUICKSOIL.get());
    private static final SurfaceRules.RuleSource HOLYSTONE = makeStateRule(AetherBlocks.HOLYSTONE.get());

    private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
        return SurfaceRules.state(p_194811_.defaultBlockState());
    }

    public static SurfaceRules.RuleSource aetherSurfaceRules() {
        SurfaceRules.ConditionSource surfacerules$conditionsource6 = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource underwaterGrassToDirt = SurfaceRules.sequence(SurfaceRules.ifTrue(surfacerules$conditionsource6, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D), HOLYSTONE),
                SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D), underwaterGrassToDirt), HOLYSTONE);
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
    }
}
