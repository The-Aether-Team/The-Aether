package com.gildedgames.aether.data.resources;

import com.gildedgames.aether.Aether;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalLong;

public class AetherDimensions {
	// NOT for API - If you are an addon author using this field, expect this field to change without warning!
	private final static ResourceLocation AETHER_LEVEL_ID = new ResourceLocation(Aether.MODID, "the_aether");

	public static final Map<ResourceLocation, DimensionType> DIMENSION_TYPES = new HashMap<>();

	// Dimension Type - Specifies logic that impacts an entire dimension
	public static ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = register(AETHER_LEVEL_ID.getPath(), new DimensionType(OptionalLong.empty(), true, false, false, true, 1.0D, true, false, 0, 256, 256, BlockTags.INFINIBURN_OVERWORLD, new ResourceLocation(Aether.MODID, "the_aether"), 0.0F, new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0)));
	// Level - Actual runtime dimension
	public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);
	// Level Stem - Begins the dimension's lifecycle
	public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);

	public static ResourceKey<DimensionType> register(String name, DimensionType dimensionType) {
		ResourceLocation location = new ResourceLocation(Aether.MODID, name);
		DIMENSION_TYPES.putIfAbsent(location, dimensionType);
		return ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, location);
	}
}