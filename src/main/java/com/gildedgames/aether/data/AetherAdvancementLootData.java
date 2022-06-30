package com.gildedgames.aether.data;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.loot.conditions.ConfigEnabled;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AetherAdvancementLootData implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(AetherLoot.ENTER_AETHER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.GOLDEN_PARACHUTE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.BOOK_OF_LORE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
        );
    }
}
