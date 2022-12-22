package com.gildedgames.aether.world.placementmodifier;

import com.gildedgames.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class AetherPlacementModifiers {
    public static final PlacementModifierType<ElevationAdjustmentModifier> ELEVATION_ADJUSTMENT = register(new ResourceLocation(Aether.MODID, "elevation_adjustment"), ElevationAdjustmentModifier.CODEC);
    public static final PlacementModifierType<ElevationFilter> ELEVATION_FILTER = register(new ResourceLocation(Aether.MODID, "elevation_filter"), ElevationFilter.CODEC);
    public static final PlacementModifierType<ConfigFilter> CONFIG_FILTER = register(new ResourceLocation(Aether.MODID, "config_filter"), ConfigFilter.CODEC);
    public static final PlacementModifierType<HolidayFilter> HOLIDAY_FILTER = register(new ResourceLocation(Aether.MODID, "holiday_filter"), HolidayFilter.CODEC);
    public static final PlacementModifierType<ImprovedLayerPlacementModifier> IMPROVED_LAYER_PLACEMENT = register(new ResourceLocation(Aether.MODID, "improved_layer_placement"), ImprovedLayerPlacementModifier.CODEC);

    public static final PlacementModifierType<DungeonBlacklistFilter> DUNGEON_BLACKLIST_FILTER = register(new ResourceLocation(Aether.MODID, "dungeon_blacklist_filter"), DungeonBlacklistFilter.CODEC);
    // You HAVE to call this. This class won't load otherwise until this problem is properly addressed
    public static void init() { }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(ResourceLocation name, Codec<P> codec) {
        return Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, name, () -> codec);
    }
}
