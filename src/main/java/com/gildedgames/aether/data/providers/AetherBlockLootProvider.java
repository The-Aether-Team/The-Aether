package com.gildedgames.aether.data.providers;

import com.gildedgames.aether.AetherTags;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.loot.functions.DoubleDrops;
import com.gildedgames.aether.loot.functions.SpawnTNT;
import com.gildedgames.aether.loot.functions.SpawnXP;
import com.gildedgames.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.function.Supplier;

public abstract class AetherBlockLootProvider extends BlockLootSubProvider {
    public AetherBlockLootProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }

    public void dropNone(Supplier<? extends Block> block) {
        this.add(block.get(), noDrop());
    }

    public void dropDoubleWithSilk(Supplier<? extends Block> block, Supplier<? extends ItemLike> drop) {
        this.add(block.get(), (result) -> droppingDoubleWithSilkTouch(result, drop.get()));
    }

    public void dropSelfDouble(Supplier<? extends Block> block) {
        this.add(block.get(), droppingDouble(block.get()));
    }

    public void dropSelf(Supplier<? extends Block> block) {
        super.dropSelf(block.get());
    }

    public void dropDoubleWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
        this.add(block.get(), (result) -> droppingDoubleItemsWithFortune(result, drop.get()));
    }

    public void dropWithFortune(Supplier<? extends Block> block, Supplier<? extends Item> drop) {
        this.add(block.get(), (result) -> createOreDrop(result, drop.get()));
    }

    public void dropSilk(Supplier<? extends Block> block) {
        this.dropWhenSilkTouch(block.get());
    }

    public void drop(Supplier<? extends Block> block, Supplier<? extends Block> drop) {
        this.dropOther(block.get(), drop.get());
    }

    public void dropPot(Supplier<? extends Block> block) {
        this.dropPottedContents(block.get());
    }

    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, ItemLike noSilkTouch) {
        return this.droppingDoubleWithSilkTouch(block, this.applyExplosionCondition(block, LootItem.lootTableItem(noSilkTouch)));
    }

    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return this.droppingDouble(block, BlockLootAccessor.hasSilkTouch(), builder);
    }

    public LootTable.Builder droppingDouble(ItemLike item) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(item))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingDouble(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
        return createSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingGoldenOakLeaves(Block block, Block sapling, float... chances) {
        return this.droppingWithChancesAndSkyrootSticks(block, sapling, chances)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionCondition(block,
                                LootItem.lootTableItem(Items.GOLDEN_APPLE))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.00005F, 0.000055555557F, 0.0000625F, 0.00008333334F, 0.00025F))));
    }

    public LootTable.Builder droppingDoubleItemsWithFortune(Block block, Item item) {
        return createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingWithSkyrootSticks(Block block) {
        return createSilkTouchOrShearsDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
        return createSilkTouchOrShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(fruit)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingDoubleGoldenOak(Block original, Block block, Item item) {
        return LootTable.lootTable()
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(original)
                        .when(BlockLootAccessor.hasSilkTouch()))))
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block)
                        .when(BlockLootAccessor.hasSilkTouch().invert()))))
                .withPool(this.applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                        .when(BlockLootAccessor.hasSilkTouch().invert())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingNameableBlockEntityTable(Block block) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))))
        );
    }

    public LootTable.Builder droppingBerryBush(Block block, Block stem, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
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
                .when(BlockLootAccessor.hasSilkTouch().invert())
                .apply(DoubleDrops.builder())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block))
                .when(BlockLootAccessor.hasSilkTouch())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(stem)
                        .when(LootItemEntityPropertyCondition.entityPresent(LootContext.EntityTarget.THIS).invert()))
        );
    }

    public LootTable.Builder droppingTreasureChest(Block block) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy("Locked", "BlockEntityTag.Locked")
                                .copy("Kind", "BlockEntityTag.Kind"))))
        );
    }

    public LootTable.Builder droppingPresentLoot(Block block) {
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
