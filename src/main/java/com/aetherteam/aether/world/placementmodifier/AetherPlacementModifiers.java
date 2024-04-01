package com.aetherteam.aether.world.placementmodifier;

import com.aetherteam.aether.Aether;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, Aether.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigFilter>> CONFIG_FILTER = PLACEMENT_MODIFIERS.register("config_filter", () -> () -> ConfigFilter.CODEC);
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<HolidayFilter>> HOLIDAY_FILTER = PLACEMENT_MODIFIERS.register("holiday_filter", () -> () -> HolidayFilter.CODEC);
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ImprovedLayerPlacementModifier>> IMPROVED_LAYER_PLACEMENT = PLACEMENT_MODIFIERS.register("improved_layer_placement", () -> () -> ImprovedLayerPlacementModifier.CODEC);
    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<DungeonBlacklistFilter>> DUNGEON_BLACKLIST_FILTER = PLACEMENT_MODIFIERS.register("dungeon_blacklist_filter", () -> () -> DungeonBlacklistFilter.CODEC);
}
