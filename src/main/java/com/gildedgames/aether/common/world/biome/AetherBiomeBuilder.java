package com.gildedgames.aether.common.world.biome;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherEntityTypes;
import com.gildedgames.aether.common.registry.worldgen.AetherConfiguredFeatures;
import com.gildedgames.aether.common.world.feature.FeatureBuilders;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class AetherBiomeBuilder {
    // No fancy variations with trees are required, so inline these different tree decoration patterns instead
    public static Biome skyrootGrove() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2))));
    }

    public static Biome skyrootForest() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2)))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(1),
                        FeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get()))))));
    }

    public static Biome skyrootThicket() {
        return makeDefaultBiome(new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(1),
                        FeatureBuilders.copyBlockSurvivability(AetherBlocks.SKYROOT_SAPLING.get())))))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(3))));
    }

    public static Biome goldenForest() {
        return makeDefaultBiome(0xb1_ff_cb, new BiomeGenerationSettings.Builder()
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(new PlacedFeature(Holder.hackyErase(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURED_FEATURE), List.of(
                        CountOnEveryLayerPlacement.of(2),
                        FeatureBuilders.copyBlockSurvivability(AetherBlocks.GOLDEN_OAK_SAPLING.get())))))
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(FeatureBuilders.treeBlendDensity(2))));
    }

    public static Biome makeDefaultBiome(BiomeGenerationSettings.Builder builder) {
        return makeDefaultBiome(0xb1_ff_cb, builder);
    }

    public static Biome makeDefaultBiome(int grassColor, BiomeGenerationSettings.Builder builder) {
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
                        .grassColorOverride(grassColor)
                        .foliageColorOverride(grassColor)
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
                        //.addFeature(GenerationStep.Decoration.RAW_GENERATION, AetherFeatureData.QUICKSOIL_SHELF_FEATURE)
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
                        //.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.Placements.COLD_AERCLOUD_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.Placements.BLUE_AERCLOUD_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.Placements.GOLDEN_AERCLOUD_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, AetherFeatures.Placements.PINK_AERCLOUD_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherFeatures.Placements.SKYROOT_TREE_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherFeatures.Placements.GOLDEN_OAK_TREE_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AetherFeatures.Placements.FLOWER_PATCH_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_AETHER_DIRT_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_ICESTONE_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_AMBROSIUM_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_ZANITE_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_GRAVITITE_COMMON_PLACED_FEATURE)
                        //.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AetherFeatures.Placements.ORE_GRAVITITE_DENSE_PLACED_FEATURE)
                        .build(),
                Biome.TemperatureModifier.NONE
        );
    }

//    public static Biome makeEmptyBiome() {
//        return AetherBiomeBuilder.fullDefinition(
//                Biome.Precipitation.NONE,
//                Biome.BiomeCategory.NONE,
//                0,
//                0,
//                new BiomeSpecialEffects.Builder().fogColor(0).waterColor(0).waterFogColor(0).skyColor(0).build(),
//                new MobSpawnSettings.Builder().build(),
//                new BiomeGenerationSettings.Builder().build(),
//                Biome.TemperatureModifier.NONE
//        );
//    }

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
}