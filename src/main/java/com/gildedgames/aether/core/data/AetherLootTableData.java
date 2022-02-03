package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.loot.conditions.ConfigEnabled;
import com.gildedgames.aether.common.loot.functions.WhirlwindSpawnEntity;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.data.provider.AetherLootTableProvider;
import com.gildedgames.aether.common.registry.*;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class AetherLootTableData extends AetherLootTableProvider
{
    public AetherLootTableData(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "Aether Loot Tables";
    }

    public static class RegisterBlockLoot extends AetherBlockLootTableProvider
    {
        @Override
        protected void addTables() {
            dropNone(AetherBlocks.AETHER_PORTAL);

            dropDoubleWithSilk(AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
            dropDoubleWithSilk(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
            dropSelfDouble(AetherBlocks.AETHER_DIRT);
            dropSelfDouble(AetherBlocks.QUICKSOIL);
            dropSelfDouble(AetherBlocks.HOLYSTONE);
            dropSelfDouble(AetherBlocks.MOSSY_HOLYSTONE);
            drop(AetherBlocks.AETHER_FARMLAND, AetherBlocks.AETHER_DIRT);

            dropSelfDouble(AetherBlocks.COLD_AERCLOUD);
            dropSelfDouble(AetherBlocks.BLUE_AERCLOUD);
            dropSelfDouble(AetherBlocks.GOLDEN_AERCLOUD);
            dropSelfDouble(AetherBlocks.PINK_AERCLOUD);

            dropSelf(AetherBlocks.ICESTONE);
            dropDoubleWithFortune(AetherBlocks.AMBROSIUM_ORE, AetherItems.AMBROSIUM_SHARD);
            dropWithFortune(AetherBlocks.ZANITE_ORE, AetherItems.ZANITE_GEMSTONE);
            dropSelf(AetherBlocks.GRAVITITE_ORE);
            this.add(AetherBlocks.SKYROOT_LEAVES.get(),
                    (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.SKYROOT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            this.add(AetherBlocks.GOLDEN_OAK_LEAVES.get(),
                    (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.GOLDEN_OAK_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            this.add(AetherBlocks.CRYSTAL_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);
            this.add(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(),
                    (leaves) -> droppingWithFruitAndSkyrootSticks(leaves, AetherItems.WHITE_APPLE.get()));
            this.add(AetherBlocks.HOLIDAY_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);
            this.add(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);

            dropSelfDouble(AetherBlocks.SKYROOT_LOG);
            this.add(AetherBlocks.GOLDEN_OAK_LOG.get(),
                    (log) -> droppingDoubleGoldenOak(log, AetherBlocks.SKYROOT_LOG.get(), AetherItems.GOLDEN_AMBER.get()));
            dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_LOG);
            dropSelfDouble(AetherBlocks.SKYROOT_WOOD);
            this.add(AetherBlocks.GOLDEN_OAK_WOOD.get(),
                    (wood) -> droppingDoubleGoldenOak(wood, AetherBlocks.SKYROOT_WOOD.get(), AetherItems.GOLDEN_AMBER.get()));
            dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_WOOD);

            dropSelf(AetherBlocks.SKYROOT_PLANKS);
            dropSelf(AetherBlocks.HOLYSTONE_BRICKS);
            dropSilk(AetherBlocks.QUICKSOIL_GLASS);
            dropSelf(AetherBlocks.AEROGEL);

            dropSelf(AetherBlocks.ZANITE_BLOCK);
            dropSelf(AetherBlocks.ENCHANTED_GRAVITITE);

            this.add(AetherBlocks.ALTAR.get(), AetherBlockLootTableProvider::droppingNameableBlockEntityTable);
            this.add(AetherBlocks.FREEZER.get(), AetherBlockLootTableProvider::droppingNameableBlockEntityTable);
            this.add(AetherBlocks.INCUBATOR.get(), AetherBlockLootTableProvider::droppingNameableBlockEntityTable);

            drop(AetherBlocks.AMBROSIUM_WALL_TORCH, AetherBlocks.AMBROSIUM_TORCH);
            dropSelf(AetherBlocks.AMBROSIUM_TORCH);

            drop(AetherBlocks.SKYROOT_WALL_SIGN, AetherBlocks.SKYROOT_SIGN);
            dropSelf(AetherBlocks.SKYROOT_SIGN);

            this.add(AetherBlocks.BERRY_BUSH.get(), (bush) -> droppingBerryBush(bush, AetherItems.BLUE_BERRY.get()));
            dropSelfDouble(AetherBlocks.BERRY_BUSH_STEM);

            dropSelf(AetherBlocks.PURPLE_FLOWER);
            dropSelf(AetherBlocks.WHITE_FLOWER);
            dropPot(AetherBlocks.POTTED_PURPLE_FLOWER);
            dropPot(AetherBlocks.POTTED_WHITE_FLOWER);

            dropSelf(AetherBlocks.SKYROOT_SAPLING);
            dropSelf(AetherBlocks.GOLDEN_OAK_SAPLING);
            dropPot(AetherBlocks.POTTED_SKYROOT_SAPLING);
            dropPot(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING);

            dropSelf(AetherBlocks.CARVED_STONE);
            dropSelf(AetherBlocks.SENTRY_STONE);
            dropSelf(AetherBlocks.ANGELIC_STONE);
            dropSelf(AetherBlocks.LIGHT_ANGELIC_STONE);
            dropSelf(AetherBlocks.HELLFIRE_STONE);
            dropSelf(AetherBlocks.LIGHT_HELLFIRE_STONE);

            dropNone(AetherBlocks.LOCKED_CARVED_STONE);
            dropNone(AetherBlocks.LOCKED_SENTRY_STONE);
            dropNone(AetherBlocks.LOCKED_ANGELIC_STONE);
            dropNone(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE);
            dropNone(AetherBlocks.LOCKED_HELLFIRE_STONE);
            dropNone(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE);

            dropNone(AetherBlocks.TRAPPED_CARVED_STONE);
            dropNone(AetherBlocks.TRAPPED_SENTRY_STONE);
            dropNone(AetherBlocks.TRAPPED_ANGELIC_STONE);
            dropNone(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE);
            dropNone(AetherBlocks.TRAPPED_HELLFIRE_STONE);
            dropNone(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE);

            dropNone(AetherBlocks.CHEST_MIMIC);
            this.add(AetherBlocks.TREASURE_CHEST.get(), AetherBlockLootTableProvider::droppingTreasureChest);

            dropSelf(AetherBlocks.PILLAR);
            dropSelf(AetherBlocks.PILLAR_TOP);

            this.add(AetherBlocks.PRESENT.get(), AetherBlockLootTableProvider::droppingPresentLoot);

            dropSelf(AetherBlocks.SKYROOT_FENCE);
            dropSelf(AetherBlocks.SKYROOT_FENCE_GATE);
            this.add(AetherBlocks.SKYROOT_DOOR.get(), createDoorTable(AetherBlocks.SKYROOT_DOOR.get()));
            dropSelf(AetherBlocks.SKYROOT_TRAPDOOR);
            dropSelf(AetherBlocks.SKYROOT_BUTTON);
            dropSelf(AetherBlocks.SKYROOT_PRESSURE_PLATE);

            dropSelf(AetherBlocks.HOLYSTONE_BUTTON);
            dropSelf(AetherBlocks.HOLYSTONE_PRESSURE_PLATE);

            dropSelf(AetherBlocks.CARVED_WALL);
            dropSelf(AetherBlocks.ANGELIC_WALL);
            dropSelf(AetherBlocks.HELLFIRE_WALL);
            dropSelf(AetherBlocks.HOLYSTONE_WALL);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_WALL);
            dropSelf(AetherBlocks.ICESTONE_WALL);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_WALL);
            dropSelf(AetherBlocks.AEROGEL_WALL);

            dropSelf(AetherBlocks.SKYROOT_STAIRS);
            dropSelf(AetherBlocks.CARVED_STAIRS);
            dropSelf(AetherBlocks.ANGELIC_STAIRS);
            dropSelf(AetherBlocks.HELLFIRE_STAIRS);
            dropSelf(AetherBlocks.HOLYSTONE_STAIRS);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_STAIRS);
            dropSelf(AetherBlocks.ICESTONE_STAIRS);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_STAIRS);
            dropSelf(AetherBlocks.AEROGEL_STAIRS);

            dropSelf(AetherBlocks.SKYROOT_SLAB);
            dropSelf(AetherBlocks.CARVED_SLAB);
            dropSelf(AetherBlocks.ANGELIC_SLAB);
            dropSelf(AetherBlocks.HELLFIRE_SLAB);
            dropSelf(AetherBlocks.HOLYSTONE_SLAB);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_SLAB);
            dropSelf(AetherBlocks.ICESTONE_SLAB);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_SLAB);
            dropSelf(AetherBlocks.AEROGEL_SLAB);

            this.add(AetherBlocks.SUN_ALTAR.get(), AetherBlockLootTableProvider::droppingNameableBlockEntityTable);

            this.add(AetherBlocks.SKYROOT_BOOKSHELF.get(),
                    (bookshelf) -> createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));

            this.add(AetherBlocks.SKYROOT_BED.get(),
                    (bed) -> createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return AetherBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    public static class RegisterEntityLoot extends EntityLoot
    {
        @Override
        protected void addTables() {
            this.add(AetherEntityTypes.PHYG.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.PORKCHOP)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.FEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.FLYING_COW.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.LEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.BEEF)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.SHEEPUFF.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.MUTTON)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                    .apply(SmeltItemFunction.smelted().when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );
            this.add(AetherLoot.ENTITIES_SHEEPUFF_BLACK, sheepLootTableBuilderWithDrop(Blocks.BLACK_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_BLUE, sheepLootTableBuilderWithDrop(Blocks.BLUE_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_BROWN, sheepLootTableBuilderWithDrop(Blocks.BROWN_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_CYAN, sheepLootTableBuilderWithDrop(Blocks.CYAN_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_GRAY, sheepLootTableBuilderWithDrop(Blocks.GRAY_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_GREEN, sheepLootTableBuilderWithDrop(Blocks.GREEN_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_LIGHT_BLUE, sheepLootTableBuilderWithDrop(Blocks.LIGHT_BLUE_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_LIGHT_GRAY, sheepLootTableBuilderWithDrop(Blocks.LIGHT_GRAY_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_LIME, sheepLootTableBuilderWithDrop(Blocks.LIME_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_MAGENTA, sheepLootTableBuilderWithDrop(Blocks.MAGENTA_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_ORANGE, sheepLootTableBuilderWithDrop(Blocks.ORANGE_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_PINK, sheepLootTableBuilderWithDrop(Blocks.PINK_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_PURPLE, sheepLootTableBuilderWithDrop(Blocks.PURPLE_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_RED, sheepLootTableBuilderWithDrop(Blocks.RED_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_WHITE, sheepLootTableBuilderWithDrop(Blocks.WHITE_WOOL));
            this.add(AetherLoot.ENTITIES_SHEEPUFF_YELLOW, sheepLootTableBuilderWithDrop(Blocks.YELLOW_WOOL));

            this.add(AetherEntityTypes.MOA.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.FEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.AERWHALE.get(), LootTable.lootTable());

            this.add(AetherEntityTypes.AERBUNNY.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.STRING)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.WHIRLWIND.get(), LootTable.lootTable());
            this.add(AetherEntityTypes.EVIL_WHIRLWIND.get(), LootTable.lootTable());

            this.add(AetherEntityTypes.AECHOR_PLANT.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AetherItems.AECHOR_PETAL.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.COCKATRICE.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.FEATHER)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.ZEPHYR.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AetherBlocks.COLD_AERCLOUD.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.SENTRY.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AetherBlocks.CARVED_STONE.get()).setWeight(4)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                            .add(LootItem.lootTableItem(AetherBlocks.SENTRY_STONE.get()).setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );

            this.add(AetherEntityTypes.MIMIC.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.CHEST)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );
            this.add(AetherEntityTypes.BLUE_SWET.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AetherItems.SWET_BALL.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AetherBlocks.BLUE_AERCLOUD.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );
            this.add(AetherEntityTypes.GOLDEN_SWET.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Blocks.GLOWSTONE)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );
            this.add(AetherEntityTypes.FIRE_MINION.get(), LootTable.lootTable());
        }

        private static LootTable.Builder sheepLootTableBuilderWithDrop(ItemLike wool) {
            return LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(wool)))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootTableReference.lootTableReference(AetherEntityTypes.SHEEPUFF.get().getDefaultLootTable())));
        }

        @Nonnull
        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return AetherEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    public static class RegisterDungeonLoot extends ChestLoot
    {
        @Override
        public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            builder.accept(AetherLoot.BRONZE_DUNGEON, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 5.0F))
                            .add(LootItem.lootTableItem(AetherItems.ZANITE_PICKAXE.get()).setWeight(1))
                            .add(LootItem.lootTableItem(AetherItems.ZANITE_AXE.get()).setWeight(1))
                            .add(LootItem.lootTableItem(AetherItems.ZANITE_SWORD.get()).setWeight(1))
                            .add(LootItem.lootTableItem(AetherItems.ZANITE_SHOVEL.get()).setWeight(1))
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
                            .add(LootItem.lootTableItem(AetherItems.REPULSION_SHIELD.get()).setWeight(1))
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

    public static class RegisterAdvancementLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
    {
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            builder.accept(AetherLoot.ENTER_AETHER, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.GOLDEN_PARACHUTE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(AetherItems.BOOK_OF_LORE.get())).when(ConfigEnabled.isEnabled(AetherConfig.COMMON.enable_startup_loot)))
            );
        }
    }

    public static class RegisterSelectorLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
    {
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            builder.accept(AetherLoot.WHIRLWIND_JUNK, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(9))
                            .add(LootItem.lootTableItem(Items.PUMPKIN).setWeight(2))
                            .add(LootItem.lootTableItem(Items.GRAVEL).setWeight(5))
                            .add(LootItem.lootTableItem(Items.CLAY).setWeight(11))
                            .add(LootItem.lootTableItem(Items.STICK).setWeight(12))
                            .add(LootItem.lootTableItem(Items.FLINT).setWeight(14))
                            .add(LootItem.lootTableItem(Blocks.OAK_LOG).setWeight(17))
                            .add(LootItem.lootTableItem(Blocks.SAND).setWeight(20))
                    )
            );
            builder.accept(AetherLoot.EVIL_WHIRLWIND_JUNK, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.AIR)
                                    .apply(WhirlwindSpawnEntity.builder(EntityType.CREEPER, 1)))
                    )
            );
        }
    }

    public static class RegisterStrippingLoot implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>
    {
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> builder) {
            builder.accept(AetherLoot.STRIP_GOLDEN_OAK, LootTable.lootTable()
                    .withPool(LootPool.lootPool().add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get())
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        }
    }
}
