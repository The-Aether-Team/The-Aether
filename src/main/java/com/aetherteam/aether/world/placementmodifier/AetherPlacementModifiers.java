package com.aetherteam.aether.world.placementmodifier;

import com.aetherteam.aether.Aether;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class AetherPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, Aether.MODID);

    public static final Supplier<PlacementModifierType<ConfigFilter>> CONFIG_FILTER = PLACEMENT_MODIFIERS.register("config_filter", () -> () -> ConfigFilter.CODEC);
    public static final Supplier<PlacementModifierType<HolidayFilter>> HOLIDAY_FILTER = PLACEMENT_MODIFIERS.register("holiday_filter", () -> () -> HolidayFilter.CODEC);
    public static final Supplier<PlacementModifierType<ImprovedLayerPlacementModifier>> IMPROVED_LAYER_PLACEMENT = PLACEMENT_MODIFIERS.register("improved_layer_placement", () -> () -> ImprovedLayerPlacementModifier.CODEC);
    public static final Supplier<PlacementModifierType<DungeonBlacklistFilter>> DUNGEON_BLACKLIST_FILTER = PLACEMENT_MODIFIERS.register("dungeon_blacklist_filter", () -> () -> DungeonBlacklistFilter.CODEC);
}
