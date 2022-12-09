package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.generators.loot.*;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.loot.AetherLootContexts;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.world.level.storage.loot.LootTable;

public class AetherLootTableData {
    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, BuiltInLootTables.all(), List.of(
                new LootTableProvider.SubProviderEntry(AetherDungeonLootData::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(AetherEntityLootData::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherBlockLootData::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AetherAdvancementLootData::new, LootContextParamSets.ADVANCEMENT_REWARD),
                new LootTableProvider.SubProviderEntry(AetherSelectorLootData::new, LootContextParamSets.SELECTOR),
                new LootTableProvider.SubProviderEntry(AetherStrippingLootData::new, AetherLootContexts.STRIPPING)
        ));
    }
}