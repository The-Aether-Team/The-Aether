package com.aether.world.biome;

import com.aether.block.AetherBlocks;

import com.aether.entity.AetherEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AetherSkylandsBiome extends Biome {

    

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
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
//			Feature.FANCY_TREE
//				.withConfiguration(AetherBiomeFeatures.SKYROOT_TREE_CONFIG)
//				.withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 2))));
		AetherBiomeFeatures.addScatteredAetherTrees(this);

		AetherBiomeFeatures.addLakes(this);		
		AetherBiomeFeatures.addWaterfalls(this);
		AetherBiomeFeatures.addAetherStoneVariants(this);
		AetherBiomeFeatures.addAetherOres(this);
		
		// TODO: make this toggleable with config like in 1.12
		DefaultBiomeFeatures.addGrass(this);
		this.addCreatureSpawns();
		this.addMobSpawns();
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

	protected void addCreatureSpawns() {
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(AetherEntityTypes.PHYG, 12, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(AetherEntityTypes.FLYING_COW, 12, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(AetherEntityTypes.SHEEPUFF, 12, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(AetherEntityTypes.MOA, 10, 3, 3));
	}

	protected void addMobSpawns() {
		this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(AetherEntityTypes.ZEPHYR, 2, 1, 1));
	}

}
