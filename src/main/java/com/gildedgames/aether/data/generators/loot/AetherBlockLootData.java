package com.gildedgames.aether.data.generators.loot;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.data.providers.AetherBlockLootProvider;
import com.gildedgames.aether.item.AetherItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AetherBlockLootData extends AetherBlockLootProvider {
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
        drop(AetherBlocks.AETHER_DIRT_PATH, AetherBlocks.AETHER_DIRT);

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
                AetherBlockLootProvider::droppingWithSkyrootSticks);
        this.add(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(),
                (leaves) -> droppingWithFruitAndSkyrootSticks(leaves, AetherItems.WHITE_APPLE.get()));
        this.add(AetherBlocks.HOLIDAY_LEAVES.get(),
                AetherBlockLootProvider::droppingWithSkyrootSticks);
        this.add(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(),
                AetherBlockLootProvider::droppingWithSkyrootSticks);

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
        dropSilk(AetherBlocks.QUICKSOIL_GLASS_PANE);
        dropSelf(AetherBlocks.AEROGEL);

        dropSelf(AetherBlocks.AMBROSIUM_BLOCK);
        dropSelf(AetherBlocks.ZANITE_BLOCK);
        dropSelf(AetherBlocks.ENCHANTED_GRAVITITE);

        this.add(AetherBlocks.ALTAR.get(), AetherBlockLootProvider::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.FREEZER.get(), AetherBlockLootProvider::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.INCUBATOR.get(), AetherBlockLootProvider::droppingNameableBlockEntityTable);

        drop(AetherBlocks.AMBROSIUM_WALL_TORCH, AetherBlocks.AMBROSIUM_TORCH);
        dropSelf(AetherBlocks.AMBROSIUM_TORCH);

        drop(AetherBlocks.SKYROOT_WALL_SIGN, AetherBlocks.SKYROOT_SIGN);
        dropSelf(AetherBlocks.SKYROOT_SIGN);

        this.add(AetherBlocks.BERRY_BUSH.get(), (bush) -> droppingBerryBush(bush, AetherBlocks.BERRY_BUSH_STEM.get(), AetherItems.BLUE_BERRY.get()));
        dropSelfDouble(AetherBlocks.BERRY_BUSH_STEM);
        dropPot(AetherBlocks.POTTED_BERRY_BUSH);
        dropPot(AetherBlocks.POTTED_BERRY_BUSH_STEM);

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

        dropNone(AetherBlocks.BOSS_DOORWAY_CARVED_STONE);
        dropNone(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE);
        dropNone(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE);

        dropNone(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE);
        dropNone(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE);
        dropNone(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE);

        dropNone(AetherBlocks.CHEST_MIMIC);
        this.add(AetherBlocks.TREASURE_CHEST.get(), AetherBlockLootProvider::droppingTreasureChest);

        dropSelf(AetherBlocks.PILLAR);
        dropSelf(AetherBlocks.PILLAR_TOP);

        this.add(AetherBlocks.PRESENT.get(), AetherBlockLootProvider::droppingPresentLoot);

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

        this.add(AetherBlocks.SUN_ALTAR.get(), AetherBlockLootProvider::droppingNameableBlockEntityTable);

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
