package com.gildedgames.aether.common.registry.worldgen;

import com.gildedgames.aether.Aether;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class AetherDimensions {
	// NOT for API - If you are an addon author using this field, expect this field to change without warning!
	private final static ResourceLocation AETHER_LEVEL_ID = new ResourceLocation(Aether.MODID, "the_aether");

	// Dimension Type - Specifies logic that impacts an entire dimension
	public static final ResourceKey<DimensionType> AETHER_DIMENSION_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);
	// Level Stem - Begins the dimension's lifecycle
	public static final ResourceKey<LevelStem> AETHER_LEVEL_STEM = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);
	// Level - Actual runtime dimension
	public static final ResourceKey<Level> AETHER_LEVEL = ResourceKey.create(Registry.DIMENSION_REGISTRY, AetherDimensions.AETHER_LEVEL_ID);

	public static boolean isPortalDestination(Level level) {
		return getPortalDestination().equals(level.dimension());
	}

	public static ResourceKey<Level> getPortalDestination() {
		return AetherDimensions.AETHER_LEVEL; // TODO Config
	}
}