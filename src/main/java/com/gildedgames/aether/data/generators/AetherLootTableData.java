package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.data.generators.loot.*;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.loot.AetherLootContexts;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class AetherLootTableData {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, AetherLoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(AetherDungeonLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(AetherEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AetherAdvancementLoot::new, LootContextParamSets.ADVANCEMENT_REWARD),
                new LootTableProvider.SubProviderEntry(AetherSelectorLoot::new, LootContextParamSets.SELECTOR),
                new LootTableProvider.SubProviderEntry(AetherStrippingLoot::new, AetherLootContexts.STRIPPING)
        ));
    }
}