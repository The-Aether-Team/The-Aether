package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;

// FIXME: This class is needed because registering structures within the Minecraft core codebase is god-awful right now
//  I'm not a fan of littering the main Aether mod class with this mess, so that's why these are here instead
@Deprecated // This class is NOT going to stay.
public final class AetherStructureIngress {
    public static void registerEvents(IEventBus modEventBus) {
        AetherStructures.STRUCTURES.register(modEventBus);
        modEventBus.addListener(AetherStructureIngress::setup);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, AetherStructureIngress::addDimensionalSpacing);
        //forgeBus.addListener(EventPriority.NORMAL, /*StructureFeature*/::setupStructureSpawns);
    }

    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AetherStructures.initStructures();
            AetherConfiguredStructures.registerConfiguredStructures();
        });
    }

    /**
     * Tells the chunkgenerator which biomes our structure can spawn in.
     * Will go into the world's chunkgenerator and manually add our structure spacing.
     * If the spacing is not added, the structure doesn't spawn.
     *
     * Use this for dimension blacklists for your structure.
     * (Don't forget to attempt to remove your structure too from the map if you are blacklisting that dimension!)
     * (It might have your structure in it already.)
     *
     * Basically use this to make absolutely sure the chunkgenerator can or cannot spawn your structure.
     */
    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel serverLevel && Aether.MODID.equals(serverLevel.dimension().location().getNamespace())) {
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();

            StructureSettings worldStructureConfig = chunkGenerator.getSettings();

            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> aetherStructure2BiomeMap = new HashMap<>();

            for(Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                var biomeKey = biomeEntry.getKey();
                if (Aether.MODID.equals(biomeKey.location().getNamespace())) { // If the biome has our namespace on it, add it
                    associateBiomeToConfiguredStructure(aetherStructure2BiomeMap, AetherConfiguredStructures.CONFIGURED_BRONZE_DUNGEON, biomeKey);
                    associateBiomeToConfiguredStructure(aetherStructure2BiomeMap, AetherConfiguredStructures.CONFIGURED_GOLD_DUNGEON, biomeKey);
                }
            }

            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !aetherStructure2BiomeMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

            aetherStructure2BiomeMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
            tempMap.putIfAbsent(AetherStructures.BRONZE_DUNGEON.get(), StructureSettings.DEFAULTS.get(AetherStructures.BRONZE_DUNGEON.get()));
            tempMap.putIfAbsent(AetherStructures.GOLD_DUNGEON.get(), StructureSettings.DEFAULTS.get(AetherStructures.GOLD_DUNGEON.get()));
            worldStructureConfig.structureConfig = tempMap;
        }
    }

    /**
     * Helper method that handles setting up the map to multimap relationship to help prevent issues.
     */
    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> aetherStructureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
        aetherStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = aetherStructureToMultiMap.get(configuredStructureFeature.feature);
        if(configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            Aether.LOGGER.error("""
                    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                    The two conflicting ConfiguredStructures are: {}, {}
                    The biome that is attempting to be shared: {}
                """,
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
                    biomeRegistryKey
            );
        }
        else{
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
        }
    }

    private AetherStructureIngress() {
    }
}
