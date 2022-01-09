package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.block.state.properties.AetherBlockStateProperties;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.world.gen.chunk.CelledSpaceGenerator;
import com.gildedgames.aether.core.data.provider.AetherWorldProvider;
import com.gildedgames.aether.core.util.math.Matrix3x3;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;

public class AetherWorldData extends AetherWorldProvider
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create(); // If desired, custom formatting rules can be set up here

    public AetherWorldData(DataGenerator generator) {
        super(generator, JsonOps.INSTANCE, GSON::toJson);
    }

    @Override
    public String getName() {
        return "Aether World Data";
    }

    @Override
    public void generate(RegistryAccess registryAccess) {
        // It if crashes on .get(), then you've got bigger problems than removing Optional here
        DimensionType dimensionType = registryAccess.registry(Registry.DIMENSION_TYPE_REGISTRY).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "aether_type"), this.aetherDimensionType())).get();
        NoiseGeneratorSettings worldNoiseSettings = registryAccess.registry(BuiltinRegistries.NOISE_GENERATOR_SETTINGS.key()).map(reg -> Registry.register(reg, new ResourceLocation(Aether.MODID, "skyland_generation"), this.aetherNoiseSettings())).get();

        NoiseBasedChunkGenerator aetherChunkGen = new NoiseBasedChunkGenerator(RegistryAccess.builtin().registryOrThrow(Registry.NOISE_REGISTRY), new FixedBiomeSource(AetherBiomeData.FLOATING_FOREST), 0L, () -> worldNoiseSettings);

        List<BlockState> states = List.of(
                Blocks.AMETHYST_BLOCK.defaultBlockState(),
                Blocks.LAPIS_BLOCK.defaultBlockState(),
                Blocks.DIAMOND_BLOCK.defaultBlockState(),
                Blocks.EMERALD_BLOCK.defaultBlockState(),
                Blocks.GOLD_BLOCK.defaultBlockState(),
                Blocks.REDSTONE_BLOCK.defaultBlockState(),
                AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)
        );

        Matrix3x3 basis = Matrix3x3.identityScaled(1f).add(
                0, 0.25f, 0,
                0, 0, 0.0625f,
                0.125f, 0, 0
        );

        CelledSpaceGenerator debug = new CelledSpaceGenerator(aetherChunkGen, basis, new BlockPos(64, 64, 64), this.aetherSurfaceRules(), states);

        this.serialize(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(Aether.MODID, "the_aether"), new LevelStem(() -> dimensionType, debug), LevelStem.CODEC);
    }
}