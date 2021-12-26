package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.structure.BronzeDungeonStructure;
import com.gildedgames.aether.common.world.structure.GoldDungeonStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class AetherStructures
{
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Aether.MODID);

//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> BRONZE_DUNGEON = STRUCTURES.register("bronze_dungeon", () -> new BronzeDungeonStructure(NoneFeatureConfiguration.CODEC));
//    public static final RegistryObject<StructureFeature<NoneFeatureConfiguration>> GOLD_DUNGEON = STRUCTURES.register("gold_dungeon", () -> new GoldDungeonStructure(NoneFeatureConfiguration.CODEC));

    public static final class ConfiguredStructures
    {
//        public static final ConfiguredStructureFeature<?, ?> BRONZE_DUNGEON = AetherStructures.BRONZE_DUNGEON.get().configured(FeatureConfiguration.NONE);
//        public static final ConfiguredStructureFeature<?, ?> GOLD_DUNGEON = AetherStructures.GOLD_DUNGEON.get().configured(FeatureConfiguration.NONE);
    }

    public static void registerStructures() {
//        setupStructure(BRONZE_DUNGEON.get(), new StructureFeatureConfiguration(6, 4, 16811681), false);
//        setupStructure(GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420), false);
    }

    public static void registerConfiguredStructures() {
//        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Aether.MODID, "bronze_dungeon"), BRONZE_DUNGEON.get().configured(FeatureConfiguration.NONE));
//        Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Aether.MODID, "gold_dungeon"), GOLD_DUNGEON.get().configured(FeatureConfiguration.NONE));

//        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(BRONZE_DUNGEON.get(), ConfiguredStructures.BRONZE_DUNGEON);
//        FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(GOLD_DUNGEON.get(), ConfiguredStructures.GOLD_DUNGEON);
    }

    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel) {
            ServerLevel serverWorld = (ServerLevel)event.getWorld();

            if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource && serverWorld.dimension().equals(Level.OVERWORLD)) {
                return;
            }

//            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
//            tempMap.put(AetherStructures.BRONZE_DUNGEON.get(), StructureSettings.DEFAULTS.get(AetherStructures.BRONZE_DUNGEON.get()));
//            tempMap.put(AetherStructures.GOLD_DUNGEON.get(), StructureSettings.DEFAULTS.get(AetherStructures.GOLD_DUNGEON.get()));
//            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }

    private static <F extends StructureFeature<?>> void setupStructure(F structure, StructureFeatureConfiguration structureSeparationSettings, boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

//        if (transformSurroundingLand) {
//            StructureFeature.NOISE_AFFECTING_FEATURES =
//                    ImmutableList.<StructureFeature<?>>builder()
//                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
//                            .add(structure)
//                            .build();
//        }
//
//        StructureSettings.DEFAULTS =
//                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
//                        .putAll(StructureSettings.DEFAULTS)
//                        .put(structure, structureSeparationSettings)
//                        .build();
    }
}
