package com.aether.data;

import com.aether.data.provider.AetherLootTableProvider;
import com.aether.registry.AetherBlocks;
import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import com.aether.registry.AetherLoot;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AetherLootTables extends AetherLootTableProvider
{
    public AetherLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "Aether Loot Tables";
    }

    public static class RegisterBlocks extends AetherBlockLootTableProvider
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

            dropSelfDouble(AetherBlocks.COLD_AERCLOUD);
            dropSelfDouble(AetherBlocks.BLUE_AERCLOUD);
            dropSelfDouble(AetherBlocks.GOLDEN_AERCLOUD);
            dropSelfDouble(AetherBlocks.PINK_AERCLOUD);

            dropSelf(AetherBlocks.ICESTONE);
            dropDoubleWithFortune(AetherBlocks.AMBROSIUM_ORE, AetherItems.AMBROSIUM_SHARD);
            dropWithFortune(AetherBlocks.ZANITE_ORE, AetherItems.ZANITE_GEMSTONE);
            dropSelf(AetherBlocks.GRAVITITE_ORE);

            this.registerLootTable(AetherBlocks.SKYROOT_LEAVES.get(),
                    (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.SKYROOT_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
            this.registerLootTable(AetherBlocks.GOLDEN_OAK_LEAVES.get(),
                    (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.GOLDEN_OAK_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
            this.registerLootTable(AetherBlocks.CRYSTAL_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);
            this.registerLootTable(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(),
                    (leaves) -> droppingWithFruitAndSkyrootSticks(leaves, AetherItems.WHITE_APPLE.get()));
            this.registerLootTable(AetherBlocks.HOLIDAY_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);
            this.registerLootTable(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(),
                    AetherBlockLootTableProvider::droppingWithSkyrootSticks);

            //TODO: Golden Oak currently uses the ore math for fortune drops, check for balancing.
            dropSelfDouble(AetherBlocks.SKYROOT_LOG);
            this.registerLootTable(AetherBlocks.GOLDEN_OAK_LOG.get(),
                    (log) -> droppingDoubleGoldenOak(log, AetherItems.GOLDEN_AMBER.get()));
            dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_LOG);
            dropSelfDouble(AetherBlocks.SKYROOT_WOOD);
            this.registerLootTable(AetherBlocks.GOLDEN_OAK_WOOD.get(),
                    (wood) -> droppingDoubleGoldenOak(wood, AetherItems.GOLDEN_AMBER.get()));
            dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_WOOD);

            dropSelf(AetherBlocks.SKYROOT_PLANKS);
            dropSelf(AetherBlocks.HOLYSTONE_BRICKS);
            dropSilk(AetherBlocks.QUICKSOIL_GLASS);
            dropSelf(AetherBlocks.AEROGEL);

            dropSelf(AetherBlocks.ZANITE_BLOCK);
            dropSelf(AetherBlocks.ENCHANTED_GRAVITITE);

            dropSelf(AetherBlocks.ENCHANTER);
            dropSelf(AetherBlocks.FREEZER);
            dropSelf(AetherBlocks.INCUBATOR);

            drop(AetherBlocks.AMBROSIUM_WALL_TORCH, AetherBlocks.AMBROSIUM_TORCH);
            dropSelf(AetherBlocks.AMBROSIUM_TORCH);

            this.registerLootTable(AetherBlocks.BERRY_BUSH.get(),
                    (bush) -> droppingBerryBush(bush, AetherItems.BLUE_BERRY.get()));
            dropSelf(AetherBlocks.BERRY_BUSH_STEM);

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
            dropSelf(AetherBlocks.TREASURE_CHEST);

            dropSelf(AetherBlocks.PILLAR);
            dropSelf(AetherBlocks.PILLAR_TOP);

            //TODO: handle the loot for this since that requires some extra stuff.
            // might need to make a loot condition that spawns an entity, if it doesnt already exist.
            // if i do make a loot condition that spawns an entity i could also probably use that for the chest mimic
            // although thats not completely necessary.
            dropSelf(AetherBlocks.PRESENT);

            dropSelf(AetherBlocks.SKYROOT_FENCE);
            dropSelf(AetherBlocks.SKYROOT_FENCE_GATE);

            dropSelf(AetherBlocks.CARVED_WALL);
            dropSelf(AetherBlocks.ANGELIC_WALL);
            dropSelf(AetherBlocks.HELLFIRE_WALL);
            dropSelf(AetherBlocks.HOLYSTONE_WALL);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_WALL);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_WALL);
            dropSelf(AetherBlocks.AEROGEL_WALL);

            dropSelf(AetherBlocks.SKYROOT_STAIRS);
            dropSelf(AetherBlocks.CARVED_STAIRS);
            dropSelf(AetherBlocks.ANGELIC_STAIRS);
            dropSelf(AetherBlocks.HELLFIRE_STAIRS);
            dropSelf(AetherBlocks.HOLYSTONE_STAIRS);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_STAIRS);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_STAIRS);
            dropSelf(AetherBlocks.AEROGEL_STAIRS);

            dropSelf(AetherBlocks.SKYROOT_SLAB);
            dropSelf(AetherBlocks.CARVED_SLAB);
            dropSelf(AetherBlocks.ANGELIC_SLAB);
            dropSelf(AetherBlocks.HELLFIRE_SLAB);
            dropSelf(AetherBlocks.HOLYSTONE_SLAB);
            dropSelf(AetherBlocks.MOSSY_HOLYSTONE_SLAB);
            dropSelf(AetherBlocks.HOLYSTONE_BRICK_SLAB);
            dropSelf(AetherBlocks.AEROGEL_SLAB);

            dropSelf(AetherBlocks.SUN_ALTAR);

            this.registerLootTable(AetherBlocks.SKYROOT_BOOKSHELF.get(),
                    (bookshelf) -> droppingWithSilkTouchOrRandomly(bookshelf, Items.BOOK, ConstantRange.of(3)));

            dropSelf(AetherBlocks.SKYROOT_BED);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return AetherBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    public static class RegisterEntities extends EntityLootTables
    {
        @Override
        protected void addTables() {
            this.registerLootTable(AetherEntityTypes.PHYG.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.PORKCHOP)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                    .acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.FEATHER)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.FLYING_COW.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.LEATHER)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.BEEF)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F)))
                                    .acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.SHEEPUFF.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.MUTTON)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                    .acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_BLACK, sheepLootTableBuilderWithDrop(Blocks.BLACK_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_BLUE, sheepLootTableBuilderWithDrop(Blocks.BLUE_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_BROWN, sheepLootTableBuilderWithDrop(Blocks.BROWN_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_CYAN, sheepLootTableBuilderWithDrop(Blocks.CYAN_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_GRAY, sheepLootTableBuilderWithDrop(Blocks.GRAY_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_GREEN, sheepLootTableBuilderWithDrop(Blocks.GREEN_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_LIGHT_BLUE, sheepLootTableBuilderWithDrop(Blocks.LIGHT_BLUE_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_LIGHT_GRAY, sheepLootTableBuilderWithDrop(Blocks.LIGHT_GRAY_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_LIME, sheepLootTableBuilderWithDrop(Blocks.LIME_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_MAGENTA, sheepLootTableBuilderWithDrop(Blocks.MAGENTA_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_ORANGE, sheepLootTableBuilderWithDrop(Blocks.ORANGE_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_PINK, sheepLootTableBuilderWithDrop(Blocks.PINK_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_PURPLE, sheepLootTableBuilderWithDrop(Blocks.PURPLE_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_RED, sheepLootTableBuilderWithDrop(Blocks.RED_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_WHITE, sheepLootTableBuilderWithDrop(Blocks.WHITE_WOOL));
            this.registerLootTable(AetherLoot.ENTITIES_SHEEPUFF_YELLOW, sheepLootTableBuilderWithDrop(Blocks.YELLOW_WOOL));

            this.registerLootTable(AetherEntityTypes.MOA.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.FEATHER)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 2.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.AERWHALE.get(), LootTable.builder());
            this.registerLootTable(AetherEntityTypes.WHIRLWIND.get(), LootTable.builder());

            this.registerLootTable(AetherEntityTypes.AECHOR_PLANT.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(AetherItems.AECHOR_PETAL.get())
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 1.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.COCKATRICE.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Items.FEATHER)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 2.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.ZEPHYR.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(AetherBlocks.COLD_AERCLOUD.get())
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.SENTRY.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(AetherBlocks.CARVED_STONE.get()).weight(5)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 1.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                            .addEntry(ItemLootEntry.builder(AetherBlocks.SENTRY_STONE.get()).weight(1)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 1.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );

            this.registerLootTable(AetherEntityTypes.MIMIC.get(), LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(Blocks.CHEST)
                                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 1.0F)))
                                    .acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F)))
                            )
                    )
            );
        }

        private static LootTable.Builder sheepLootTableBuilderWithDrop(IItemProvider wool) {
            return LootTable.builder()
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(wool)))
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(TableLootEntry.builder(AetherEntityTypes.SHEEPUFF.get().getLootTable())));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return AetherEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }
}
