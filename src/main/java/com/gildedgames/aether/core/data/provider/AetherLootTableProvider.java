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
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import javax.annotation.Nonnull;

public class AetherLootTableProvider extends LootTableProvider
{
    public AetherLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(AetherLootTableData.RegisterBlockLoot::new, LootContextParamSets.BLOCK),
                Pair.of(AetherLootTableData.RegisterEntityLoot::new, LootContextParamSets.ENTITY),
                Pair.of(AetherLootTableData.RegisterDungeonLoot::new, LootContextParamSets.CHEST),
                Pair.of(AetherLootTableData.RegisterAdvancementLoot::new, LootContextParamSets.ADVANCEMENT_REWARD),
                Pair.of(AetherLootTableData.RegisterSelectorLoot::new, LootContextParamSets.SELECTOR),
                Pair.of(AetherLootTableData.RegisterStrippingLoot::new, AetherLoot.STRIPPING));
    }

    @Override
    protected void validate(@Nonnull Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) { }

    public static class AetherBlockLootTableProvider extends BlockLoot
    {
        public void dropNone(Supplier<? extends Block> block) {
            super.add(block.get(), noDrop());
        }

        public void dropDoubleWithSilk(Supplier<? extends Block> block, Supplier<? extends ItemLike> drop) {
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

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, ItemLike noSilkTouch) {
            return droppingDoubleWithSilkTouch(block, applyExplosionCondition(block, LootItem.lootTableItem(noSilkTouch)));
        }

        protected static LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
            return droppingDouble(block, HAS_SILK_TOUCH, builder);
        }

        protected static LootTable.Builder droppingDouble(ItemLike item) {
            return LootTable.lootTable().withPool(applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(item))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDouble(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> p_218494_2_) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                            .add(applyExplosionDecay(block,
                                    LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDoubleItemsWithFortune(Block block, Item item) {
            return createSilkTouchDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(item)
                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithSkyrootSticks(Block block) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block,
                    LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
            return createSilkTouchOrShearsDispatchTable(block, applyExplosionDecay(block, LootItem.lootTableItem(fruit)))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SHEARS_OR_SILK_TOUCH.invert())
                            .add(applyExplosionDecay(block,
                                    LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                    .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingDoubleGoldenOak(Block original, Block block, Item item) {
            return LootTable.lootTable()
                    .withPool(applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(original)
                            .when(HAS_SILK_TOUCH))))
                    .withPool(applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block)
                            .when(HAS_SILK_TOUCH.invert()))))
                    .withPool(applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)
                            .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                            .when(HAS_SILK_TOUCH.invert())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))
                    .apply(DoubleDrops.builder());
        }

        protected static LootTable.Builder droppingNameableBlockEntityTable(Block block) {
            return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block)
                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))))
            );
        }

        protected static LootTable.Builder droppingBerryBush(Block block, Item drop) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(applyExplosionDecay(block, LootItem.lootTableItem(drop)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))
                            .when(LocationCheck.checkLocation(
                                    LocationPredicate.Builder.location().setBlock(
                                            BlockPredicate.Builder.block().of(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0)).invert()))
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))
                            .when(LocationCheck.checkLocation(
                                    LocationPredicate.Builder.location().setBlock(
                                            BlockPredicate.Builder.block().of(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()).build()),
                                    new BlockPos(0, -1, 0))))))
                    .apply(DoubleDrops.builder())
            );
        }

        protected static LootTable.Builder droppingTreasureChest(Block block) {
            return LootTable.lootTable().withPool(applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(block)
                            .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                            .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                    .copy("Locked", "BlockEntityTag.Locked")
                                    .copy("Kind", "BlockEntityTag.Kind"))))
            );
        }

        protected static LootTable.Builder droppingPresentLoot(Block block) {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(Items.AIR).setWeight(18)
                            .apply(SpawnTNT.builder()))
                    .add(LootItem.lootTableItem(Items.AIR).setWeight(9)
                            .apply(SpawnXP.builder()))
                    .add((applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.GINGERBREAD_MAN.get()).setWeight(8)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 6.0F))))))
                    .add((applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.CANDY_CANE_SWORD.get()).setWeight(1))))
            );
        }
    }
}
