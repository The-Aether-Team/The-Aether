package com.aetherteam.aether.data.providers;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import com.aetherteam.aether.loot.functions.SpawnTNT;
import com.aetherteam.aether.loot.functions.SpawnXP;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import com.aetherteam.nitrogen.data.providers.NitrogenBlockLootSubProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
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

public abstract class AetherBlockLootSubProvider extends NitrogenBlockLootSubProvider {
    public AetherBlockLootSubProvider(Set<Item> items, FeatureFlagSet flags) {
        super(items, flags);
    }

    public void dropDoubleWithSilk(Block block, ItemLike drop) {
        this.add(block, (result) -> this.droppingDoubleWithSilkTouch(result, drop));
    }

    public void dropSelfDouble(Block block) {
        this.add(block, this.droppingDouble(block));
    }

    public void dropDoubleWithFortune(Block block, Item drop) {
        this.add(block, (result) -> this.droppingDoubleItemsWithFortune(result, drop));
    }

    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, ItemLike noSilkTouch) {
        return this.droppingDoubleWithSilkTouch(block, this.applyExplosionCondition(block, LootItem.lootTableItem(noSilkTouch)));
    }

    public LootTable.Builder droppingDoubleWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return this.droppingDouble(block, BlockLootAccessor.aether$hasSilkTouch(), builder);
    }

    public LootTable.Builder droppingDouble(ItemLike item) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(item))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingDouble(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> builder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(builder)))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingWithChancesAndSkyrootSticks(Block block, Block sapling, float... chances) {
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.aether$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingGoldenOakLeaves(Block block, Block sapling, float... chances) {
        return this.droppingWithChancesAndSkyrootSticks(block, sapling, chances)
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(BlockLootAccessor.aether$hasShearsOrSilkTouch().invert())
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
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingWithFruitAndSkyrootSticks(Block block, Item fruit) {
        return createForgeSilkTouchOrShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(fruit)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(BlockLootAccessor.aether$hasShearsOrSilkTouch().invert())
                        .add(this.applyExplosionDecay(block,
                                LootItem.lootTableItem(AetherItems.SKYROOT_STICK.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingDoubleGoldenOak(Block original, Block block, Item item) {
        return LootTable.lootTable()
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(original)
                        .when(BlockLootAccessor.aether$hasSilkTouch()))))
                .withPool(this.applyExplosionDecay(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block)
                        .when(BlockLootAccessor.aether$hasSilkTouch().invert()))))
                .withPool(this.applyExplosionDecay(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)))
                        .when(BlockLootAccessor.aether$hasSilkTouch().invert())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))
                .apply(DoubleDrops.builder());
    }

    public LootTable.Builder droppingBerryBush(Block block, Block stem, Item drop) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(drop)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(BlockLootAccessor.aether$hasSilkTouch().invert())
                .apply(DoubleDrops.builder())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block))
                .when(BlockLootAccessor.aether$hasSilkTouch())
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
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.GINGERBREAD_MAN.get()).setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 6.0F)))))
                .add(this.applyExplosionDecay(block, LootItem.lootTableItem(AetherItems.CANDY_CANE_SWORD.get()).setWeight(1)))
                .when(BlockLootAccessor.aether$hasSilkTouch().invert())
        ).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block))
                .when(BlockLootAccessor.aether$hasSilkTouch())
        );
    }
}
