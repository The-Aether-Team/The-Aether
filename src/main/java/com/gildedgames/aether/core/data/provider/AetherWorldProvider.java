package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.worldgen.AetherBiomes;
import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.common.registry.worldgen.AetherFeatures;
import com.gildedgames.aether.core.util.RegistryUtil;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.function.Supplier;

public abstract class AetherWorldProvider extends WorldProvider {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));

    public AetherWorldProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        RegistryAccess registryAccess = RegistryAccess.BUILTIN.get();
        DynamicOps<JsonElement> dynamicOps = RegistryOps.create(JsonOps.INSTANCE, registryAccess);

        RegistryAccess.knownRegistries().forEach(registryData -> this.dumpRegistryCap(cache, path, registryAccess, dynamicOps, registryData));
        this.dumpRegistries(registryAccess, cache, path, dynamicOps);
    }

    protected abstract void dumpRegistries(RegistryAccess registryAccess, HashCache cache, Path path, DynamicOps<JsonElement> dynamicOps);

    protected Registry<LevelStem> registerLevelStem(RegistryAccess registryAccess) {
        WritableRegistry<LevelStem> writableRegistry = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental(), null);

        Registry<DimensionType> dimensionTypeRegistry = registryAccess.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);
        Registry<StructureSet> structureSetRegistry = registryAccess.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY);
        Registry<NoiseGeneratorSettings> noiseGeneratorSettingsRegistry = registryAccess.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
        Registry<NormalNoise.NoiseParameters> noiseParametersRegistry = registryAccess.registryOrThrow(Registry.NOISE_REGISTRY);

        Holder<DimensionType> dimensionType = RegistryUtil.register(dimensionTypeRegistry, "the_aether", aetherDimensionType());
        BiomeSource source = AetherWorldProvider.buildAetherBiomeSource(biomeRegistry);
        Holder<NoiseGeneratorSettings> worldNoiseSettings = RegistryUtil.register(noiseGeneratorSettingsRegistry, "skyland_generation", aetherNoiseSettings());
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(structureSetRegistry, noiseParametersRegistry, source, 0L, worldNoiseSettings);

        writableRegistry.register(AetherDimensions.AETHER_LEVEL_STEM, new LevelStem(dimensionType, aetherChunkGen), Lifecycle.stable());
        return writableRegistry;
    }

    protected static BiomeSource buildAetherBiomeSource(Registry<Biome> registry) {
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

    protected static DimensionType aetherDimensionType() {
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

    protected static NoiseGeneratorSettings aetherNoiseSettings() {
        return new NoiseGeneratorSettings(
                //new StructureSettings(Optional.empty(), Map.of(
                //        //AetherStructures.BRONZE_DUNGEON_INSTANCE, new StructureFeatureConfiguration(6, 4, 16811681)//,
                //        //AetherStructures.GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420)
                //)),
                new NoiseSettings(
                        0,
                        128,
                        new NoiseSamplingSettings(2, 1, 80, 160),
                        new NoiseSlider(-3, 40, -22),
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

    private static SurfaceRules.RuleSource makeStateRule(BlockState block) {
        return SurfaceRules.state(block);
    }
}
