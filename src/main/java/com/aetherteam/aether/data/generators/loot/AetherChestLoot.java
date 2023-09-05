package com.aetherteam.aether.data.generators.loot;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import com.aetherteam.aether.loot.conditions.ConfigEnabled;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class AetherChestLoot implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
        builder.accept(AetherLoot.BRONZE_DUNGEON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_LOOT).setWeight(8))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_DISC).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_TRASH).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PICKAXE.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_AXE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SWORD.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.SWET_CAPE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ICE_PENDANT.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SHOVEL.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_HOE.get()).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_PICKAXE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_POISON_BUCKET.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.IRON_RING.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.HOLYSTONE_PICKAXE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.ICESTONE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 4.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(2))
                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_RING.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.SKYROOT_PLANKS.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_TRASH, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.SKYROOT_PLANKS.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_PICKAXE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_DISC, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_AETHER_TUNE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(Items.MUSIC_DISC_CAT).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 10.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 16.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_PICKAXE.get()).setWeight(2))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_NEPTUNE).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_TREASURE).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 2.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_GUMMIES).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_HELMET.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_CHESTPLATE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_LEGGINGS.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_BOOTS.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GLOVES.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_RING.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PENDANT.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.SHIELD_OF_REPULSION.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.CLOUD_STAFF.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_BOW.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.FLAMING_SWORD.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.AGILITY_CAPE.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_LANCE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.HAMMER_OF_KINGBDOGZ.get()).setWeight(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.BRONZE_DUNGEON_NEPTUNE).setWeight(8))
                        .add(LootItem.lootTableItem(AetherItems.SENTRY_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.SENTRY_STONE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.CARVED_STONE.get()).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 15.0F))
                        .add(LootItem.lootTableItem(AetherItems.LIGHTNING_KNIFE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).setWeight(3))
                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.SENTRY_STONE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_TREASURE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_LANCE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.HAMMER_OF_KINGBDOGZ.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.SENTRY_BOOTS.get()).setWeight(3))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_NEPTUNE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_HELMET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_CHESTPLATE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_LEGGINGS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_BOOTS.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.NEPTUNE_GLOVES.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.BRONZE_DUNGEON_GUMMIES, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 4.0F))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(2))
                )
        );

        builder.accept(AetherLoot.SILVER_DUNGEON, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_LOOT).setWeight(8))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_DISC).setWeight(2))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_TRASH).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SWORD.get()).setWeight(6))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART_SHOOTER.get()).setWeight(6))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART_SHOOTER.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART_SHOOTER.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.WHITE_MOA_EGG.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PICKAXE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_SWORD.get()).setWeight(2))
                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(2))
                        .add(LootItem.lootTableItem(Items.SHIELD).setWeight(2))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 6.0F))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_MOA_EGG.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_REMEDY_BUCKET.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_PENDANT.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.IRON_PENDANT.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_PENDANT.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_RING.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.AECHOR_PETAL.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.HEALING_STONE.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_BERRY.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(2))
                        .add(LootItem.lootTableItem(Items.ARROW).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_RING.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.PILLAR.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.PILLAR_TOP.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.ANGELIC_STONE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.LIGHT_ANGELIC_STONE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_TRASH, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 12.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3))
                        .add(LootItem.lootTableItem(Items.FEATHER).setWeight(2))
                        .add(LootItem.lootTableItem(Items.STRING).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.PILLAR.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.PILLAR_TOP.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.ANGELIC_STONE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherBlocks.LIGHT_ANGELIC_STONE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.HOLYSTONE_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_DISC, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_AETHER_TUNE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get()).setWeight(2))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 10.0F))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 16.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(AetherItems.HOLYSTONE_SWORD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.HEALING_STONE.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_VALKYRIE).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_TREASURE).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_GUMMIES).setWeight(1))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(6.0F, 10.0F))
                        .add(LootItem.lootTableItem(AetherItems.LIGHTNING_SWORD.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.HOLY_SWORD.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.INVISIBILITY_CLOAK.get()).setWeight(6))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_GRAVITITE).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART_SHOOTER.get()).setWeight(8))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART_SHOOTER.get()).setWeight(6))
                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(4))
                        .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(4))
                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(6))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_AETHER_TUNE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.MUSIC_DISC_ASCENDING_DAWN.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_BERRY.get()).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_BERRY.get()).setWeight(8).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 10.0F))))
                        .add(LootItem.lootTableItem(AetherBlocks.ANGELIC_STONE.get()).setWeight(2))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_VALKYRIE).setWeight(10))
                        .add(LootItem.lootTableItem(AetherItems.REGENERATION_STONE.get()).setWeight(3))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_TREASURE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_VALKYRIE).setWeight(8))
                        .add(LootItem.lootTableItem(AetherItems.LIGHTNING_SWORD.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.HOLY_SWORD.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.INVISIBILITY_CLOAK.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.REGENERATION_STONE.get()).setWeight(3))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_GRAVITITE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_SWORD.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_AXE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_HELMET.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_CHESTPLATE.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_LEGGINGS.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_BOOTS.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_GLOVES.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_PICKAXE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherBlocks.GRAVITITE_ORE.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_VALKYRIE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.SILVER_DUNGEON_CAPE_CONFIG).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_HELMET.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_CHESTPLATE.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_LEGGINGS.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_BOOTS.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_GLOVES.get()).setWeight(4))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_PICKAXE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_AXE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_SHOVEL.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_HOE.get()).setWeight(2))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_CAPE_CONFIG, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.VALKYRIE_CAPE.get()).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).when(ConfigEnabled.isEnabled(AetherConfig.SERVER.spawn_valkyrie_cape)))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_FEATHER.get()).setWeight(1).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).when(ConfigEnabled.isEnabled(AetherConfig.SERVER.spawn_golden_feather)))
                )
        );
        builder.accept(AetherLoot.SILVER_DUNGEON_GUMMIES, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(AetherItems.BLUE_GUMMY_SWET.get()).setWeight(2).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(6).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                )
        );

        builder.accept(AetherLoot.GOLD_DUNGEON_REWARD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_TREASURE))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5.0F, 8.0F))
                        .add(LootItem.lootTableItem(AetherItems.IRON_BUBBLE.get()).setWeight(12))
                        .add(LootItem.lootTableItem(AetherItems.VAMPIRE_BLADE.get()).setWeight(12))
                        .add(LootItem.lootTableItem(AetherItems.PIG_SLAYER.get()).setWeight(12))
                        .add(LootTableReference.lootTableReference(AetherLoot.GOLD_DUNGEON_TREASURE).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.LIFE_SHARD.get()).setWeight(1))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_HELMET.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_CHESTPLATE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_LEGGINGS.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_BOOTS.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_GLOVES.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_SWORD.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherItems.GRAVITITE_AXE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(AetherBlocks.ENCHANTED_GRAVITITE.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_HELMET).setWeight(2))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_CHESTPLATE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_LEGGINGS).setWeight(2))
                        .add(LootItem.lootTableItem(Items.CHAINMAIL_BOOTS).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.CHAINMAIL_GLOVES.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.ICE_PENDANT.get()).setWeight(6))
                        .add(LootItem.lootTableItem(AetherItems.ICE_RING.get()).setWeight(6))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 20.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_GUMMY_SWET.get()).setWeight(6))
                        .add(LootItem.lootTableItem(AetherItems.HEALING_STONE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_BERRY.get()).setWeight(5))
                )
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0.0F, 4.0F))
                        .add(LootItem.lootTableItem(AetherBlocks.ICESTONE.get()).setWeight(3))
                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).setWeight(2))
                        .add(LootItem.lootTableItem(AetherBlocks.AEROGEL.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.GOLD_DUNGEON_TREASURE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 5.0F))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_HELMET.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_CHESTPLATE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_LEGGINGS.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_BOOTS.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.PHOENIX_GLOVES.get()).setWeight(2))
                        .add(LootItem.lootTableItem(AetherItems.LIFE_SHARD.get()).setWeight(1))
                )
        );
        builder.accept(AetherLoot.RUINED_PORTAL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4.0F, 8.0F))
                        .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(40).apply(SetItemCountFunction.setCount(UniformGenerator.between(9.0F, 18.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(40)).add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(40))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(15)).add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 24.0F))))
                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_AXE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HOE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_SHOVEL).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_PICKAXE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_BOOTS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_CHESTPLATE).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HELMET).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GOLDEN_LEGGINGS).setWeight(15).apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
                        .add(LootItem.lootTableItem(Items.GLISTERING_MELON_SLICE).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F))))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR).setWeight(5)).add(LootItem.lootTableItem(Items.LIGHT_WEIGHTED_PRESSURE_PLATE).setWeight(5))
                        .add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 12.0F))))
                        .add(LootItem.lootTableItem(Items.CLOCK).setWeight(5)).add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.BELL).setWeight(1)).add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GOLD_BLOCK).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))));
    }
}
