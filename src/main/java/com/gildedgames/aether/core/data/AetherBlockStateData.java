package com.gildedgames.aether.core.data;

import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.core.data.provider.AetherBlockStateProvider;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class AetherBlockStateData extends AetherBlockStateProvider
{
    public AetherBlockStateData(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Block States";
    }

    @Override
    protected void registerStatesAndModels() {
        grass(AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        enchantedGrass(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, AetherBlocks.AETHER_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);
        randomBlockDoubleDrops(AetherBlocks.AETHER_DIRT, "natural/");
        randomBlockDoubleDrops(AetherBlocks.QUICKSOIL, "natural/");
        blockDoubleDrops(AetherBlocks.HOLYSTONE, "natural/");
        blockDoubleDrops(AetherBlocks.MOSSY_HOLYSTONE, "natural/");

        block(AetherBlocks.COLD_AERCLOUD, "natural/");
        block(AetherBlocks.BLUE_AERCLOUD, "natural/");
        block(AetherBlocks.GOLDEN_AERCLOUD, "natural/");
        block(AetherBlocks.PINK_AERCLOUD, "natural/");

        block(AetherBlocks.ICESTONE, "natural/");
        block(AetherBlocks.AMBROSIUM_ORE, "natural/");
        block(AetherBlocks.ZANITE_ORE, "natural/");
        block(AetherBlocks.GRAVITITE_ORE, "natural/");

        block(AetherBlocks.SKYROOT_LEAVES, "natural/");
        block(AetherBlocks.GOLDEN_OAK_LEAVES, "natural/");
        block(AetherBlocks.CRYSTAL_LEAVES, "natural/");
        block(AetherBlocks.CRYSTAL_FRUIT_LEAVES, "natural/");
        block(AetherBlocks.HOLIDAY_LEAVES, "natural/");
        block(AetherBlocks.DECORATED_HOLIDAY_LEAVES, "natural/");

        log(AetherBlocks.SKYROOT_LOG);
        enchantedLog(AetherBlocks.GOLDEN_OAK_LOG, AetherBlocks.SKYROOT_LOG);
        log(AetherBlocks.STRIPPED_SKYROOT_LOG);
        wood(AetherBlocks.SKYROOT_WOOD, AetherBlocks.SKYROOT_LOG);
        wood(AetherBlocks.GOLDEN_OAK_WOOD, AetherBlocks.GOLDEN_OAK_LOG);
        wood(AetherBlocks.STRIPPED_SKYROOT_WOOD, AetherBlocks.STRIPPED_SKYROOT_LOG);

        block(AetherBlocks.SKYROOT_PLANKS, "construction/");
        block(AetherBlocks.HOLYSTONE_BRICKS, "construction/");
        block(AetherBlocks.QUICKSOIL_GLASS, "construction/");
        block(AetherBlocks.AEROGEL, "construction/");

        block(AetherBlocks.ZANITE_BLOCK, "construction/");
        block(AetherBlocks.ENCHANTED_GRAVITITE, "construction/");

        altar(AetherBlocks.ALTAR);
        freezer(AetherBlocks.FREEZER);
        incubator(AetherBlocks.INCUBATOR);

        torchBlock(AetherBlocks.AMBROSIUM_TORCH, AetherBlocks.AMBROSIUM_WALL_TORCH);

        crossBlock(AetherBlocks.BERRY_BUSH_STEM, "natural/");

        crossBlock(AetherBlocks.PURPLE_FLOWER, "natural/");
        crossBlock(AetherBlocks.WHITE_FLOWER, "natural/");

        saplingBlock(AetherBlocks.SKYROOT_SAPLING, "natural/");
        saplingBlock(AetherBlocks.GOLDEN_OAK_SAPLING, "natural/");

        block(AetherBlocks.CARVED_STONE, "dungeon/");
        block(AetherBlocks.SENTRY_STONE, "dungeon/");
        block(AetherBlocks.ANGELIC_STONE, "dungeon/");
        block(AetherBlocks.LIGHT_ANGELIC_STONE, "dungeon/");
        block(AetherBlocks.HELLFIRE_STONE, "dungeon/");
        block(AetherBlocks.LIGHT_HELLFIRE_STONE, "dungeon/");

        dungeonBlock(AetherBlocks.LOCKED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        dungeonBlock(AetherBlocks.LOCKED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        dungeonBlock(AetherBlocks.LOCKED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        dungeonBlock(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        dungeonBlock(AetherBlocks.LOCKED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        dungeonBlock(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        dungeonBlock(AetherBlocks.TRAPPED_CARVED_STONE, AetherBlocks.CARVED_STONE);
        dungeonBlock(AetherBlocks.TRAPPED_SENTRY_STONE, AetherBlocks.SENTRY_STONE);
        dungeonBlock(AetherBlocks.TRAPPED_ANGELIC_STONE, AetherBlocks.ANGELIC_STONE);
        dungeonBlock(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE, AetherBlocks.LIGHT_ANGELIC_STONE);
        dungeonBlock(AetherBlocks.TRAPPED_HELLFIRE_STONE, AetherBlocks.HELLFIRE_STONE);
        dungeonBlock(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE, AetherBlocks.LIGHT_HELLFIRE_STONE);

        chest(AetherBlocks.TREASURE_CHEST, AetherBlocks.CARVED_STONE);

        pillar(AetherBlocks.PILLAR);
        pillarTop(AetherBlocks.PILLAR_TOP);

        present(AetherBlocks.PRESENT);

        fence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS, "construction/");
        fenceGateBlock(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS, "construction/");
        doorBlock((DoorBlock) AetherBlocks.SKYROOT_DOOR.get(), texture(name(AetherBlocks.SKYROOT_DOOR), "construction/", "_bottom"), texture(name(AetherBlocks.SKYROOT_DOOR), "construction/", "_top"));
        trapdoorBlock((TrapDoorBlock) AetherBlocks.SKYROOT_TRAPDOOR.get(), texture(name(AetherBlocks.SKYROOT_TRAPDOOR), "construction/"), false);

        wallBlock(AetherBlocks.CARVED_WALL, AetherBlocks.CARVED_STONE, "dungeon/");
        wallBlock(AetherBlocks.ANGELIC_WALL, AetherBlocks.ANGELIC_STONE, "dungeon/");
        wallBlock(AetherBlocks.HELLFIRE_WALL, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        wallBlock(AetherBlocks.HOLYSTONE_WALL, AetherBlocks.HOLYSTONE, "natural/");
        wallBlock(AetherBlocks.MOSSY_HOLYSTONE_WALL, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        wallBlock(AetherBlocks.ICESTONE_WALL, AetherBlocks.ICESTONE, "natural/");
        wallBlock(AetherBlocks.HOLYSTONE_BRICK_WALL, AetherBlocks.HOLYSTONE_BRICKS, "construction/");

        stairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS, "construction/");
        stairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE, "dungeon/");
        stairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE, "dungeon/");
        stairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        stairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE, "natural/");
        stairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        stairs(AetherBlocks.ICESTONE_STAIRS, AetherBlocks.ICESTONE, "natural/");
        stairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS, "construction/");

        slab(AetherBlocks.SKYROOT_SLAB, AetherBlocks.SKYROOT_PLANKS, "construction/");
        slab(AetherBlocks.CARVED_SLAB, AetherBlocks.CARVED_STONE, "dungeon/");
        slab(AetherBlocks.ANGELIC_SLAB, AetherBlocks.ANGELIC_STONE, "dungeon/");
        slab(AetherBlocks.HELLFIRE_SLAB, AetherBlocks.HELLFIRE_STONE, "dungeon/");
        slab(AetherBlocks.HOLYSTONE_SLAB, AetherBlocks.HOLYSTONE, "natural/");
        slab(AetherBlocks.MOSSY_HOLYSTONE_SLAB, AetherBlocks.MOSSY_HOLYSTONE, "natural/");
        slab(AetherBlocks.ICESTONE_SLAB, AetherBlocks.ICESTONE, "natural/");
        slab(AetherBlocks.HOLYSTONE_BRICK_SLAB, AetherBlocks.HOLYSTONE_BRICKS, "construction/");
        slab(AetherBlocks.AEROGEL_SLAB, AetherBlocks.AEROGEL, "construction/");

        sunAltar(AetherBlocks.SUN_ALTAR);

        bookshelf(AetherBlocks.SKYROOT_BOOKSHELF, AetherBlocks.SKYROOT_PLANKS);

        bed(AetherBlocks.SKYROOT_BED, AetherBlocks.SKYROOT_PLANKS);
    }
}
