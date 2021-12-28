package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.data.provider.AetherBiomeProvider;
import com.gildedgames.aether.core.data.provider.SmartRegistryWriteOps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

import java.util.Collections;
import java.util.Optional;
import java.util.OptionalLong;

public final class AetherWorldData extends SmartRegistryWriteOps<JsonElement> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // If desired, custom formatting rules can be set up here

    public AetherWorldData(DataGenerator generator) {
        super(Aether.MODID, generator, JsonOps.INSTANCE, GSON::toJson, DimensionType.registerBuiltin(new RegistryAccess.RegistryHolder()));
    }

    @Override
    public void generate(RegistryAccess registryAccess) {
        DimensionType dimensionType = DimensionType.create(
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
                BlockTags.INFINIBURN_OVERWORLD.getName(), // infiniburn
                new ResourceLocation(Aether.MODID, "the_aether"), // effects
                0.1F // ambient_light
        );

        NoiseGeneratorSettings worldNoiseSettings = new NoiseGeneratorSettings(
                new StructureSettings(Optional.empty(), Collections.emptyMap()),
                new NoiseSettings(
                        0,
                        256,
                        new NoiseSamplingSettings(2, 1, 80, 160),
                        new NoiseSlider(-23.4375D, 64, -46),
                        new NoiseSlider(-0.234375D, 7, 1),
                        2,
                        1,
                        false,
                        false,
                        false,
                        TerrainProvider.floatingIslands()
                ),
                AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true),
                Blocks.WATER.defaultBlockState(),
                AetherBiomeProvider.aetherSurfaceRules(),
                Integer.MIN_VALUE, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // noiseCavesEnabled
                false, // oreVeinsEnabled
                false, // noodleCavesEnabled
                false  // We want to use that fancy faster algorithm [Xoroshiro]
        );

        registryAccess.registry(Registry.DIMENSION_TYPE_REGISTRY).ifPresent(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "aether_type"), dimensionType));
        registryAccess.registry(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.key()).ifPresent(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "skyland_generation"), worldNoiseSettings));

        //MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(RegistryAccess.builtin().registryOrThrow(Registry.BIOME_REGISTRY), true) // Default Overworld settings
        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), new FixedBiomeSource(AetherBiomeData.FLOATING_FOREST), 0L, () -> worldNoiseSettings);

        this.serialize(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"), new LevelStem(() -> dimensionType, aetherChunkGen), LevelStem.CODEC);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }
}