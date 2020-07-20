package com.aether.world.biome;

import com.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.IPlantable;

public class AetherBiomeFeatures {
	
	public static final OreFeatureConfig.FillerBlockType HOLYSTONE_FILLER = OreFeatureConfig.FillerBlockType.create("HOLYSTONE", "holystone", new BlockMatcher(AetherBlocks.HOLYSTONE));
    
    public static final TreeFeatureConfig SKYROOT_TREE_CONFIG = new TreeFeatureConfig.Builder(
																	/*trunkProvider:*/ new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LOG.getDoubleDropsState()),
																	/*leavesProvider:*/ new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LEAVES.getDefaultState()),
																	/*foliagePlacer:*/ new BlobFoliagePlacer(2, 0))
																.baseHeight(4)
																.heightRandA(2)
																.foliageHeight(3)
																.ignoreVines()
																.setSapling((IPlantable)AetherBlocks.SKYROOT_SAPLING)
																.build();
    public static final TreeFeatureConfig GOLDEN_OAK_TREE_CONFIG = new TreeFeatureConfig.Builder(
																	/*trunkProvider:*/ new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LOG.getDoubleDropsState()),
																	/*leavesProvider:*/ new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LEAVES.getDefaultState()),
																	/*foliagePlacer:*/ new BlobFoliagePlacer(3, 0))
																.baseHeight(4)
																.heightRandA(2)
																.foliageHeight(3)
																.ignoreVines()
																.setSapling((IPlantable)AetherBlocks.GOLDEN_OAK_SAPLING)
																.build();
	public static final LiquidsConfig AETHER_SPRING_CONFIG = new LiquidsConfig(Fluids.WATER.getDefaultState(), 
																	/*needsBlockBelow:*/ false,
																	/*rockAmount:*/ 4,
																	/*holeAmount:*/ 1,
																	/*acceptedBlocks:*/ ImmutableSet.of(AetherBlocks.AETHER_DIRT, AetherBlocks.HOLYSTONE));
	
	public static void addScatteredAetherTrees(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, 
			Feature.RANDOM_SELECTOR
				.withConfiguration(new MultipleRandomFeatureConfig(
					ImmutableList.of(
						Feature.FANCY_TREE
							.withConfiguration(GOLDEN_OAK_TREE_CONFIG)
							.withChance(0.1F)), 
					Feature.NORMAL_TREE
						.withConfiguration(SKYROOT_TREE_CONFIG)))
				.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 2))));
	}
	
	public static void addAetherOres(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AMBROSIUM_ORE.getDoubleDropsState(), 16))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ZANITE_ORE.getDefaultState(), 8))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.GRAVITITE_ORE.getDefaultState(), 6))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
	}
	
	public static void addAetherStoneVariants(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AETHER_DIRT.getDoubleDropsState(), 32))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, 
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ICESTONE.getDefaultState(), 16))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
	}
	
	public static void addLakes(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
			Feature.LAKE
				.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState()))
				.withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
	}
	
	public static void addWaterfalls(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
			Feature.SPRING_FEATURE
				.withConfiguration(AETHER_SPRING_CONFIG)
				.withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(40, 8, 16, 256))));
	}
	
}
