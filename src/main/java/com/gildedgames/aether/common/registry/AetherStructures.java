package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.world.structure.BronzeDungeonStructure;
import com.gildedgames.aether.common.world.structure.GoldDungeonStructure;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class AetherStructures
{
    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Aether.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> BRONZE_DUNGEON = STRUCTURES.register("bronze_dungeon", () -> new BronzeDungeonStructure(NoFeatureConfig.CODEC));
    public static final RegistryObject<Structure<NoFeatureConfig>> GOLD_DUNGEON = STRUCTURES.register("gold_dungeon", () -> new GoldDungeonStructure(NoFeatureConfig.CODEC));

    public static final class ConfiguredStructures
    {
        public static final StructureFeature<?, ?> BRONZE_DUNGEON = AetherStructures.BRONZE_DUNGEON.get().configured(IFeatureConfig.NONE);
        public static final StructureFeature<?, ?> GOLD_DUNGEON = AetherStructures.GOLD_DUNGEON.get().configured(IFeatureConfig.NONE);
    }

    public static void registerStructures() {
        setupStructure(BRONZE_DUNGEON.get(), new StructureSeparationSettings(6, 4, 16811681), false);
        setupStructure(GOLD_DUNGEON.get(), new StructureSeparationSettings(24, 12, 120320420), false);
    }

    public static void registerConfiguredStructures() {
        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Aether.MODID, "bronze_dungeon"), BRONZE_DUNGEON.get().configured(IFeatureConfig.NONE));
        Registry.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Aether.MODID, "gold_dungeon"), GOLD_DUNGEON.get().configured(IFeatureConfig.NONE));

        FlatGenerationSettings.STRUCTURE_FEATURES.put(BRONZE_DUNGEON.get(), ConfiguredStructures.BRONZE_DUNGEON);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(GOLD_DUNGEON.get(), ConfiguredStructures.GOLD_DUNGEON);
    }

    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.put(AetherStructures.BRONZE_DUNGEON.get(), DimensionStructuresSettings.DEFAULTS.get(AetherStructures.BRONZE_DUNGEON.get()));
            tempMap.put(AetherStructures.GOLD_DUNGEON.get(), DimensionStructuresSettings.DEFAULTS.get(AetherStructures.GOLD_DUNGEON.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }

    private static <F extends Structure<?>> void setupStructure(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<Structure<?>>builder()
                            .addAll(Structure.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        DimensionStructuresSettings.DEFAULTS =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.DEFAULTS)
                        .put(structure, structureSeparationSettings)
                        .build();
    }
}
