package com.aetherteam.aether.data.generators.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.AetherLoot;
import io.github.fabricators_of_create.porting_lib.data.ModdedEntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class AetherEntityLoot extends ModdedEntityLootSubProvider {
    public AetherEntityLoot() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
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
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_BLACK, createSheepuffTable(Blocks.BLACK_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_BLUE, createSheepuffTable(Blocks.BLUE_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_BROWN, createSheepuffTable(Blocks.BROWN_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_CYAN, createSheepuffTable(Blocks.CYAN_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_GRAY, createSheepuffTable(Blocks.GRAY_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_GREEN, createSheepuffTable(Blocks.GREEN_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_LIGHT_BLUE, createSheepuffTable(Blocks.LIGHT_BLUE_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_LIGHT_GRAY, createSheepuffTable(Blocks.LIGHT_GRAY_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_LIME, createSheepuffTable(Blocks.LIME_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_MAGENTA, createSheepuffTable(Blocks.MAGENTA_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_ORANGE, createSheepuffTable(Blocks.ORANGE_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_PINK, createSheepuffTable(Blocks.PINK_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_PURPLE, createSheepuffTable(Blocks.PURPLE_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_RED, createSheepuffTable(Blocks.RED_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_WHITE, createSheepuffTable(Blocks.WHITE_WOOL));
        this.add(AetherEntityTypes.SHEEPUFF.get(), AetherLoot.ENTITIES_SHEEPUFF_YELLOW, createSheepuffTable(Blocks.YELLOW_WOOL));

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
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 3.0F))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_DART.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(AetherItems.ENCHANTED_DART.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(AetherItems.POISON_DART.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
                        .add(LootItem.lootTableItem(AetherItems.SKYROOT_PICKAXE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherItems.IRON_RING.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherItems.GOLDEN_AMBER.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherItems.ZANITE_GEMSTONE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherItems.HOLYSTONE_PICKAXE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherBlocks.ICESTONE.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherItems.AMBROSIUM_SHARD.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                        .add(LootItem.lootTableItem(AetherBlocks.AMBROSIUM_TORCH.get()).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
                )
        );

        this.add(AetherEntityTypes.VALKYRIE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.VICTORY_MEDAL.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
        );

        this.add(AetherEntityTypes.FIRE_MINION.get(), LootTable.lootTable());

        this.add(AetherEntityTypes.SLIDER.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.BRONZE_DUNGEON_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherBlocks.CARVED_STONE.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(7.0F, 9.0F)))
                        )
                )
        );

        this.add(AetherEntityTypes.VALKYRIE_QUEEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.SILVER_DUNGEON_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.GOLDEN_SWORD)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
        );

        this.add(AetherEntityTypes.SUN_SPIRIT.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherItems.GOLD_DUNGEON_KEY.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(AetherBlocks.SUN_ALTAR.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        )
                )
        );
    }

    private static LootTable.Builder createSheepuffTable(ItemLike wool) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(wool)))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootTableReference.lootTableReference(AetherEntityTypes.SHEEPUFF.get().getDefaultLootTable())));
    }

    @Override
    public Stream<EntityType<?>> getKnownEntityTypes() {
        return AetherEntityTypes.ENTITY_TYPES.getEntries().stream().flatMap(entityType -> Stream.of(entityType.get()));
    }
}
