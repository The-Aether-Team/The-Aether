package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.generators.loot.*;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.loot.AetherLootContexts;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
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

public class AetherLootTableData extends LootTableProvider {
    public AetherLootTableData(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Loot Tables";
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(AetherBlockLootData::new, LootContextParamSets.BLOCK),
                Pair.of(AetherEntityLootData::new, LootContextParamSets.ENTITY),
                Pair.of(AetherDungeonLootData::new, LootContextParamSets.CHEST),
                Pair.of(AetherAdvancementLootData::new, LootContextParamSets.ADVANCEMENT_REWARD),
                Pair.of(AetherSelectorLootData::new, LootContextParamSets.SELECTOR),
                Pair.of(AetherStrippingLootData::new, AetherLootContexts.STRIPPING));
    }

    @Override
    protected void validate(@Nonnull Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) { }
}
