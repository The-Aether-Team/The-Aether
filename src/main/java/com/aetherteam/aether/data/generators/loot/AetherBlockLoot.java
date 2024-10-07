package com.aetherteam.aether.data.generators.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherBlockLootSubProvider;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.core.HolderLookup;
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

    public AetherBlockLoot(HolderLookup.Provider registries) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        this.dropNone(AetherBlocks.AETHER_PORTAL.get());

        this.dropDoubleWithSilk(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get());
        this.add(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), (block) -> this.createSingleItemTableWithSilkTouch(block, AetherBlocks.AETHER_DIRT.get()));
        this.dropSelfDouble(AetherBlocks.AETHER_DIRT.get());
        this.dropSelfDouble(AetherBlocks.QUICKSOIL.get());
        this.dropSelfDouble(AetherBlocks.HOLYSTONE.get());
        this.dropSelfDouble(AetherBlocks.MOSSY_HOLYSTONE.get());
        this.dropOther(AetherBlocks.AETHER_FARMLAND.get(), AetherBlocks.AETHER_DIRT.get());
        this.dropOther(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_DIRT.get());

        this.dropSelfDouble(AetherBlocks.COLD_AERCLOUD.get());
        this.dropSelfDouble(AetherBlocks.BLUE_AERCLOUD.get());
        this.dropSelfDouble(AetherBlocks.GOLDEN_AERCLOUD.get());

        this.dropSelf(AetherBlocks.ICESTONE.get());
        this.dropDoubleWithFortune(AetherBlocks.AMBROSIUM_ORE.get(), AetherItems.AMBROSIUM_SHARD.get());
        this.dropWithFortune(AetherBlocks.ZANITE_ORE.get(), AetherItems.ZANITE_GEMSTONE.get());
        this.dropSelf(AetherBlocks.GRAVITITE_ORE.get());
        this.add(AetherBlocks.SKYROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, AetherBlocks.SKYROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.add(AetherBlocks.GOLDEN_OAK_LEAVES.get(),
                (leaves) -> droppingGoldenOakLeaves(leaves, AetherBlocks.GOLDEN_OAK_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.add(AetherBlocks.CRYSTAL_LEAVES.get(),
                this::droppingWithSkyrootSticks);
        this.add(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(),
                (leaves) -> droppingWithFruitAndSkyrootSticks(leaves, AetherItems.WHITE_APPLE.get()));
        this.add(AetherBlocks.HOLIDAY_LEAVES.get(),
                this::droppingWithSkyrootSticks);
        this.add(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(),
                this::droppingWithSkyrootSticks);

        this.dropSelfDouble(AetherBlocks.SKYROOT_LOG.get());
        this.add(AetherBlocks.GOLDEN_OAK_LOG.get(),
                (log) -> droppingDoubleGoldenOak(log, AetherBlocks.SKYROOT_LOG.get(), AetherItems.GOLDEN_AMBER.get()));
        this.dropSelf(AetherBlocks.STRIPPED_SKYROOT_LOG.get());
        this.dropSelfDouble(AetherBlocks.SKYROOT_WOOD.get());
        this.add(AetherBlocks.GOLDEN_OAK_WOOD.get(),
                (wood) -> droppingDoubleGoldenOak(wood, AetherBlocks.SKYROOT_WOOD.get(), AetherItems.GOLDEN_AMBER.get()));
        this.dropSelf(AetherBlocks.STRIPPED_SKYROOT_WOOD.get());

        this.dropSelf(AetherBlocks.SKYROOT_PLANKS.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICKS.get());
        this.dropWhenSilkTouch(AetherBlocks.QUICKSOIL_GLASS.get());
        this.dropWhenSilkTouch(AetherBlocks.QUICKSOIL_GLASS_PANE.get());
        this.dropSelf(AetherBlocks.AEROGEL.get());

        this.dropSelf(AetherBlocks.AMBROSIUM_BLOCK.get());
        this.dropSelf(AetherBlocks.ZANITE_BLOCK.get());
        this.dropSelf(AetherBlocks.ENCHANTED_GRAVITITE.get());

        this.add(AetherBlocks.ALTAR.get(), this::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.FREEZER.get(), this::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.INCUBATOR.get(), this::droppingNameableBlockEntityTable);

        this.dropOther(AetherBlocks.AMBROSIUM_WALL_TORCH.get(), AetherBlocks.AMBROSIUM_TORCH.get());
        this.dropSelf(AetherBlocks.AMBROSIUM_TORCH.get());

        this.dropOther(AetherBlocks.SKYROOT_WALL_SIGN.get(), AetherBlocks.SKYROOT_SIGN.get());
        this.dropSelf(AetherBlocks.SKYROOT_SIGN.get());

        this.dropOther(AetherBlocks.SKYROOT_WALL_HANGING_SIGN.get(), AetherBlocks.SKYROOT_HANGING_SIGN.get());
        this.dropSelf(AetherBlocks.SKYROOT_HANGING_SIGN.get());

        this.add(AetherBlocks.BERRY_BUSH.get(), (bush) -> droppingBerryBush(bush, AetherBlocks.BERRY_BUSH_STEM.get(), AetherItems.BLUE_BERRY.get()));
        this.dropSelfDouble(AetherBlocks.BERRY_BUSH_STEM.get());
        this.dropPottedContents(AetherBlocks.POTTED_BERRY_BUSH.get());
        this.dropPottedContents(AetherBlocks.POTTED_BERRY_BUSH_STEM.get());

        this.dropSelf(AetherBlocks.PURPLE_FLOWER.get());
        this.dropSelf(AetherBlocks.WHITE_FLOWER.get());
        this.dropPottedContents(AetherBlocks.POTTED_PURPLE_FLOWER.get());
        this.dropPottedContents(AetherBlocks.POTTED_WHITE_FLOWER.get());

        this.dropSelf(AetherBlocks.SKYROOT_SAPLING.get());
        this.dropSelf(AetherBlocks.GOLDEN_OAK_SAPLING.get());
        this.dropPottedContents(AetherBlocks.POTTED_SKYROOT_SAPLING.get());
        this.dropPottedContents(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING.get());

        this.dropSelf(AetherBlocks.CARVED_STONE.get());
        this.dropSelf(AetherBlocks.SENTRY_STONE.get());
        this.dropSelf(AetherBlocks.ANGELIC_STONE.get());
        this.dropSelf(AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.dropSelf(AetherBlocks.HELLFIRE_STONE.get());
        this.dropSelf(AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.dropNone(AetherBlocks.LOCKED_CARVED_STONE.get());
        this.dropNone(AetherBlocks.LOCKED_SENTRY_STONE.get());
        this.dropNone(AetherBlocks.LOCKED_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.LOCKED_HELLFIRE_STONE.get());
        this.dropNone(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get());

        this.dropNone(AetherBlocks.TRAPPED_CARVED_STONE.get());
        this.dropNone(AetherBlocks.TRAPPED_SENTRY_STONE.get());
        this.dropNone(AetherBlocks.TRAPPED_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.TRAPPED_HELLFIRE_STONE.get());
        this.dropNone(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get());

        this.dropNone(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get());
        this.dropNone(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE.get());
        this.dropNone(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get());
        this.dropNone(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE.get());

        this.dropNone(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get());
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE.get());
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE.get());
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get());
        this.dropNone(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE.get());

        this.dropNone(AetherBlocks.CHEST_MIMIC.get());
        this.add(AetherBlocks.TREASURE_CHEST.get(), this::droppingTreasureChest);

        this.dropSelf(AetherBlocks.PILLAR.get());
        this.dropSelf(AetherBlocks.PILLAR_TOP.get());

        this.add(AetherBlocks.PRESENT.get(), this::droppingPresentLoot);

        this.dropSelf(AetherBlocks.SKYROOT_FENCE.get());
        this.dropSelf(AetherBlocks.SKYROOT_FENCE_GATE.get());
        this.add(AetherBlocks.SKYROOT_DOOR.get(), createDoorTable(AetherBlocks.SKYROOT_DOOR.get()));
        this.dropSelf(AetherBlocks.SKYROOT_TRAPDOOR.get());
        this.dropSelf(AetherBlocks.SKYROOT_BUTTON.get());
        this.dropSelf(AetherBlocks.SKYROOT_PRESSURE_PLATE.get());

        this.dropSelf(AetherBlocks.HOLYSTONE_BUTTON.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get());

        this.dropSelf(AetherBlocks.CARVED_WALL.get());
        this.dropSelf(AetherBlocks.ANGELIC_WALL.get());
        this.dropSelf(AetherBlocks.HELLFIRE_WALL.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_WALL.get());
        this.dropSelf(AetherBlocks.MOSSY_HOLYSTONE_WALL.get());
        this.dropSelf(AetherBlocks.ICESTONE_WALL.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICK_WALL.get());
        this.dropSelf(AetherBlocks.AEROGEL_WALL.get());

        this.dropSelf(AetherBlocks.SKYROOT_STAIRS.get());
        this.dropSelf(AetherBlocks.CARVED_STAIRS.get());
        this.dropSelf(AetherBlocks.ANGELIC_STAIRS.get());
        this.dropSelf(AetherBlocks.HELLFIRE_STAIRS.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_STAIRS.get());
        this.dropSelf(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get());
        this.dropSelf(AetherBlocks.ICESTONE_STAIRS.get());
        this.dropSelf(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get());
        this.dropSelf(AetherBlocks.AEROGEL_STAIRS.get());

        this.add(AetherBlocks.SKYROOT_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.CARVED_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.ANGELIC_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.HELLFIRE_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.ICESTONE_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.add(AetherBlocks.AEROGEL_SLAB.get(), this::createSlabItemTable);

        this.add(AetherBlocks.SUN_ALTAR.get(), this::droppingNameableBlockEntityTable);
        this.add(AetherBlocks.SKYROOT_BOOKSHELF.get(),
                (bookshelf) -> createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3)));
        this.add(AetherBlocks.SKYROOT_BED.get(),
                (bed) -> createSinglePropConditionTable(bed, BedBlock.PART, BedPart.HEAD));

        this.dropNone(AetherBlocks.FROSTED_ICE.get());
        this.dropNone(AetherBlocks.UNSTABLE_OBSIDIAN.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return AetherBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
