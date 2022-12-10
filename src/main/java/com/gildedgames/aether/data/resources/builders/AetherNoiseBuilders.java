package com.gildedgames.aether.data.resources.builders;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
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

    public static NoiseGeneratorSettings skylandsNoiseSettings(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        BlockState holystone = AetherBlocks.HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
        return new NoiseGeneratorSettings(
                new NoiseSettings(0, 128, 2, 1),
                holystone,
                Blocks.WATER.defaultBlockState(),
                makeNoiseRouter(densityFunctions, noise),
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
    public static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        DensityFunction finalDensity = buildFinalDensity(densityFunctions, noise);
        return noNewCaves(densityFunctions, noise, finalDensity);
    }

    private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise) {
        DensityFunction density = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("overworld/base_3d_noise")));
        density = DensityFunctions.mul(density, DensityFunctions.noise(noise.getOrThrow(Noises.JAGGED), 3, 1));
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
    private static NoiseRouter noNewCaves(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
        DensityFunction shiftX = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
        DensityFunction shiftZ = getFunction(densityFunctions, ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noise.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25D, noise.getOrThrow(Noises.VEGETATION));
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

    private static DensityFunction getFunction(HolderGetter<DensityFunction> p_256312_, ResourceKey<DensityFunction> p_256077_) {
        return new DensityFunctions.HolderHolder(p_256312_.getOrThrow(p_256077_));
    }
}
