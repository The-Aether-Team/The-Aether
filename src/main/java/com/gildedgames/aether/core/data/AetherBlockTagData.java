package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class AetherBlockTagData extends BlockTagsProvider
{
    public AetherBlockTagData(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Aether.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Aether Block Tags";
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags() {
        //aether
        tag(AetherTags.Blocks.AETHER_DIRT)
                .add(AetherBlocks.AETHER_GRASS_BLOCK.get())
                .add(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())
                .add(AetherBlocks.AETHER_DIRT.get());
        tag(AetherTags.Blocks.HOLYSTONE)
                .add(AetherBlocks.HOLYSTONE.get())
                .add(AetherBlocks.MOSSY_HOLYSTONE.get());
        tag(AetherTags.Blocks.AETHER_ISLAND_BLOCKS)
                .add(AetherBlocks.AETHER_DIRT.get())
                .add(AetherBlocks.AETHER_GRASS_BLOCK.get())
                .add(AetherBlocks.HOLYSTONE.get());
        tag(AetherTags.Blocks.AERCLOUDS)
                .add(AetherBlocks.COLD_AERCLOUD.get())
                .add(AetherBlocks.BLUE_AERCLOUD.get())
                .add(AetherBlocks.GOLDEN_AERCLOUD.get())
                .add(AetherBlocks.PINK_AERCLOUD.get());
        tag(AetherTags.Blocks.SKYROOT_LOGS)
                .add(AetherBlocks.SKYROOT_LOG.get())
                .add(AetherBlocks.SKYROOT_WOOD.get())
                .add(AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .add(AetherBlocks.STRIPPED_SKYROOT_WOOD.get());
        tag(AetherTags.Blocks.GOLDEN_OAK_LOGS)
                .add(AetherBlocks.GOLDEN_OAK_LOG.get())
                .add(AetherBlocks.GOLDEN_OAK_WOOD.get());
        tag(AetherTags.Blocks.AEROGEL)
                .add(AetherBlocks.AEROGEL.get())
                .add(AetherBlocks.AEROGEL_WALL.get())
                .add(AetherBlocks.AEROGEL_STAIRS.get())
                .add(AetherBlocks.AEROGEL_SLAB.get());
        tag(AetherTags.Blocks.DUNGEON_BLOCKS)
                .add(AetherBlocks.CARVED_STONE.get())
                .add(AetherBlocks.SENTRY_STONE.get())
                .add(AetherBlocks.ANGELIC_STONE.get())
                .add(AetherBlocks.LIGHT_ANGELIC_STONE.get())
                .add(AetherBlocks.HELLFIRE_STONE.get())
                .add(AetherBlocks.LIGHT_HELLFIRE_STONE.get());
        tag(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)
                .add(AetherBlocks.LOCKED_CARVED_STONE.get())
                .add(AetherBlocks.LOCKED_SENTRY_STONE.get())
                .add(AetherBlocks.LOCKED_ANGELIC_STONE.get())
                .add(AetherBlocks.LOCKED_LIGHT_ANGELIC_STONE.get())
                .add(AetherBlocks.LOCKED_HELLFIRE_STONE.get())
                .add(AetherBlocks.LOCKED_LIGHT_HELLFIRE_STONE.get());
        tag(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS)
                .add(AetherBlocks.TRAPPED_CARVED_STONE.get())
                .add(AetherBlocks.TRAPPED_SENTRY_STONE.get())
                .add(AetherBlocks.TRAPPED_ANGELIC_STONE.get())
                .add(AetherBlocks.TRAPPED_LIGHT_ANGELIC_STONE.get())
                .add(AetherBlocks.TRAPPED_HELLFIRE_STONE.get())
                .add(AetherBlocks.TRAPPED_LIGHT_HELLFIRE_STONE.get());
        tag(AetherTags.Blocks.AETHER_PORTAL_BLOCKS)
                .add(Blocks.GLOWSTONE)
                .add(Blocks.SHROOMLIGHT);

        //vanilla
        tag(BlockTags.STONE_BRICKS)
                .add(AetherBlocks.HOLYSTONE_BRICKS.get())
                .addTag(AetherTags.Blocks.DUNGEON_BLOCKS);
        tag(BlockTags.WOODEN_STAIRS)
                .add(AetherBlocks.SKYROOT_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS)
                .add(AetherBlocks.SKYROOT_SLAB.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(AetherBlocks.SKYROOT_FENCE.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(AetherBlocks.SKYROOT_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(AetherBlocks.SKYROOT_TRAPDOOR.get());
        tag(BlockTags.WOODEN_BUTTONS)
                .add(AetherBlocks.SKYROOT_BUTTON.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(AetherBlocks.SKYROOT_PRESSURE_PLATE.get());
        tag(BlockTags.BUTTONS)
                .add(AetherBlocks.SKYROOT_BUTTON.get())
                .add(AetherBlocks.HOLYSTONE_BUTTON.get());
        tag(BlockTags.PRESSURE_PLATES)
                .add(AetherBlocks.SKYROOT_PRESSURE_PLATE.get())
                .add(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get());
        tag(BlockTags.SAPLINGS)
                .add(AetherBlocks.SKYROOT_SAPLING.get())
                .add(AetherBlocks.GOLDEN_OAK_SAPLING.get());
        tag(BlockTags.LOGS_THAT_BURN) //charcoal recipes
                .addTag(AetherTags.Blocks.SKYROOT_LOGS)
                .addTag(AetherTags.Blocks.GOLDEN_OAK_LOGS);
        tag(BlockTags.STAIRS)
                .add(AetherBlocks.SKYROOT_STAIRS.get())
                .add(AetherBlocks.CARVED_STAIRS.get())
                .add(AetherBlocks.ANGELIC_STAIRS.get())
                .add(AetherBlocks.HELLFIRE_STAIRS.get())
                .add(AetherBlocks.HOLYSTONE_STAIRS.get())
                .add(AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get())
                .add(AetherBlocks.HOLYSTONE_BRICK_STAIRS.get())
                .add(AetherBlocks.AEROGEL_STAIRS.get());
        tag(BlockTags.SLABS)
                .add(AetherBlocks.SKYROOT_SLAB.get())
                .add(AetherBlocks.CARVED_SLAB.get())
                .add(AetherBlocks.ANGELIC_SLAB.get())
                .add(AetherBlocks.HELLFIRE_SLAB.get())
                .add(AetherBlocks.HOLYSTONE_SLAB.get())
                .add(AetherBlocks.MOSSY_HOLYSTONE_SLAB.get())
                .add(AetherBlocks.HOLYSTONE_BRICK_SLAB.get())
                .add(AetherBlocks.AEROGEL_SLAB.get());
        tag(BlockTags.WALLS)
                .add(AetherBlocks.CARVED_WALL.get())
                .add(AetherBlocks.ANGELIC_WALL.get())
                .add(AetherBlocks.HELLFIRE_WALL.get())
                .add(AetherBlocks.HOLYSTONE_WALL.get())
                .add(AetherBlocks.MOSSY_HOLYSTONE_WALL.get())
                .add(AetherBlocks.HOLYSTONE_BRICK_WALL.get())
                .add(AetherBlocks.AEROGEL_WALL.get());
        tag(BlockTags.LEAVES)
                .add(AetherBlocks.SKYROOT_LEAVES.get())
                .add(AetherBlocks.GOLDEN_OAK_LEAVES.get())
                .add(AetherBlocks.CRYSTAL_LEAVES.get())
                .add(AetherBlocks.CRYSTAL_FRUIT_LEAVES.get())
                .add(AetherBlocks.HOLIDAY_LEAVES.get())
                .add(AetherBlocks.DECORATED_HOLIDAY_LEAVES.get());
        tag(BlockTags.SMALL_FLOWERS)
                .add(AetherBlocks.PURPLE_FLOWER.get())
                .add(AetherBlocks.WHITE_FLOWER.get());
        tag(BlockTags.BEDS)
                .add(AetherBlocks.SKYROOT_BED.get());
        tag(BlockTags.FLOWER_POTS)
                .add(AetherBlocks.POTTED_PURPLE_FLOWER.get())
                .add(AetherBlocks.POTTED_WHITE_FLOWER.get())
                .add(AetherBlocks.POTTED_SKYROOT_SAPLING.get())
                .add(AetherBlocks.POTTED_GOLDEN_OAK_SAPLING.get());
        tag(BlockTags.ENDERMAN_HOLDABLE)
                .addTag(AetherTags.Blocks.AETHER_DIRT)
                .add(AetherBlocks.QUICKSOIL.get())
                .add(AetherBlocks.PURPLE_FLOWER.get())
                .add(AetherBlocks.WHITE_FLOWER.get());
        tag(BlockTags.VALID_SPAWN)
                .addTag(AetherTags.Blocks.AETHER_DIRT);
        tag(BlockTags.IMPERMEABLE)
                .add(AetherBlocks.QUICKSOIL_GLASS.get());
        tag(BlockTags.BAMBOO_PLANTABLE_ON)
                .addTags(AetherTags.Blocks.AETHER_DIRT);
        tag(BlockTags.DRAGON_IMMUNE)
                .addTag(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)
                .addTag(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS)
                .addTag(AetherTags.Blocks.AEROGEL);
        tag(BlockTags.WITHER_IMMUNE)
                .addTag(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS)
                .addTag(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS);
        tag(BlockTags.BEE_GROWABLES)
                .add(AetherBlocks.BERRY_BUSH_STEM.get());
        tag(BlockTags.PORTALS)
                .add(AetherBlocks.AETHER_PORTAL.get());
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(AetherBlocks.ZANITE_BLOCK.get())
                .add(AetherBlocks.ENCHANTED_GRAVITITE.get());
        tag(BlockTags.WALL_POST_OVERRIDE)
                .add(AetherBlocks.AMBROSIUM_TORCH.get());
        tag(BlockTags.FENCE_GATES)
                .add(AetherBlocks.SKYROOT_FENCE_GATE.get());
        tag(BlockTags.SIGNS)
                .add(AetherBlocks.SKYROOT_SIGN.get())
                .add(AetherBlocks.SKYROOT_WALL_SIGN.get());
        tag(BlockTags.STANDING_SIGNS)
                .add(AetherBlocks.SKYROOT_SIGN.get());
        tag(BlockTags.WALL_SIGNS)
                .add(AetherBlocks.SKYROOT_WALL_SIGN.get());

        //forge
        tag(Tags.Blocks.DIRT)
                .addTag(AetherTags.Blocks.AETHER_DIRT);
        tag(Tags.Blocks.FENCE_GATES_WOODEN)
                .add(AetherBlocks.SKYROOT_FENCE_GATE.get());
        tag(Tags.Blocks.FENCES_WOODEN)
                .add(AetherBlocks.SKYROOT_FENCE.get());
        tag(Tags.Blocks.GLASS_COLORLESS)
                .add(AetherBlocks.QUICKSOIL_GLASS.get());
        tag(Tags.Blocks.ORES)
                .add(AetherBlocks.AMBROSIUM_ORE.get())
                .add(AetherBlocks.ZANITE_ORE.get())
                .add(AetherBlocks.GRAVITITE_ORE.get());
        tag(Tags.Blocks.STONE)
                .addTag(AetherTags.Blocks.HOLYSTONE);
        tag(Tags.Blocks.STORAGE_BLOCKS)
                .add(AetherBlocks.ZANITE_BLOCK.get());
    }

    protected TagsProvider.Builder<Block> tag(ITag.INamedTag<Block> tag) {
        return super.tag(tag);
    }
}
