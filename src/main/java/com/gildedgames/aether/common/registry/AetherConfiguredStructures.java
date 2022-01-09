package com.gildedgames.aether.common.registry;

import com.gildedgames.aether.Aether;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class AetherConfiguredStructures {
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_BRONZE_DUNGEON = AetherStructures.BRONZE_DUNGEON.get()
            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_GOLD_DUNGEON = AetherStructures.GOLD_DUNGEON.get()
            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(Aether.MODID, "configured_bronze_dungeon"), CONFIGURED_BRONZE_DUNGEON);
        Registry.register(registry, new ResourceLocation(Aether.MODID, "configured_gold_dungeon"), CONFIGURED_GOLD_DUNGEON);
    }
}
