package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.core.data.AetherLootTableData;
import com.gildedgames.aether.common.loot.functions.DoubleDrops;
import com.gildedgames.aether.common.loot.functions.SpawnTNT;
import com.gildedgames.aether.common.loot.functions.SpawnXP;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherLoot;
import com.gildedgames.aether.common.registry.AetherTags;
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
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
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
                Pair.of(AetherLootTableData.RegisterBlockLoot::new, LootParameterSets.BLOCK),
                Pair.of(AetherLootTableData.RegisterEntityLoot::new, LootParameterSets.ENTITY),
                Pair.of(AetherLootTableData.RegisterDungeonLoot::new, LootParameterSets.CHEST),
                Pair.of(AetherLootTableData.RegisterAdvancementLoot::new, LootParameterSets.ADVANCEMENT_REWARD),
                Pair.of(AetherLootTableData.RegisterStrippingLoot::new, AetherLoot.STRIPPING));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
    }

    public static class AetherBlockLootTableProvider extends BlockLootTables
    {
        public void dropNone(Supplier<? extends Block> block) {
            super.add(block.get(), noDrop());
        }

        public void dropDoubleWithSilk(Supplier<? extends Block> block, Supplier<? extends IItemProvider> drop) {
            add(block.get(), (result) -> droppingDoubleWithSilkTouch(result, drop.get()));
        }

        public void dropSelfDouble(Supplier<? extends Block> block) {
            super.add(block.get(), droppingDouble(block.get()));
        }

        public void dropSelf(Supplier<? extends Block> block) {
            super.dropSelf(block.get());
        }

        public void dropDoubleWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
            super.add(block.get(), (result) -> droppingDoubleItemsWithFortune(result, drop.get()));
        }

        public void dropWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
            super.add(block.get(), (result) -> createOreDrop(result, drop.get()));
        }

        public void dropSilk(Supplier<? extends Block> block) {
            super.dropWhenSilkTouch(block.get());
        }

        public void drop(Supplier<? extends Block> block, Supplier<? extends Block> drop) {
            this.dropOther(block.get(), drop.get());
        }

        public void dropPot(Supplier<? extends Block> block) {
            this.dropPottedContents(block.get());
        }

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, IItemProvider noSilkTouch) {
            return droppingDoubleWithSilkTouch(block, applyExplosionCondition(block, ItemLootEntry.lootTableItem(noSilkTouch)));
        }

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootEntry.Builder<?> builder) {
            return droppingDouble(block, HAS_SILK_TOUCH, builder);
        }

        protected static LootTable.Builder droppingDouble(IItemProvider item) {
            return LootTable.lootTable().withPool(applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(item))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDouble(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, ItemLootEntry.lootTableItem(sapling)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                    .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                            .add(applyExplosionDecay(block,
                                    ItemLootEntry.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                                    .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDoubleItemsWithFortune(Block block, Item item) {
            return createSilkTouchDispatchTable(block, applyExplosionDecay(block, ItemLootEntry.lootTableItem(item)
                    .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithSkyrootSticks(Block block) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block,
                    ItemLootEntry.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                    .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, ItemLootEntry.lootTableItem(fruit)))
                    .withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                            .add(applyExplosionDecay(block,
                                    ItemLootEntry.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F))))
                                    .when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDoubleGoldenOak(Block original, Block block, Item item) {
            return LootTable.lootTable()
                    .withPool(applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(original)
                            .when(HAS_SILK_TOUCH))))
                    .withPool(applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block)
                            .when(HAS_SILK_TOUCH.invert()))))
                    .withPool(applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item)
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                            .when(HAS_SILK_TOUCH.invert())
                            .apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
                            .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingNameableBlockEntityTable(Block block) {
            return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(block)
                            .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))))
            );
        }

        protected static LootTable.Builder droppingBerryBush(Block block, Item drop) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(applyExplosionDecay(block, ItemLootEntry.lootTableItem(drop)
                    .apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F))
                            .when(LocationCheck.checkLocation(
                                    LocationPredicate.Builder.location().setBlock(
                                            BlockPredicate.Builder.block().of(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0)).invert()))
                    .apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F))
                            .when(LocationCheck.checkLocation(
                                    LocationPredicate.Builder.location().setBlock(
                                            BlockPredicate.Builder.block().of(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0))))))
                    .apply(DoubleDrops.builder())
            );
        }

        protected static LootTable.Builder droppingTreasureChest(Block block) {
            return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(block)
                            .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))
                            .apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
                                    .copy("Locked", "BlockEntityTag.Locked")
                                    .copy("Kind", "BlockEntityTag.Kind"))))
            );
        }

        protected static LootTable.Builder droppingPresentLoot(Block block) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(Items.AIR).setWeight(18)
                            .apply(SpawnTNT.builder()))
                    .add(ItemLootEntry.lootTableItem(Items.AIR).setWeight(9)
                            .apply(SpawnXP.builder()))
                    .add((applyExplosionDecay(block, ItemLootEntry.lootTableItem(AetherItems.GINGERBREAD_MAN.get()).setWeight(8)
                            .apply(SetCount.setCount(RandomValueRange.between(5.0F, 6.0F))))))
                    .add((applyExplosionDecay(block, ItemLootEntry.lootTableItem(AetherItems.CANDY_CANE_SWORD.get()).setWeight(1))))
            );
        }
    }
}
