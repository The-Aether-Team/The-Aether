package com.gildedgames.aether.common.world.gen.placement;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class PlacementModifiers {
    public static final PlacementModifierType<ElevationAdjustment> ELEVATION_ADJUSTMENT = register(new ResourceLocation(Aether.MODID, "elevation_adjustment"), ElevationAdjustment.CODEC);
    public static final PlacementModifierType<ElevationFilter> ELEVATION_FILTER = register(new ResourceLocation(Aether.MODID, "elevation_filter"), ElevationFilter.CODEC);
    public static final PlacementModifierType<ConfigFilter> CONFIG_FILTER = register(new ResourceLocation(Aether.MODID, "config_filter"), ConfigFilter.CODEC);
    public static final PlacementModifierType<HolidayFilter> HOLIDAY_FILTER = register(new ResourceLocation(Aether.MODID, "holiday_filter"), HolidayFilter.CODEC);
    public static final PlacementModifierType<RangeFromHeightmapPlacement> RANGE_FROM_HEIGHTMAP = register(new ResourceLocation(Aether.MODID, "range_from_heightmap"), RangeFromHeightmapPlacement.CODEC);

    // You HAVE to call this. This class won't load otherwise until this problem is properly addressed
    public static void init() { }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(ResourceLocation name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> codec);
    }
}
