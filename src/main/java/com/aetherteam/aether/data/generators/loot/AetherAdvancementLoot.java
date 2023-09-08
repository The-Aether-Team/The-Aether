package com.aetherteam.aether.data.generators.loot;

//import com.aetherteam.aether.AetherConfig;
//import com.aetherteam.aether.item.AetherItems;
//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.loot.conditions.ConfigEnabled;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//
//import java.util.function.BiConsumer;
//
//public class AetherAdvancementLoot implements LootTableSubProvider {
//    @Override
//    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
//        builder.accept(AetherLoot.ENTER_AETHER, LootTable.lootTable()
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.GOLDEN_PARACHUTE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
//                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.BOOK_OF_LORE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
//        );
//    }
//}
