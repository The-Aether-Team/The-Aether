package com.gildedgames.aether.common.world.builders;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.worldgen.AetherBiomes;
import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import com.gildedgames.aether.common.world.builders.AetherFeatureBuilders;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class AetherBiomeBuilders {
    public static BiomeSource buildAetherBiomeSource(Registry<Biome> registry) {
        final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);

        return new MultiNoiseBiomeSource(new Climate.ParameterList<>(List.of(
                Pair.of(
                        new Climate.ParameterPoint(
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                Climate.Parameter.span(1.0F, 2.0F),
                                0
                        ), Holder.Reference.createStandAlone(registry, AetherBiomes.Keys.GOLDEN_FOREST)
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                Climate.Parameter.span(0.5F, 1.0F),
                                0
                        ), Holder.Reference.createStandAlone(registry, AetherBiomes.Keys.SKYROOT_FOREST)
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                Climate.Parameter.span(-0.1F, 0.5F),
                                0
                        ), Holder.Reference.createStandAlone(registry, AetherBiomes.Keys.SKYROOT_THICKET)
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                Climate.Parameter.span(-0.7F, -0.1F),
                                0
                        ), Holder.Reference.createStandAlone(registry, AetherBiomes.Keys.SKYROOT_FOREST)
                ),
                Pair.of(
                        new Climate.ParameterPoint(
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                FULL_RANGE,
                                Climate.Parameter.span(-2.0F, -0.7F),
                                0
                        ), Holder.Reference.createStandAlone(registry, AetherBiomes.Keys.SKYROOT_GROVE)
                )
        )));
    }

    // No fancy variations with trees are required, so inline these different tree decoration patterns instead
    public static Biome skyrootGroveBiome() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(AetherFeatureBuilders.treeBlendDensity(2))));
    }

    public static Biome skyrootForestBiome() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(AetherFeatureBuilders.treeBlendDensity(2)))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherFeatures.ConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(1),
                        AetherFeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get()))))));
    }

    public static Biome skyrootThicketBiome() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherFeatures.ConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(1),
                        AetherFeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get())))))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(AetherFeatureBuilders.treeBlendDensity(3))));
    }

    public static Biome goldenForestBiome() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherFeatures.ConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(2),
                        AetherFeatureBuilders.copyBlockSurvivability(AetherBlocks.GOLDEN_OAK_SAPLING.get())))))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(AetherFeatureBuilders.treeBlendDensity(2))));
    }

    public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder) {
        return fullDefinition(
                Biome.Precipitation.NONE,
                Biome.BiomeCategory.NONE,
                0.8F,
                0.0F,
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
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 9, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 100, 4, 4))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 50, 1, 1))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.BLUE_SWET.get(), 15, 3, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 15, 3, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT.get(), 29, 1, 1))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 12, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 11, 3, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 10, 3, 3))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 10, 4, 4))
                        .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERWHALE.get(), 2, 1, 1))
                        .build(),
                builder
                        //// TODO GenerationStep.Decoration.RAW_GENERATION
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherFeatures.PlacedFeatures.QUICKSOIL_SHELF_PLACED_FEATURE)
                        ////  "aether:crystal_tree"
                        //// TODO GenerationStep.Decoration.LAKES
                        ////  "aether:water_lake"
                        //// TODO GenerationStep.Decoration.UNDERGROUND_ORES
                        ////   "aether:ore_aether_dirt",
                        ////   "aether:ore_icestone",
                        ////   "aether:ore_ambrosium",
                        ////   "aether:ore_zanite",
                        ////   "aether:ore_gravitite"
                        //// TODO GenerationStep.Decoration.FLUID_SPRINGS
                        ////   "aether:spring_water"
                        //// TODO GenerationStep.Decoration.VEGETAL_DECORATION
                        ////   "aether:grass_patch",
                        ////   "aether:tall_grass_patch",
                        ////   "aether:aether_skylands_flowers",
                        ////   "aether:tree_skyroot",
                        ////   "aether:tree_golden_oak",
                        ////   "aether:holiday_tree"
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.PlacedFeatures.COLD_AERCLOUD_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.PlacedFeatures.BLUE_AERCLOUD_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.PlacedFeatures.GOLDEN_AERCLOUD_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.PlacedFeatures.PINK_AERCLOUD_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(AetherFeatures.PlacedFeatures.FLOWER_PATCH_PLACED_FEATURE.value())) //this probably isnt ideal but it can be looked into later
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_AETHER_DIRT_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_ICESTONE_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_AMBROSIUM_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_ZANITE_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_GRAVITITE_COMMON_PLACED_FEATURE)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.PlacedFeatures.ORE_GRAVITITE_DENSE_PLACED_FEATURE)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome fullDefinition(Biome.Precipitation precipitation, Biome.BiomeCategory category, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .precipitation(precipitation)
                .biomeCategory(category)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }
}