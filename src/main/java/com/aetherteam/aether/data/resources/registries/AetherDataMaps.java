package com.aetherteam.aether.data.resources.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.fabric.registries.datamaps.DataMapType;
import com.aetherteam.nitrogen.fabric.registries.datamaps.builtin.FurnaceFuel;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class AetherDataMaps {
    public static final DataMapType<Item, FurnaceFuel> ALTAR_FUEL = DataMapType.builder(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "altar_fuel"), Registries.ITEM, FurnaceFuel.CODEC).synced(FurnaceFuel.BURN_TIME_CODEC, false).build();
    public static final DataMapType<Item, FurnaceFuel> FREEZER_FUEL = DataMapType.builder(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "freezer_fuel"), Registries.ITEM, FurnaceFuel.CODEC).synced(FurnaceFuel.BURN_TIME_CODEC, false).build();
    public static final DataMapType<Item, FurnaceFuel> INCUBATOR_FUEL = DataMapType.builder(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "incubator_fuel"), Registries.ITEM, FurnaceFuel.CODEC).synced(FurnaceFuel.BURN_TIME_CODEC, false).build();
}
