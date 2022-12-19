package com.gildedgames.aether.data.generators.loot;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.data.providers.AetherBlockLootSubProvider;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AetherBlockLoot extends AetherBlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    public AetherBlockLoot() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.dropNone(AetherBlocks.AETHER_PORTAL);

        this.dropDoubleWithSilk(AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        this.dropDoubleWithSilk(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        this.dropSelfDouble(AetherBlocks.AETHER_DIRT);
        this.dropSelfDouble(AetherBlocks.QUICKSOIL);
        this.dropSelfDouble(AetherBlocks.HOLYSTONE);
        this.dropSelfDouble(AetherBlocks.MOSSY_HOLYSTONE);
        this.drop(AetherBlocks.AETHER_FARMLAND, AetherBlocks.AETHER_DIRT);
        this.drop(AetherBlocks.AETHER_DIRT_PATH, AetherBlocks.AETHER_DIRT);

        this.dropSelfDouble(AetherBlocks.COLD_AERCLOUD);
        this.dropSelfDouble(AetherBlocks.BLUE_AERCLOUD);
        this.dropSelfDouble(AetherBlocks.GOLDEN_AERCLOUD);
        this.dropSelfDouble(AetherBlocks.PINK_AERCLOUD);

        this.dropSelf(AetherBlocks.ICESTONE);
        this.dropDoubleWithFortune(AetherBlocks.AMBROSIUM_ORE, AetherItems.AMBROSIUM_SHARD);
        this.dropWithFortune(AetherBlocks.ZANITE_ORE, AetherItems.ZANITE_GEMSTONE);
        this.dropSelf(AetherBlocks.GRAVITITE_ORE);
        this.add(AetherBlocks.SKYROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.SKYROOT_SAPLING.get(), BlockLootAccessor.getNormalLeavesSaplingChances()));
        this.add(AetherBlocks.GOLDEN_OAK_LEAVES.get(),
                (leaves) -> droppingGoldenOakLeaves(leaves, AetherBlocks.GOLDEN_OAK_SAPLING.get(), BlockLootAccessor.getNormalLeavesSaplingChances()));
        this.add(AetherBlocks.CRYSTAL_LEAVES.get(),
                this::droppingWithSkyrootSticks);
        this.add(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(),
                (leaves) -> droppingWithFruitAndSkyrootSticks(leaves, AetherItems.WHITE_APPLE.get()));
        this.add(AetherBlocks.HOLIDAY_LEAVES.get(),
                this::droppingWithSkyrootSticks);
        this.add(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(),
                this::droppingWithSkyrootSticks);

        this.dropSelfDouble(AetherBlocks.SKYROOT_LOG);
        this.add(AetherBlocks.GOLDEN_OAK_LOG.get(),
                (log) -> droppingDoubleGoldenOak(log, AetherBlocks.SKYROOT_LOG.get(), AetherItems.GOLDEN_AMBER.get()));
        this.dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_LOG);
        this.dropSelfDouble(AetherBlocks.SKYROOT_WOOD);
        this.add(AetherBlocks.GOLDEN_OAK_WOOD.get(),
                (wood) -> droppingDoubleGoldenOak(wood, AetherBlocks.SKYROOT_WOOD.get(), AetherItems.GOLDEN_AMBER.get()));
        this.dropSelfDouble(AetherBlocks.STRIPPED_SKYROOT_WOOD);

        this.dropSelf(AetherBlocks.SKYROOT_PLANKS);
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICKS);
        this.dropSilk(AetherBlocks.QUICKSOIL_GLASS);
        this.dropSilk(AetherBlocks.QUICKSOIL_GLASS_PANE);
        this.dropSelf(AetherBlocks.AEROGEL);

        this.dropSelf(AetherBlocks.AMBROSIUM_BLOCK);
        this.dropSelf(AetherBlocks.ZANITE_BLOCK);
        this.dropSelf(AetherBlocks.ENCHANTED_GRAVITITE);

        this.add(AetherBlocks.ALTAR.get(), this::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.FREEZER.get(), this::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.INCUBATOR.get(), this::droppingNameableBlockEntityTable);

        this.drop(AetherBlocks.AMBROSIUM_WALL_TORCH, AetherBlocks.AMBROSIUM_TORCH);
        this.dropSelf(AetherBlocks.AMBROSIUM_TORCH);

        this.drop(AetherBlocks.SKYROOT_WALL_SIGN, AetherBlocks.SKYROOT_SIGN);
        this.dropSelf(AetherBlocks.SKYROOT_SIGN);

        this.add(AetherBlocks.BERRY_BUSH.get(), (bush) -> droppingBerryBush(bush, AetherBlocks.BERRY_BUSH_STEM.get(), AetherItems.BLUE_BERRY.get()));
        this.dropSelfDouble(AetherBlocks.BERRY_BUSH_STEM);
        this.dropPot(AetherBlocks.POTTED_BERRY_BUSH);
        this.dropPot(AetherBlocks.POTTED_BERRY_BUSH_STEM);

        this.dropSelf(AetherBlocks.PURPLE_FLOWER);
        this.dropSelf(AetherBlocks.WHITE_FLOWER);
        this.dropPot(AetherBlocks.POTTED_PURPLE_FLOWER);
        this.dropPot(AetherBlocks.POTTED_WHITE_FLOWER);

        this.dropSelf(AetherBlocks.SKYROOT_SAPLING);
        this.dropSelf(AetherBlocks.GOLDEN_OAK_SAPLING);
        this.dropPot(AetherBlocks.POTTED_SKYROOT_SAPLING);
        this.dropPot(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING);

        this.dropSelf(AetherBlocks.CARVED_STONE);
        this.dropSelf(AetherBlocks.SENTRY_STONE);
        this.dropSelf(AetherBlocks.ANGELIC_STONE);
        this.dropSelf(AetherBlocks.LIGHT_ANGELIC_STONE);
        this.dropSelf(AetherBlocks.HELLFIRE_STONE);
        this.dropSelf(AetherBlocks.LIGHT_HELLFIRE_STONE);

        this.dropNone(AetherBlocks.LOCKED_CARVED_STONE);
        this.dropNone(AetherBlocks.LOCKED_SENTRY_STONE);
        this.dropNone(AetherBlocks.LOCKED_ANGELIC_STONE);
        this.dropNone(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE);
        this.dropNone(AetherBlocks.LOCKED_HELLFIRE_STONE);
        this.dropNone(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE);

        this.dropNone(AetherBlocks.TRAPPED_CARVED_STONE);
        this.dropNone(AetherBlocks.TRAPPED_SENTRY_STONE);
        this.dropNone(AetherBlocks.TRAPPED_ANGELIC_STONE);
        this.dropNone(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE);
        this.dropNone(AetherBlocks.TRAPPED_HELLFIRE_STONE);
        this.dropNone(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE);

        this.dropNone(AetherBlocks.BOSS_DOORWAY_CARVED_STONE);
        this.dropNone(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE);
        this.dropNone(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE);
        this.dropNone(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE);
        this.dropNone(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE);
        this.dropNone(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE);

        this.dropNone(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE);
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE);
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE);
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE);
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE);
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE);

        this.dropNone(AetherBlocks.CHEST_MIMIC);
        this.add(AetherBlocks.TREASURE_CHEST.get(), this::droppingTreasureChest);

        this.dropSelf(AetherBlocks.PILLAR);
        this.dropSelf(AetherBlocks.PILLAR_TOP);

        this.add(AetherBlocks.PRESENT.get(), this::droppingPresentLoot);

        this.dropSelf(AetherBlocks.SKYROOT_FENCE);
        this.dropSelf(AetherBlocks.SKYROOT_FENCE_GATE);
        this.add(AetherBlocks.SKYROOT_DOOR.get(), createDoorTable(AetherBlocks.SKYROOT_DOOR.get()));
        this.dropSelf(AetherBlocks.SKYROOT_TRAPDOOR);
        this.dropSelf(AetherBlocks.SKYROOT_BUTTON);
        this.dropSelf(AetherBlocks.SKYROOT_PRESSURE_PLATE);

        this.dropSelf(AetherBlocks.HOLYSTONE_BUTTON);
        this.dropSelf(AetherBlocks.HOLYSTONE_PRESSURE_PLATE);

        this.dropSelf(AetherBlocks.CARVED_WALL);
        this.dropSelf(AetherBlocks.ANGELIC_WALL);
        this.dropSelf(AetherBlocks.HELLFIRE_WALL);
        this.dropSelf(AetherBlocks.HOLYSTONE_WALL);
        this.dropSelf(AetherBlocks.MOSSY_HOLYSTONE_WALL);
        this.dropSelf(AetherBlocks.ICESTONE_WALL);
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICK_WALL);
        this.dropSelf(AetherBlocks.AEROGEL_WALL);

        this.dropSelf(AetherBlocks.SKYROOT_STAIRS);
        this.dropSelf(AetherBlocks.CARVED_STAIRS);
        this.dropSelf(AetherBlocks.ANGELIC_STAIRS);
        this.dropSelf(AetherBlocks.HELLFIRE_STAIRS);
        this.dropSelf(AetherBlocks.HOLYSTONE_STAIRS);
        this.dropSelf(AetherBlocks.MOSSY_HOLYSTONE_STAIRS);
        this.dropSelf(AetherBlocks.ICESTONE_STAIRS);
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICK_STAIRS);
        this.dropSelf(AetherBlocks.AEROGEL_STAIRS);

        this.dropSelf(AetherBlocks.SKYROOT_SLAB);
        this.dropSelf(AetherBlocks.CARVED_SLAB);
        this.dropSelf(AetherBlocks.ANGELIC_SLAB);
        this.dropSelf(AetherBlocks.HELLFIRE_SLAB);
        this.dropSelf(AetherBlocks.HOLYSTONE_SLAB);
        this.dropSelf(AetherBlocks.MOSSY_HOLYSTONE_SLAB);
        this.dropSelf(AetherBlocks.ICESTONE_SLAB);
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICK_SLAB);
        this.dropSelf(AetherBlocks.AEROGEL_SLAB);

        this.add(AetherBlocks.SUN_ALTAR.get(), this::droppingNameableBlockEntityTable);

        this.add(AetherBlocks.SKYROOT_BOOKSHELF.get(),
                (bookshelf) -> createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));

        this.add(AetherBlocks.SKYROOT_BED.get(),
                (bed) -> createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));

        this.dropNone(AetherBlocks.UNSTABLE_OBSIDIAN);
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
