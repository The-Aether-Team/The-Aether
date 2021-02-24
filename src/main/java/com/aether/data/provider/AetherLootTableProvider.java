package com.aether.data.provider;

import com.aether.data.AetherLootTables;
import com.aether.loot.functions.DoubleDrops;
import com.aether.registry.AetherBlocks;
import com.aether.registry.AetherItems;
import com.aether.registry.AetherTags;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AetherLootTableProvider extends LootTableProvider
{
    public AetherLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(AetherLootTables.RegisterBlocks::new, LootParameterSets.BLOCK),
                Pair.of(AetherLootTables.RegisterEntities::new, LootParameterSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
    }

    public static class AetherBlockLootTableProvider extends BlockLootTables
    {
        public void dropNone(Supplier<? extends Block> block) {
            super.registerLootTable(block.get(), blockNoDrop());
        }

        public void dropDoubleWithSilk(Supplier<? extends Block> block, Supplier<? extends IItemProvider> drop) {
            registerLootTable(block.get(), (result) -> droppingDoubleWithSilkTouch(result, drop.get()));
        }

        public void dropSelfDouble(Supplier<? extends Block> block) {
            super.registerLootTable(block.get(), droppingDouble(block.get()));
        }

        public void dropSelf(Supplier<? extends Block> block) {
            super.registerDropSelfLootTable(block.get());
        }

        public void dropDoubleWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
            super.registerLootTable(block.get(), (result) -> droppingDoubleItemsWithFortune(result, drop.get()));
        }

        public void dropWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
            super.registerLootTable(block.get(), (result) -> droppingItemWithFortune(result, drop.get()));
        }

        public void dropSilk(Supplier<? extends Block> block) {
            super.registerSilkTouch(block.get());
        }

        public void drop(Supplier<? extends Block> block, Supplier<? extends Block> drop) {
            this.registerDropping(block.get(), drop.get());
        }

        public void dropPot(Supplier<? extends Block> block) {
            this.registerFlowerPot(block.get());
        }

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, IItemProvider noSilkTouch) {
            return droppingDoubleWithSilkTouch(block, withSurvivesExplosion(block, ItemLootEntry.builder(noSilkTouch)));
        }

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootEntry.Builder<?> builder) {
            return droppingDouble(block, SILK_TOUCH, builder);
        }

        protected static LootTable.Builder droppingDouble(IItemProvider item) {
            return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(item))))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDouble(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_) {
            return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(block).acceptCondition(conditionBuilder).alternatively(p_218494_2_)))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
            return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(sapling)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, chances)))
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SILK_TOUCH_OR_SHEARS.inverted())
                            .addEntry(withExplosionDecay(block,
                                    ItemLootEntry.builder(AetherItems.SKYROOT_STICK.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))))
                                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDoubleItemsWithFortune(Block block, Item item) {
            return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item)
                    .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithSkyrootSticks(Block block) {
            return droppingWithSilkTouchOrShears(block, withExplosionDecay(block,
                    ItemLootEntry.builder(AetherItems.SKYROOT_STICK.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))))
                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
            return droppingWithSilkTouchOrShears(block, withExplosionDecay(block, ItemLootEntry.builder(fruit)))
                    .addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SILK_TOUCH_OR_SHEARS.inverted())
                            .addEntry(withExplosionDecay(block,
                                    ItemLootEntry.builder(AetherItems.SKYROOT_STICK.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))))
                                    .acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
        }

        protected static LootTable.Builder droppingDoubleGoldenOak(Block block, Item item) {
            return LootTable.builder()
                    .addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block))))
                    .addLootPool(withExplosionDecay(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)
                            .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().tag(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                            .acceptCondition(SILK_TOUCH.inverted())
                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                            .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))))
                    .acceptFunction(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingGoldenOak(Block block, Item item) {
            return LootTable.builder()
                    .addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block))))
                    .addLootPool(withExplosionDecay(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)
                            .acceptCondition(MatchTool.builder(ItemPredicate.Builder.create().tag(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                            .acceptCondition(SILK_TOUCH.inverted())
                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                            .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
        }

        protected static LootTable.Builder droppingBerryBush(Block block, Item drop) {
            return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
                    .addEntry(withExplosionDecay(block, ItemLootEntry.builder(drop)
                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F))
                            .acceptCondition(LocationCheck.func_241547_a_(
                                    LocationPredicate.Builder.builder().block(
                                            BlockPredicate.Builder.createBuilder().setBlock(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0)).inverted()))
                    .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 4.0F))
                            .acceptCondition(LocationCheck.func_241547_a_(
                                    LocationPredicate.Builder.builder().block(
                                            BlockPredicate.Builder.createBuilder().setBlock(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0))))
                            )
                    )
            );
        }
    }
}
