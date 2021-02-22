package com.aether.data;

import com.aether.data.provider.AetherLootTableProvider;
import com.aether.registry.AetherBlocks;
import com.aether.registry.AetherEntityTypes;
import com.aether.registry.AetherItems;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.KilledByPlayer;
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

//    public static class Blocks extends AetherBlockLootTableProvider
//    {
//
//    }

    public static class Entities extends EntityLootTables
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
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_BLACK, sheepLootTableBuilderWithDrop(Blocks.BLACK_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_BLUE, sheepLootTableBuilderWithDrop(Blocks.BLUE_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_BROWN, sheepLootTableBuilderWithDrop(Blocks.BROWN_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_CYAN, sheepLootTableBuilderWithDrop(Blocks.CYAN_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_GRAY, sheepLootTableBuilderWithDrop(Blocks.GRAY_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_GREEN, sheepLootTableBuilderWithDrop(Blocks.GREEN_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_LIGHT_BLUE, sheepLootTableBuilderWithDrop(Blocks.LIGHT_BLUE_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_LIGHT_GRAY, sheepLootTableBuilderWithDrop(Blocks.LIGHT_GRAY_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_LIME, sheepLootTableBuilderWithDrop(Blocks.LIME_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_MAGENTA, sheepLootTableBuilderWithDrop(Blocks.MAGENTA_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_ORANGE, sheepLootTableBuilderWithDrop(Blocks.ORANGE_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_PINK, sheepLootTableBuilderWithDrop(Blocks.PINK_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_PURPLE, sheepLootTableBuilderWithDrop(Blocks.PURPLE_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_RED, sheepLootTableBuilderWithDrop(Blocks.RED_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_WHITE, sheepLootTableBuilderWithDrop(Blocks.WHITE_WOOL));
            this.registerLootTable(com.aether.registry.AetherLootTables.ENTITIES_SHEEPUFF_YELLOW, sheepLootTableBuilderWithDrop(Blocks.YELLOW_WOOL));

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
