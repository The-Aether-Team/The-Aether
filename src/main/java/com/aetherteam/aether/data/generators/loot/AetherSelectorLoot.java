package com.aetherteam.aether.data.generators.loot;

//import com.aetherteam.aether.loot.AetherLoot;
//import com.aetherteam.aether.loot.functions.WhirlwindSpawnEntity;
//import net.minecraft.data.loot.LootTableSubProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//
//import java.util.function.BiConsumer;
//
//public class AetherSelectorLoot implements LootTableSubProvider {
//    @Override
//    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
//        builder.accept(AetherLoot.WHIRLWIND_JUNK, LootTable.lootTable()
//                .withPool(whirlwindLoot())
//        );
//        builder.accept(AetherLoot.EVIL_WHIRLWIND_JUNK, LootTable.lootTable()
//                .withPool(whirlwindLoot().add(LootItem.lootTableItem(Items.AIR).apply(WhirlwindSpawnEntity.builder(EntityType.CREEPER, 1)).setWeight(60)))
//        );
//    }
//
//    private static LootPool.Builder whirlwindLoot() {
//        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
//                .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
//                .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4))
//                .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
//                .add(LootItem.lootTableItem(Items.COAL).setWeight(9))
//                .add(LootItem.lootTableItem(Items.PUMPKIN).setWeight(2))
//                .add(LootItem.lootTableItem(Items.GRAVEL).setWeight(5))
//                .add(LootItem.lootTableItem(Items.CLAY).setWeight(11))
//                .add(LootItem.lootTableItem(Items.STICK).setWeight(12))
//                .add(LootItem.lootTableItem(Items.FLINT).setWeight(14))
//                .add(LootItem.lootTableItem(Blocks.OAK_LOG).setWeight(17))
//                .add(LootItem.lootTableItem(Blocks.SAND).setWeight(20));
//    }
//}
