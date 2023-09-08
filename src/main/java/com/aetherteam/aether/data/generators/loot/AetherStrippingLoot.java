package com.aetherteam.aether.data.generators.loot;

//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.enchantment.Enchantments;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
//import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
//
//import java.util.function.BiConsumer;
//
//public class AetherStrippingLoot implements LootTableSubProvider {
//    @Override
//    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
//        builder.accept(AetherLoot.STRIP_GOLDEN_OAK, LootTable.lootTable()
//                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get())
//                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
//                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
//    }
//}
