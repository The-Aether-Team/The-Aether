package com.gildedgames.aether.data;

import com.gildedgames.aether.AetherConfig;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.AetherLoot;
import com.gildedgames.aether.loot.conditions.ConfigEnabled;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.Nonnull;
import java.util.function.BiConsumer;

public class AetherDungeonLootData extends ChestLoot {
    @Override
    public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(AetherLoot.BRONZE_DUNGEON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PICKAXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_AXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SHOVEL.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_HOE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SWET_CAPE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_SUB_1).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_POISON_BUCKET.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_SUB_2).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_SUB_3).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_SUB_4).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_SUB_1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_AETHER_TUNE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(19))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_SUB_2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(9))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_SUB_3, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.IRON_RING.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(3))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_SUB_4, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_RING.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(9))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 6.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_REWARD_SUB_1).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_BOW.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.FLAMING_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.LIGHTNING_KNIFE.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 20.0F)))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_LANCE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.AGILITY_CAPE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SENTRY_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SHIELD_OF_REPULSION.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.HAMMER_OF_NOTCH.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.CLOUD_STAFF.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_REWARD_SUB_1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                )
        );

        builder.accept(AetherLoot.SILVER_DUNGEON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PICKAXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_BUCKET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART_SHOOTER.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_MOA_EGG.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_1).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F)))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F)))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_2).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_POISON_BUCKET.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_3).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_4).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_5).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_6).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_SUB_7).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(1)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.WHITE_MOA_EGG.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_AETHER_TUNE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(19)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_3, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(9)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_4, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_BOOTS.get()).setWeight(200))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_HELMET.get()).setWeight(100))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_LEGGINGS.get()).setWeight(50))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_CHESTPLATE.get()).setWeight(25))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(25)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_5, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.IRON_PENDANT.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(3)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_6, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_PENDANT.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(9)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_SUB_7, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_RING.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(14)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 6.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_REWARD_SUB_1).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.LIGHTNING_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_AXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_SHOVEL.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_PICKAXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_HOE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.HOLY_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_HELMET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.REGENERATION_STONE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_HELMET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_LEGGINGS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_CHESTPLATE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_GLOVES.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.INVISIBILITY_CLOAK.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_GLOVES.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_LEGGINGS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_CHESTPLATE.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_REWARD_SUB_2).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_REWARD_SUB_1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 15.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 15.0F))))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_REWARD_SUB_2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_CAPE.get()).setWeight(1).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.spawn_valkyrie_cape)))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_FEATHER.get()).setWeight(1).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.spawn_golden_feather)))
                )
        );

        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherItems.IRON_BUBBLE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.VAMPIRE_BLADE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.PIG_SLAYER.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_REWARD_SUB_1).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_REWARD_SUB_2).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.LIFE_SHARD.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_REWARD_SUB_3).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_REWARD_SUB_4).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_REWARD_SUB_5).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.OBSIDIAN_CHESTPLATE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD_SUB_1, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_HELMET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_CHESTPLATE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD_SUB_2, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_GLOVES.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD_SUB_3, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_HELMET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_LEGGINGS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_CHESTPLATE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD_SUB_4, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_GLOVES.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD_SUB_5, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_LEGGINGS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.CHAINMAIL_GLOVES.get()).setWeight(1))
                )
        );
    }
}
