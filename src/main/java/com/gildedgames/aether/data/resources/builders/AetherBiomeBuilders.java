package com.gildedgames.aether.data.resources.builders;

import com.gildedgames.aether.client.AetherSoundEvents;
import com.gildedgames.aether.data.resources.registries.AetherBiomes;
import com.gildedgames.aether.data.resources.registries.AetherPlacedFeatures;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class AetherBiomeBuilders {
    public static Biome skyrootGroveBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.SKYROOT_GROVE_TREES_PLACEMENT));
    }

    public static Biome skyrootForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.SKYROOT_FOREST_TREES_PLACEMENT));
    }

    public static Biome skyrootThicketBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.SKYROOT_THICKET_TREES_PLACEMENT));
    }

    public static Biome goldenForestBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers).addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GOLDEN_FOREST_TREES_PLACEMENT));
    }

    public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder) {
        return fullDefinition(
                Biome.Precipitation.NONE,
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
                        .backgroundMusic(new Music(AetherSoundEvents.MUSIC_AETHER.getHolder().orElseThrow(), 12000, 24000, true))
                        .build(),
                new MobSpawnSettings.Builder()
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 9, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 2, 2))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 100, 4, 4))
                        .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 50, 1, 1))
                        .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.7, 0.15)
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
                        .addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherPlacedFeatures.QUICKSOIL_SHELF_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.LAKES, AetherPlacedFeatures.WATER_LAKE_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AETHER_DIRT_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ICESTONE_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_AMBROSIUM_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_ZANITE_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_COMMON_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherPlacedFeatures.ORE_GRAVITITE_DENSE_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.FLUID_SPRINGS, AetherPlacedFeatures.WATER_SPRING_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.TALL_GRASS_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.HOLIDAY_TREE_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherPlacedFeatures.FLOWER_PATCH_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.CRYSTAL_ISLAND_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.COLD_AERCLOUD_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.BLUE_AERCLOUD_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.GOLDEN_AERCLOUD_PLACEMENT)
                        .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherPlacedFeatures.PINK_AERCLOUD_PLACEMENT)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

    public static Biome fullDefinition(Biome.Precipitation precipitation, float temperature, float downfall, BiomeSpecialEffects effects, MobSpawnSettings spawnSettings, BiomeGenerationSettings generationSettings, Biome.TemperatureModifier temperatureModifier) {
        return new Biome.BiomeBuilder()
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(effects)
                .mobSpawnSettings(spawnSettings)
                .generationSettings(generationSettings)
                .temperatureAdjustment(temperatureModifier)
                .build();
    }
    
    public static BiomeSource buildAetherBiomeSource(HolderGetter<Biome> biomes) {
        final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
        return new MultiNoiseBiomeSource(new Climate.ParameterList<>(List.of(
        		Pair.of(new Climate.ParameterPoint(FULL_RANGE, Climate.Parameter.span(-1.0F, -0.7F), FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0),
                        biomes.getOrThrow(AetherBiomes.SKYROOT_GROVE)),                
                Pair.of(new Climate.ParameterPoint(FULL_RANGE, Climate.Parameter.span(-0.7F, -0.1F), FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0),
                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
                Pair.of(new Climate.ParameterPoint(FULL_RANGE, Climate.Parameter.span(-0.1F, 0.5F), FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0),
                        biomes.getOrThrow(AetherBiomes.SKYROOT_THICKET)),
                Pair.of(new Climate.ParameterPoint(FULL_RANGE, Climate.Parameter.span(0.5F, 0.85F), FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0),
                        biomes.getOrThrow(AetherBiomes.SKYROOT_FOREST)),
                Pair.of(new Climate.ParameterPoint(FULL_RANGE, Climate.Parameter.span(0.85F, 1.0F), FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0),
                        biomes.getOrThrow(AetherBiomes.GOLDEN_FOREST))
                
        )));
    }
}