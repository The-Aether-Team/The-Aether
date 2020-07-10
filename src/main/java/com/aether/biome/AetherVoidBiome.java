package com.aether.biome;

import com.aether.block.AetherBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherVoidBiome extends Biome {

    public static final OreFeatureConfig.FillerBlockType HOLYSTONE_FILLER = OreFeatureConfig.FillerBlockType.create("HOLYSTONE", "holystone", new BlockMatcher(AetherBlocks.HOLYSTONE));

    public AetherVoidBiome() {
        super((new Biome.Builder())
                .surfaceBuilder(new DefaultSurfaceBuilder(SurfaceBuilderConfig::deserialize), new SurfaceBuilderConfig(AetherBlocks.AETHER_GRASS_BLOCK.getDefaultState(), AetherBlocks.AETHER_DIRT.getDefaultState(), AetherBlocks.AETHER_DIRT.getDefaultState()))
                .precipitation(RainType.RAIN)
                .category(Category.NONE)
                .depth(0.125F)
                .scale(0.05F)
                .temperature(0.8F)
                .downfall(0.4F)
                .waterColor(4159204)
                .waterFogColor(329011)
                .parent((null))
        );
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState())).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));

        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AETHER_DIRT.getDefaultState(), 32)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AMBROSIUM_ORE.getDefaultState(), 16)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ICESTONE.getDefaultState(), 16)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ZANITE_ORE.getDefaultState(), 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.GRAVITITE_ORE.getDefaultState(), 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));

        //TODO: make this toggleable with config like in 1.12
        DefaultBiomeFeatures.addGrass(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getSkyColor() {
        return 0xBCBCFA;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(double x, double z) {
        return 0xb1ffcb;
    }

}
