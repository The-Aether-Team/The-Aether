package com.aether.block.trees;

import java.util.Random;

import com.aether.world.biome.AetherBiomeFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class GoldenOakTree extends Tree {

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return Feature.FANCY_TREE.withConfiguration(AetherBiomeFeatures.GOLDEN_OAK_TREE_CONFIG);
	}

}
