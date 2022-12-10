package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.generators.loot.*;
import com.gildedgames.aether.loot.AetherLootContexts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

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