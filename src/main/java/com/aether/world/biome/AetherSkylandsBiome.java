package com.aether.world.biome;

import com.aether.block.AetherBlocks;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;

public class AetherSkylandsBiome extends Biome {

    public static final OreFeatureConfig.FillerBlockType HOLYSTONE_FILLER = OreFeatureConfig.FillerBlockType.create("HOLYSTONE", "holystone", new BlockMatcher(AetherBlocks.HOLYSTONE));
    
    public static final TreeFeatureConfig SKYROOT_CONFIG = new TreeFeatureConfig.Builder(
																	/*trunkProvider:*/ new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LOG.getDoubleDropsState()),
																	/*leavesProvider:*/ new SimpleBlockStateProvider(AetherBlocks.SKYROOT_LEAVES.getDefaultState()),
																	/*foliagePlacer:*/ new BlobFoliagePlacer(2, 0))
																.baseHeight(4)
																.heightRandA(2)
																.foliageHeight(3)
																.ignoreVines()
																.setSapling((IPlantable)Blocks.OAK_SAPLING /* TODO AetherBlocks.SKYROOT_SAPLING */)
																.build();
    public static final TreeFeatureConfig GOLDEN_OAK_CONFIG = (new TreeFeatureConfig.Builder(
																	/*trunkProvider:*/ new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LOG.getDoubleDropsState()),
																	/*leavesProvider:*/ new SimpleBlockStateProvider(AetherBlocks.GOLDEN_OAK_LEAVES.getDefaultState()),
																	/*foliagePlacer:*/ new BlobFoliagePlacer(3, 0)))
																.baseHeight(4)
																.heightRandA(2)
																.foliageHeight(3)
																.ignoreVines()
																.setSapling((IPlantable)Blocks.OAK_SAPLING /* TODO AetherBlocks.GOLDEN_OAK_SAPLING */)
																.build();
	public static final LiquidsConfig AETHER_SPRING_CONFIG = new LiquidsConfig(Fluids.WATER.getDefaultState(), 
																	/*needsBlockBelow:*/ false,
																	/*rockAmount:*/ 4,
																	/*holeAmount:*/ 1,
																	/*acceptedBlocks:*/ ImmutableSet.of(AetherBlocks.AETHER_DIRT, AetherBlocks.HOLYSTONE));

	public AetherSkylandsBiome() {
		super(new Biome.Builder()
			.surfaceBuilder(new DefaultSurfaceBuilder(SurfaceBuilderConfig::deserialize),
				new SurfaceBuilderConfig(
					/*topMaterial:*/ AetherBlocks.AETHER_GRASS_BLOCK.getDoubleDropsState(),
					/*underMaterial:*/ AetherBlocks.AETHER_DIRT.getDoubleDropsState(),
					/*underWaterMaterial:*/ AetherBlocks.AETHER_DIRT.getDoubleDropsState()))
			.precipitation(RainType.RAIN)
			.category(Category.NONE)
			.depth(0.125F)
			.scale(0.05F)
			.temperature(0.8F)
			.downfall(0.4F)
			.waterColor(0x3F76E4)
			.waterFogColor(0x050533));
		
		// skyroot trees
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
			Feature.NORMAL_TREE
				.withConfiguration(SKYROOT_CONFIG)
				.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 2))));

		// waterfalls
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,
			Feature.LAKE
				.withConfiguration(new BlockStateFeatureConfig(Blocks.WATER.getDefaultState()))
				.withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
		
		// water springs
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
			Feature.SPRING_FEATURE
				.withConfiguration(AETHER_SPRING_CONFIG)
				.withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(40, 8, 16, 256))));

		// dirt clumps
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AETHER_DIRT.getDoubleDropsState(), 32))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
		
		// ambrosium ore
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.AMBROSIUM_ORE.getDoubleDropsState(), 16))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
		
		// icestone
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ICESTONE.getDefaultState(), 16))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
		
		// zanite ore
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.ZANITE_ORE.getDefaultState(), 8))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));
		
		// gravitite ore
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
			Feature.ORE
				.withConfiguration(new OreFeatureConfig(HOLYSTONE_FILLER, AetherBlocks.GRAVITITE_ORE.getDefaultState(), 6))
				.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 128))));

		// TODO: make this toggleable with config like in 1.12
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
		return 0xB1FFCB;
	}

}
