package com.gildedgames.aether.data.resources.registries;

import com.gildedgames.aether.Aether;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.OptionalLong;

public class AetherDimensions {
	private final static ResourceLocation AETHER_LEVEL_ID = new ResourceLocation(Aether.MODID, "the_aether");

	// DimensionType - Specifies the logic and settings for a dimension.
	public static final ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, AETHER_LEVEL_ID);
	// Level - The dimension during runtime.
	public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registries.DIMENSION, AETHER_LEVEL_ID);
	// LevelStem - The dimension during lifecycle start and datagen.
	public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, AETHER_LEVEL_ID);

	public static void bootstrap(BootstapContext<DimensionType> context) {
		context.register(AETHER_DIMENSION_TYPE, new DimensionType(
				OptionalLong.empty(),
				true,
				false,
				false,
				true,
				1.0D,
				true,
				false,
				0,
				256,
				256,
				BlockTags.INFINIBURN_OVERWORLD,
				new ResourceLocation(Aether.MODID, "the_aether"),
				0.0F,
				new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
	}
}