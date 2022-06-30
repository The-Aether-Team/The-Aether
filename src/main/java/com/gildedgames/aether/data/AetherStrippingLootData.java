package com.gildedgames.aether.data;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.AetherLoot;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AetherStrippingLootData implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(AetherLoot.STRIP_GOLDEN_OAK, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get())
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
}
