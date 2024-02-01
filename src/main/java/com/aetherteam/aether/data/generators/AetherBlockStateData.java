package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AetherBlockStateData extends AetherBlockStateProvider {
    public AetherBlockStateData(PackOutput output, ExistingFileHelper helper) {
        super(output, Aether.MODID, helper);
    }

    @Override
    public void registerStatesAndModels() {
        this.portal(AetherBlocks.AETHER_PORTAL.get());

        this.grass(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get());
        this.enchantedGrass(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get());
        this.randomBlockDoubleDrops(AetherBlocks.AETHER_DIRT.get(), "natural/");
        this.randomBlockDoubleDrops(AetherBlocks.QUICKSOIL.get(), "natural/");
        this.blockDoubleDrops(AetherBlocks.HOLYSTONE.get(), "natural/");
        this.blockDoubleDrops(AetherBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.farmland(AetherBlocks.AETHER_FARMLAND.get(), AetherBlocks.AETHER_DIRT.get());
        this.dirtPath(AetherBlocks.AETHER_DIRT_PATH.get(), AetherBlocks.AETHER_DIRT.get());

        this.aercloudAll(AetherBlocks.COLD_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherBlocks.BLUE_AERCLOUD.get(), "natural/");
        this.aercloudAll(AetherBlocks.GOLDEN_AERCLOUD.get(), "natural/");

        this.block(AetherBlocks.ICESTONE.get(), "natural/");
        this.block(AetherBlocks.AMBROSIUM_ORE.get(), "natural/");
        this.block(AetherBlocks.ZANITE_ORE.get(), "natural/");
        this.block(AetherBlocks.GRAVITITE_ORE.get(), "natural/");

        this.block(AetherBlocks.SKYROOT_LEAVES.get(), "natural/");
        this.block(AetherBlocks.GOLDEN_OAK_LEAVES.get(), "natural/");
        this.block(AetherBlocks.CRYSTAL_LEAVES.get(), "natural/");
        this.block(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get(), "natural/");
        this.block(AetherBlocks.HOLIDAY_LEAVES.get(), "natural/");
        this.block(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get(), "natural/");

        this.log(AetherBlocks.SKYROOT_LOG.get());
        this.enchantedLog(AetherBlocks.GOLDEN_OAK_LOG.get(), AetherBlocks.SKYROOT_LOG.get());
        this.log(AetherBlocks.STRIPPED_SKYROOT_LOG.get());
        this.wood(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.SKYROOT_LOG.get());
        this.wood(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.GOLDEN_OAK_LOG.get());
        this.wood(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get());

        this.block(AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.block(AetherBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.translucentBlock(AetherBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.pane(AetherBlocks.QUICKSOIL_GLASS_PANE.get(), AetherBlocks.QUICKSOIL_GLASS.get(), "construction/");
        this.translucentBlock(AetherBlocks.AEROGEL.get(), "construction/");

        this.block(AetherBlocks.AMBROSIUM_BLOCK.get(), "construction/");
        this.block(AetherBlocks.ZANITE_BLOCK.get(), "construction/");
        this.block(AetherBlocks.ENCHANTED_GRAVITITE.get(), "construction/");

        this.altar(AetherBlocks.ALTAR.get());
        this.freezer(AetherBlocks.FREEZER.get());
        this.incubator(AetherBlocks.INCUBATOR.get());

        this.torchBlock(AetherBlocks.AMBROSIUM_TORCH.get(), AetherBlocks.AMBROSIUM_WALL_TORCH.get());

        this.signBlock(AetherBlocks.SKYROOT_SIGN.get(), AetherBlocks.SKYROOT_WALL_SIGN.get(), this.texture(this.name(AetherBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.hangingSignBlock(AetherBlocks.SKYROOT_HANGING_SIGN.get(), AetherBlocks.SKYROOT_WALL_HANGING_SIGN.get(), this.texture(this.name(AetherBlocks.STRIPPED_SKYROOT_LOG.get()), "natural/"));

        this.crossBlock(AetherBlocks.BERRY_BUSH_STEM.get(), "natural/");
        this.berryBush(AetherBlocks.BERRY_BUSH.get(), AetherBlocks.BERRY_BUSH_STEM.get());
        this.pottedStem(AetherBlocks.POTTED_BERRY_BUSH_STEM.get(), "natural/");
        this.pottedBush(AetherBlocks.POTTED_BERRY_BUSH.get(), AetherBlocks.POTTED_BERRY_BUSH_STEM.get(), "natural/");

        this.crossBlock(AetherBlocks.PURPLE_FLOWER.get(), "natural/");
        this.crossBlock(AetherBlocks.WHITE_FLOWER.get(), "natural/");
        this.pottedPlant(AetherBlocks.POTTED_PURPLE_FLOWER.get(), AetherBlocks.PURPLE_FLOWER.get(), "natural/");
        this.pottedPlant(AetherBlocks.POTTED_WHITE_FLOWER.get(), AetherBlocks.WHITE_FLOWER.get(), "natural/");

        this.saplingBlock(AetherBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.saplingBlock(AetherBlocks.GOLDEN_OAK_SAPLING.get(), "natural/");
        this.pottedPlant(AetherBlocks.POTTED_SKYROOT_SAPLING.get(), AetherBlocks.SKYROOT_SAPLING.get(), "natural/");
        this.pottedPlant(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING.get(), AetherBlocks.GOLDEN_OAK_SAPLING.get(), "natural/");

        this.block(AetherBlocks.CARVED_STONE.get(), "dungeon/");
        this.block(AetherBlocks.SENTRY_STONE.get(), "dungeon/");
        this.block(AetherBlocks.ANGELIC_STONE.get(), "dungeon/");
        this.block(AetherBlocks.LIGHT_ANGELIC_STONE.get(), "dungeon/");
        this.block(AetherBlocks.HELLFIRE_STONE.get(), "dungeon/");
        this.block(AetherBlocks.LIGHT_HELLFIRE_STONE.get(), "dungeon/");

        this.dungeonBlock(AetherBlocks.LOCKED_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.dungeonBlock(AetherBlocks.LOCKED_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.dungeonBlock(AetherBlocks.LOCKED_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.LOCKED_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.dungeonBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.dungeonBlock(AetherBlocks.TRAPPED_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.dungeonBlock(AetherBlocks.TRAPPED_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.dungeonBlock(AetherBlocks.TRAPPED_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.dungeonBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.invisibleBlock(AetherBlocks.BOSS_DOORWAY_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_CARVED_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_SENTRY_STONE.get(), AetherBlocks.SENTRY_STONE.get());
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.dungeonBlock(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.chestMimic(AetherBlocks.CHEST_MIMIC.get(), Blocks.OAK_PLANKS);
        this.treasureChest(AetherBlocks.TREASURE_CHEST.get(), AetherBlocks.CARVED_STONE.get());

        this.pillar(AetherBlocks.PILLAR.get());
        this.pillarTop(AetherBlocks.PILLAR_TOP.get());

        this.present(AetherBlocks.PRESENT.get());

        this.fence(AetherBlocks.SKYROOT_FENCE.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.fenceGateBlock(AetherBlocks.SKYROOT_FENCE_GATE.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.doorBlock(AetherBlocks.SKYROOT_DOOR.get(), this.texture(this.name(AetherBlocks.SKYROOT_DOOR.get()), "construction/", "_bottom"), this.texture(this.name(AetherBlocks.SKYROOT_DOOR.get()), "construction/", "_top"));
        this.trapdoorBlock(AetherBlocks.SKYROOT_TRAPDOOR.get(), this.texture(this.name(AetherBlocks.SKYROOT_TRAPDOOR.get()), "construction/"), false);
        this.buttonBlock(AetherBlocks.SKYROOT_BUTTON.get(), this.texture(this.name(AetherBlocks.SKYROOT_PLANKS.get()), "construction/"));
        this.pressurePlateBlock(AetherBlocks.SKYROOT_PRESSURE_PLATE.get(), this.texture(this.name(AetherBlocks.SKYROOT_PLANKS.get()), "construction/"));

        this.buttonBlock(AetherBlocks.HOLYSTONE_BUTTON.get(), this.texture(this.name(AetherBlocks.HOLYSTONE.get()), "natural/"));
        this.pressurePlateBlock(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get(), this.texture(this.name(AetherBlocks.HOLYSTONE.get()), "natural/"));

        this.wallBlock(AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get(), "dungeon/");
        this.wallBlock(AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get(), "dungeon/");
        this.wallBlock(AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get(), "dungeon/");
        this.wallBlock(AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.wallBlock(AetherBlocks.ICESTONE_WALL.get(), AetherBlocks.ICESTONE.get(), "natural/");
        this.wallBlock(AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        this.stairs(AetherBlocks.SKYROOT_STAIRS.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.stairs(AetherBlocks.CARVED_STAIRS.get(), AetherBlocks.CARVED_STONE.get(), "dungeon/");
        this.stairs(AetherBlocks.ANGELIC_STAIRS.get(), AetherBlocks.ANGELIC_STONE.get(), "dungeon/");
        this.stairs(AetherBlocks.HELLFIRE_STAIRS.get(), AetherBlocks.HELLFIRE_STONE.get(), "dungeon/");
        this.stairs(AetherBlocks.HOLYSTONE_STAIRS.get(), AetherBlocks.HOLYSTONE.get(), "natural/");
        this.stairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.stairs(AetherBlocks.ICESTONE_STAIRS.get(), AetherBlocks.ICESTONE.get(), "natural/");
        this.stairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), "construction/");

        this.slab(AetherBlocks.SKYROOT_SLAB.get(), AetherBlocks.SKYROOT_PLANKS.get(), "construction/");
        this.slab(AetherBlocks.CARVED_SLAB.get(), AetherBlocks.CARVED_STONE.get(), "dungeon/");
        this.slab(AetherBlocks.ANGELIC_SLAB.get(), AetherBlocks.ANGELIC_STONE.get(), "dungeon/");
        this.slab(AetherBlocks.HELLFIRE_SLAB.get(), AetherBlocks.HELLFIRE_STONE.get(), "dungeon/");
        this.slab(AetherBlocks.HOLYSTONE_SLAB.get(), AetherBlocks.HOLYSTONE.get(), "natural/");
        this.slab(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), "natural/");
        this.slab(AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE.get(), "natural/");
        this.slab(AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), "construction/");
        this.translucentSlab(AetherBlocks.AEROGEL_SLAB.get(), AetherBlocks.AEROGEL.get(), "construction/");

        this.sunAltar(AetherBlocks.SUN_ALTAR.get());
        this.bookshelf(AetherBlocks.SKYROOT_BOOKSHELF.get(), AetherBlocks.SKYROOT_PLANKS.get());
        this.bed(AetherBlocks.SKYROOT_BED.get(), AetherBlocks.SKYROOT_PLANKS.get());

        this.frostedIce(AetherBlocks.FROSTED_ICE.get(), Blocks.FROSTED_ICE);
        this.unstableObsidian(AetherBlocks.UNSTABLE_OBSIDIAN.get());
    }
}
