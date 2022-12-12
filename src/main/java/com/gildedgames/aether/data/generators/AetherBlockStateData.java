package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.providers.AetherBlockStateProvider;
import com.gildedgames.aether.block.AetherBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

public class AetherBlockStateData extends AetherBlockStateProvider {
    public AetherBlockStateData(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Aether.MODID, existingFileHelper);
    }

    @Override
    public void registerStatesAndModels() {
        this.portal(AetherBlocks.AETHER_PORTAL);

        this.grass(AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        this.enchantedGrass(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        this.randomBlockDoubleDrops(AetherBlocks.AETHER_DIRT, "natural/");
        this.randomBlockDoubleDrops(AetherBlocks.QUICKSOIL, "natural/");
        this.blockDoubleDrops(AetherBlocks.HOLYSTONE, "natural/");
        this.blockDoubleDrops(AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        this.farmland(AetherBlocks.AETHER_FARMLAND, AetherBlocks.AETHER_DIRT);
        this.dirtPath(AetherBlocks.AETHER_DIRT_PATH, AetherBlocks.AETHER_DIRT);

        this.translucentBlock(AetherBlocks.COLD_AERCLOUD, "natural/");
        this.translucentBlock(AetherBlocks.BLUE_AERCLOUD, "natural/");
        this.translucentBlock(AetherBlocks.GOLDEN_AERCLOUD, "natural/");
        this.translucentBlock(AetherBlocks.PINK_AERCLOUD, "natural/");

        this.block(AetherBlocks.ICESTONE, "natural/");
        this.block(AetherBlocks.AMBROSIUM_ORE, "natural/");
        this.block(AetherBlocks.ZANITE_ORE, "natural/");
        this.block(AetherBlocks.GRAVITITE_ORE, "natural/");

        this.block(AetherBlocks.SKYROOT_LEAVES, "natural/");
        this.block(AetherBlocks.GOLDEN_OAK_LEAVES, "natural/");
        this.block(AetherBlocks.CRYSTAL_LEAVES, "natural/");
        this.block(AetherBlocks.CRYSTAL_FRUIT_LEAVES, "natural/");
        this.block(AetherBlocks.HOLIDAY_LEAVES, "natural/");
        this.block(AetherBlocks.DECORATED_HOLIDAY_LEAVES, "natural/");

        this.log(AetherBlocks.SKYROOT_LOG);
        this.enchantedLog(AetherBlocks.GOLDEN_OAK_LOG, AetherBlocks.SKYROOT_LOG);
        this.log(AetherBlocks.STRIPPED_SKYROOT_LOG);
        this.wood(AetherBlocks.SKYROOT_WOOD, AetherBlocks.SKYROOT_LOG);
        this.wood(AetherBlocks.GOLDEN_OAK_WOOD, AetherBlocks.GOLDEN_OAK_LOG);
        this.wood(AetherBlocks.STRIPPED_SKYROOT_WOOD, AetherBlocks.STRIPPED_SKYROOT_LOG);

        this.block(AetherBlocks.SKYROOT_PLANKS, "construction/");
        this.block(AetherBlocks.HOLYSTONE_BRICKS, "construction/");
        this.translucentBlock(AetherBlocks.QUICKSOIL_GLASS, "construction/");
        this.pane(AetherBlocks.QUICKSOIL_GLASS_PANE, AetherBlocks.QUICKSOIL_GLASS, "construction/");
        this.translucentBlock(AetherBlocks.AEROGEL, "construction/");

        this.block(AetherBlocks.AMBROSIUM_BLOCK, "construction/");
        this.block(AetherBlocks.ZANITE_BLOCK, "construction/");
        this.block(AetherBlocks.ENCHANTED_GRAVITITE, "construction/");

        this.altar(AetherBlocks.ALTAR);
        this.freezer(AetherBlocks.FREEZER);
        this.incubator(AetherBlocks.INCUBATOR);

        this.torchBlock(AetherBlocks.AMBROSIUM_TORCH, AetherBlocks.AMBROSIUM_WALL_TORCH);

        this.signBlock(AetherBlocks.SKYROOT_SIGN, AetherBlocks.SKYROOT_WALL_SIGN, texture(name(AetherBlocks.SKYROOT_PLANKS), "construction/"));

        this.crossBlock(AetherBlocks.BERRY_BUSH_STEM, "natural/");
        this.berryBush(AetherBlocks.BERRY_BUSH, AetherBlocks.BERRY_BUSH_STEM);
        this.pottedStem(AetherBlocks.POTTED_BERRY_BUSH_STEM, "natural/");
        this.pottedBush(AetherBlocks.POTTED_BERRY_BUSH, AetherBlocks.POTTED_BERRY_BUSH_STEM, "natural/");

        this.crossBlock(AetherBlocks.PURPLE_FLOWER, "natural/");
        this.crossBlock(AetherBlocks.WHITE_FLOWER, "natural/");
        this.pottedPlant(AetherBlocks.POTTED_PURPLE_FLOWER, AetherBlocks.PURPLE_FLOWER, "natural/");
        this.pottedPlant(AetherBlocks.POTTED_WHITE_FLOWER, AetherBlocks.WHITE_FLOWER, "natural/");

        this.saplingBlock(AetherBlocks.SKYROOT_SAPLING, "natural/");
        this.saplingBlock(AetherBlocks.GOLDEN_OAK_SAPLING, "natural/");
        this.pottedPlant(AetherBlocks.POTTED_SKYROOT_SAPLING, AetherBlocks.SKYROOT_SAPLING, "natural/");
        this.pottedPlant(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING, AetherBlocks.GOLDEN_OAK_SAPLING, "natural/");

        this.block(AetherBlocks.CARVED_STONE, "dungeon/");
        this.block(AetherBlocks.SENTRY_STONE, "dungeon/");
        this.block(AetherBlocks.ANGELIC_STONE, "dungeon/");
        this.block(AetherBlocks.LIGHT_ANGELIC_STONE, "dungeon/");
        this.block(AetherBlocks.HELLFIRE_STONE, "dungeon/");
        this.block(AetherBlocks.LIGHT_HELLFIRE_STONE, "dungeon/");

        this.dungeonBlock(AetherBlocks.LOCKED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        this.dungeonBlock(AetherBlocks.LOCKED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        this.dungeonBlock(AetherBlocks.LOCKED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.LOCKED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        this.dungeonBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        this.dungeonBlock(AetherBlocks.TRAPPED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        this.dungeonBlock(AetherBlocks.TRAPPED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        this.dungeonBlock(AetherBlocks.TRAPPED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        this.dungeonBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_CARVED_STONE, AetherBlocks.CARVED_STONE);
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE, AetherBlocks.CARVED_STONE);
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        this.chestMimic(AetherBlocks.CHEST_MIMIC, () -> Blocks.OAK_PLANKS);
        this.treasureChest(AetherBlocks.TREASURE_CHEST, AetherBlocks.CARVED_STONE);

        this.pillar(AetherBlocks.PILLAR);
        this.pillarTop(AetherBlocks.PILLAR_TOP);

        this.present(AetherBlocks.PRESENT);

        this.fence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS, "construction/");
        this.fenceGateBlock(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS, "construction/");
        this.doorBlock(AetherBlocks.SKYROOT_DOOR, texture(name(AetherBlocks.SKYROOT_DOOR), "construction/", "_bottom"), texture(name(AetherBlocks.SKYROOT_DOOR), "construction/", "_top"));
        this.trapdoorBlock(AetherBlocks.SKYROOT_TRAPDOOR, texture(name(AetherBlocks.SKYROOT_TRAPDOOR), "construction/"), false);
        this.buttonBlock(AetherBlocks.SKYROOT_BUTTON, texture(name(AetherBlocks.SKYROOT_PLANKS), "construction/"));
        this.pressurePlateBlock(AetherBlocks.SKYROOT_PRESSURE_PLATE, texture(name(AetherBlocks.SKYROOT_PLANKS), "construction/"));

        this.buttonBlock(AetherBlocks.HOLYSTONE_BUTTON, texture(name(AetherBlocks.HOLYSTONE), "natural/"));
        this.pressurePlateBlock(AetherBlocks.HOLYSTONE_PRESSURE_PLATE, texture(name(AetherBlocks.HOLYSTONE), "natural/"));

        this.wallBlock(AetherBlocks.CARVED_WALL, AetherBlocks.CARVED_STONE, "dungeon/");
        this.wallBlock(AetherBlocks.ANGELIC_WALL, AetherBlocks.ANGELIC_STONE, "dungeon/");
        this.wallBlock(AetherBlocks.HELLFIRE_WALL, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        this.wallBlock(AetherBlocks.HOLYSTONE_WALL, AetherBlocks.HOLYSTONE, "natural/");
        this.wallBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        this.wallBlock(AetherBlocks.ICESTONE_WALL, AetherBlocks.ICESTONE, "natural/");
        this.wallBlock(AetherBlocks.HOLYSTONE_BRICK_WALL, AetherBlocks.HOLYSTONE_BRICKS, "construction/");

        this.stairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS, "construction/");
        this.stairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE, "dungeon/");
        this.stairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE, "dungeon/");
        this.stairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        this.stairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE, "natural/");
        this.stairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        this.stairs(AetherBlocks.ICESTONE_STAIRS, AetherBlocks.ICESTONE, "natural/");
        this.stairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS, "construction/");

        this.slab(AetherBlocks.SKYROOT_SLAB, AetherBlocks.SKYROOT_PLANKS, "construction/");
        this.slab(AetherBlocks.CARVED_SLAB, AetherBlocks.CARVED_STONE, "dungeon/");
        this.slab(AetherBlocks.ANGELIC_SLAB, AetherBlocks.ANGELIC_STONE, "dungeon/");
        this.slab(AetherBlocks.HELLFIRE_SLAB, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        this.slab(AetherBlocks.HOLYSTONE_SLAB, AetherBlocks.HOLYSTONE, "natural/");
        this.slab(AetherBlocks.MOSSY_HOLYSTONE_SLAB, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        this.slab(AetherBlocks.ICESTONE_SLAB, AetherBlocks.ICESTONE, "natural/");
        this.slab(AetherBlocks.HOLYSTONE_BRICK_SLAB, AetherBlocks.HOLYSTONE_BRICKS, "construction/");
        this.translucentSlab(AetherBlocks.AEROGEL_SLAB, AetherBlocks.AEROGEL, "construction/");

        this.sunAltar(AetherBlocks.SUN_ALTAR);

        this.bookshelf(AetherBlocks.SKYROOT_BOOKSHELF, AetherBlocks.SKYROOT_PLANKS);

        this.bed(AetherBlocks.SKYROOT_BED, AetherBlocks.SKYROOT_PLANKS);

        this.unstableObsidian(AetherBlocks.UNSTABLE_OBSIDIAN);
    }
}
