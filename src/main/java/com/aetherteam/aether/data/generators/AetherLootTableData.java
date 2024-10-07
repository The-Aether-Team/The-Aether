package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.data.generators.loot.*;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.loot.AetherLootContexts;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AetherLootTableData {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(output, AetherLoot.IMMUTABLE_LOOT_TABLES, List.of(
                new LootTableProvider.SubProviderEntry(AetherChestLoot::new, LootContextParamSets.CHEST),
                new LootTableProvider.SubProviderEntry(AetherEntityLoot::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(AetherBlockLoot::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(AetherAdvancementLoot::new, LootContextParamSets.ADVANCEMENT_REWARD),
                new LootTableProvider.SubProviderEntry(AetherSelectorLoot::new, LootContextParamSets.SELECTOR),
                new LootTableProvider.SubProviderEntry(AetherStrippingLoot::new, AetherLootContexts.STRIPPING)
        ), registries);
    }
}
