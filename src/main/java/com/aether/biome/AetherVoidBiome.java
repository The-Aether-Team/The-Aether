package com.aether.biome;

import com.aether.block.AetherBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

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
        this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(Blocks.WATER.getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(4)));

        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AETHER_DIRT.getDefaultState(), 32), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AMBROSIUM_ORE.getDefaultState(), 16), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 128)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ICESTONE.getDefaultState(), 16), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 128)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ZANITE_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 128)));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.GRAVITITE_ORE.getDefaultState(), 6), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 128)));

    }

}
