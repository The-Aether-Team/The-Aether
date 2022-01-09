package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.structure.BronzeDungeonStructure;
import com.gildedgames.aether.common.world.structure.GoldDungeonStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AetherStructures {
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Aether.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> BRONZE_DUNGEON = STRUCTURES.register("bronze_dungeon", BronzeDungeonStructure::new);
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> GOLD_DUNGEON = STRUCTURES.register("gold_dungeon", GoldDungeonStructure::new);

    public static void initStructures() {
        setupMapSpacingAndLand(AetherStructures.BRONZE_DUNGEON.get(), new StructureFeatureConfiguration(6, 4, 16811681), true);
        setupMapSpacingAndLand(AetherStructures.GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420), false);
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration structureFeatureConfiguration, boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand)
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder()
                    .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                    .add(structure)
                    .build();

        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().stream().map(Map.Entry::getValue).forEach(settings -> AetherStructures.attachStructureToNoiseGen(structure, structureFeatureConfiguration, settings));
    }

    public static void attachStructureToNoiseGen(StructureFeature<?> structure, StructureFeatureConfiguration structureFeatureConfiguration, NoiseGeneratorSettings settings) {
        Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.structureSettings().structureConfig();

        if (structureMap instanceof ImmutableMap) {
            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
            tempMap.put(structure, structureFeatureConfiguration);
            settings.structureSettings().structureConfig = tempMap;
        } else {
            structureMap.put(structure, structureFeatureConfiguration);
        }
    }
}
