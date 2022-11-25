package com.gildedgames.aether.data.resources.builders;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class AetherNoiseBuilders {
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(AetherBlocks.AETHER_GRASS_BLOCK.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(AetherBlocks.AETHER_DIRT.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true));

    public static NoiseGeneratorSettings skylandsNoiseSettings() {
        BlockState holystone = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        return new NoiseGeneratorSettings(
                //new StructureSettings(Optional.empty(), Map.of(
                //        //AetherStructures.BRONZE_DUNGEON_INSTANCE, new StructureFeatureConfiguration(6, 4, 16811681)//,
                //        //AetherStructures.GOLD_DUNGEON.get(), new StructureFeatureConfiguration(24, 12, 120320420)
                //)),
                new NoiseSettings(0, 128, 2, 1),
                holystone,
                Blocks.WATER.defaultBlockState(),
                makeNoiseRouter(),
                aetherSurfaceRules(),
                List.of(), // spawnTarget
                -64, // seaLevel
                false, // disableMobGeneration
                false, // aquifersEnabled
                false, // oreVeinsEnabled
                false  // We want to use that fancy faster algorithm [Xoroshiro]
        );
    }

    public static SurfaceRules.RuleSource aetherSurfaceRules() {
        SurfaceRules.RuleSource surface = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), GRASS_BLOCK), DIRT);
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(BlockState block) {
        return SurfaceRules.state(block);
    }

    /**
     * Everything below is based off of NoiseRouterData
     * @see NoiseRouterData
     */
    public static NoiseRouter makeNoiseRouter() {
        DensityFunction finalDensity = buildFinalDensity();
        return noNewCaves(finalDensity);
    }

    private static DensityFunction buildFinalDensity() {
        DensityFunction density = getFunction(createKey("overworld/base_3d_noise"));
        density = DensityFunctions.mul(density, DensityFunctions.noise(getNoise(Noises.JAGGED), 3, 1));
        density = buildSlide(density, 0, 128, 72, -184, -23.4375D, 4, 32, -0.234375D);
        density = DensityFunctions.blendDensity(density);
        return DensityFunctions.mul(DensityFunctions.interpolated(density), DensityFunctions.constant(0.64D)).squeeze();
    }

    private static DensityFunction buildSlide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop, double offset1, int fromYBottom, int toYBottom, double offset2) {
        DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1, 0);
        DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0, 1);
        density = DensityFunctions.lerp(topSlide, offset1, density);
        return DensityFunctions.lerp(bottomSlide, offset2, density);
    }

    // [VANILLA COPY] - NoiseRouterData#noNewCaves() - Moved postProcess() logic to buildFinalDensity()
    private static NoiseRouter noNewCaves(DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(createKey("shift_x"));
        DensityFunction shiftZ = getFunction(createKey("shift_z"));
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, getNoise(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, getNoise(Noises.VEGETATION));
        return new NoiseRouter(DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                temperature,
                vegetation,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                finalDensity,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero());
    }

    private static DensityFunction getFunction(ResourceKey<DensityFunction> p_224466_) {
        return new DensityFunctions.HolderHolder(BuiltinRegistries.DENSITY_FUNCTION.getHolderOrThrow(p_224466_));
    }

    private static Holder<NormalNoise.NoiseParameters> getNoise(ResourceKey<NormalNoise.NoiseParameters> noiseKey) {
        return BuiltinRegistries.NOISE.getHolderOrThrow(noiseKey);
    }

    private static ResourceKey<DensityFunction> createKey(String pLocation) {
        return ResourceKey.create(Registry.DENSITY_FUNCTION_REGISTRY, new ResourceLocation(pLocation));
    }
}
