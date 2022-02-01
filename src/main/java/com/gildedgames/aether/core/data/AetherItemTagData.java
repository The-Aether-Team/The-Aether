package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AetherItemTagData extends ItemTagsProvider
{
    public AetherItemTagData(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Aether.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Aether Item Tags";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        //aether
        copy(AetherTags.Blocks.AETHER_DIRT, AetherTags.Items.AETHER_DIRT);
        copy(AetherTags.Blocks.HOLYSTONE, AetherTags.Items.HOLYSTONE);
        copy(AetherTags.Blocks.AERCLOUDS, AetherTags.Items.AERCLOUDS);
        copy(AetherTags.Blocks.SKYROOT_LOGS, AetherTags.Items.SKYROOT_LOGS);
        copy(AetherTags.Blocks.GOLDEN_OAK_LOGS, AetherTags.Items.GOLDEN_OAK_LOGS);
        copy(AetherTags.Blocks.AEROGEL, AetherTags.Items.AEROGEL);
        copy(AetherTags.Blocks.DUNGEON_BLOCKS, AetherTags.Items.DUNGEON_BLOCKS);
        copy(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS, AetherTags.Items.LOCKED_DUNGEON_BLOCKS);
        copy(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS, AetherTags.Items.TRAPPED_DUNGEON_BLOCKS);

        tag(AetherTags.Items.PLANKS_CRAFTING).addTag(ItemTags.PLANKS).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        tag(AetherTags.Items.STONE_CRAFTING).addTag(ItemTags.STONE_CRAFTING_MATERIALS).add(AetherBlocks.HOLYSTONE.get().asItem());
        tag(AetherTags.Items.BANNED_IN_AETHER).add(
                Items.FLINT_AND_STEEL,
                Items.TORCH,
                Items.SOUL_TORCH,
                Items.FIRE_CHARGE);
        tag(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS);
        tag(AetherTags.Items.BOOK_OF_LORE_MATERIALS).addTag(Tags.Items.DUSTS_GLOWSTONE).add(
                Items.FLINT,
                AetherItems.AMBROSIUM_SHARD.get());
        tag(AetherTags.Items.SKYROOT_STICKS).add(AetherItems.SKYROOT_STICK.get());
        tag(AetherTags.Items.SKYROOT_TOOLS).add(
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.SKYROOT_AXE.get(),
                AetherItems.SKYROOT_SHOVEL.get(),
                AetherItems.SKYROOT_HOE.get());
        tag(AetherTags.Items.HOLYSTONE_TOOLS).add(
                AetherItems.HOLYSTONE_PICKAXE.get(),
                AetherItems.HOLYSTONE_AXE.get(),
                AetherItems.HOLYSTONE_SHOVEL.get(),
                AetherItems.HOLYSTONE_HOE.get());
        tag(AetherTags.Items.ZANITE_TOOLS).add(
                AetherItems.ZANITE_PICKAXE.get(),
                AetherItems.ZANITE_AXE.get(),
                AetherItems.ZANITE_SHOVEL.get(),
                AetherItems.ZANITE_HOE.get());
        tag(AetherTags.Items.GRAVITITE_TOOLS).add(
                AetherItems.GRAVITITE_PICKAXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.GRAVITITE_SHOVEL.get(),
                AetherItems.GRAVITITE_HOE.get());
        tag(AetherTags.Items.VALKYRIE_TOOLS).add(
                AetherItems.VALKYRIE_PICKAXE.get(),
                AetherItems.VALKYRIE_AXE.get(),
                AetherItems.VALKYRIE_SHOVEL.get(),
                AetherItems.VALKYRIE_HOE.get());
        tag(AetherTags.Items.GOLDEN_AMBER_HARVESTERS).add(
                AetherItems.ZANITE_AXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.VALKYRIE_AXE.get());
        tag(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS).addTag(AetherTags.Items.DUNGEON_KEYS).add(
                AetherItems.VICTORY_MEDAL.get(),
                Items.PLAYER_HEAD,
                Items.SKELETON_SKULL,
                Items.CREEPER_HEAD,
                Items.ZOMBIE_HEAD,
                Items.WITHER_SKELETON_SKULL,
                Items.DRAGON_HEAD,
                Items.NETHER_STAR);
        tag(AetherTags.Items.PIG_DROPS).add(Items.PORKCHOP);
        tag(AetherTags.Items.DARTS).add(
                AetherItems.GOLDEN_DART.get(),
                AetherItems.POISON_DART.get(),
                AetherItems.ENCHANTED_DART.get());
        tag(AetherTags.Items.DART_SHOOTERS).add(
                AetherItems.GOLDEN_DART_SHOOTER.get(),
                AetherItems.POISON_DART_SHOOTER.get(),
                AetherItems.ENCHANTED_DART_SHOOTER.get());
        tag(AetherTags.Items.DUNGEON_KEYS).add(
                AetherItems.BRONZE_DUNGEON_KEY.get(),
                AetherItems.SILVER_DUNGEON_KEY.get(),
                AetherItems.GOLD_DUNGEON_KEY.get());
        tag(AetherTags.Items.ACCEPTED_MUSIC_DISCS).add(
                Items.MUSIC_DISC_11,
                Items.MUSIC_DISC_13,
                Items.MUSIC_DISC_BLOCKS,
                Items.MUSIC_DISC_FAR,
                Items.MUSIC_DISC_MALL,
                Items.MUSIC_DISC_MELLOHI,
                Items.MUSIC_DISC_STAL,
                Items.MUSIC_DISC_STRAD,
                Items.MUSIC_DISC_WAIT,
                Items.MUSIC_DISC_WARD);
        tag(AetherTags.Items.SAVE_NBT_IN_RECIPE).add(
                AetherItems.ENCHANTED_DART_SHOOTER.get(),
                AetherItems.ICE_RING.get(),
                AetherItems.ICE_PENDANT.get());
        tag(AetherTags.Items.MOA_EGGS).add(
                AetherItems.BLUE_MOA_EGG.get(),
                AetherItems.WHITE_MOA_EGG.get(),
                AetherItems.BLACK_MOA_EGG.get(),
                AetherItems.ORANGE_MOA_EGG.get());

        //vanilla
        tag(ItemTags.STONE_BRICKS).add(AetherBlocks.HOLYSTONE_BRICKS.get().asItem()).addTag(AetherTags.Items.DUNGEON_BLOCKS);
        tag(ItemTags.WOODEN_STAIRS).add(AetherBlocks.SKYROOT_STAIRS.get().asItem());
        tag(ItemTags.WOODEN_SLABS).add(AetherBlocks.SKYROOT_SLAB.get().asItem());
        tag(ItemTags.WOODEN_FENCES).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        tag(ItemTags.WOODEN_DOORS).add(AetherBlocks.SKYROOT_DOOR.get().asItem());
        tag(ItemTags.WOODEN_TRAPDOORS).add(AetherBlocks.SKYROOT_TRAPDOOR.get().asItem());
        tag(ItemTags.SAPLINGS).add(
                AetherBlocks.SKYROOT_SAPLING.get().asItem(),
                AetherBlocks.GOLDEN_OAK_SAPLING.get().asItem());
        tag(ItemTags.LOGS_THAT_BURN).addTags( //charcoal recipes
                AetherTags.Items.SKYROOT_LOGS,
                AetherTags.Items.GOLDEN_OAK_LOGS);
        tag(ItemTags.SAND).add(AetherBlocks.QUICKSOIL.get().asItem());
        tag(ItemTags.STAIRS).add(
                AetherBlocks.SKYROOT_STAIRS.get().asItem(),
                AetherBlocks.CARVED_STAIRS.get().asItem(),
                AetherBlocks.ANGELIC_STAIRS.get().asItem(),
                AetherBlocks.HELLFIRE_STAIRS.get().asItem(),
                AetherBlocks.HOLYSTONE_STAIRS.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get().asItem(),
                AetherBlocks.ICESTONE_STAIRS.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_STAIRS.get().asItem(),
                AetherBlocks.AEROGEL_STAIRS.get().asItem());
        tag(ItemTags.SLABS).add(
                AetherBlocks.SKYROOT_SLAB.get().asItem(),
                AetherBlocks.CARVED_SLAB.get().asItem(),
                AetherBlocks.ANGELIC_SLAB.get().asItem(),
                AetherBlocks.HELLFIRE_SLAB.get().asItem(),
                AetherBlocks.HOLYSTONE_SLAB.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_SLAB.get().asItem(),
                AetherBlocks.ICESTONE_SLAB.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_SLAB.get().asItem(),
                AetherBlocks.AEROGEL_SLAB.get().asItem());
        tag(ItemTags.WALLS).add(
                AetherBlocks.CARVED_WALL.get().asItem(),
                AetherBlocks.ANGELIC_WALL.get().asItem(),
                AetherBlocks.HELLFIRE_WALL.get().asItem(),
                AetherBlocks.HOLYSTONE_WALL.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_WALL.get().asItem(),
                AetherBlocks.ICESTONE_WALL.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_WALL.get().asItem(),
                AetherBlocks.AEROGEL_WALL.get().asItem());
        tag(ItemTags.LEAVES).add(
                AetherBlocks.SKYROOT_LEAVES.get().asItem(),
                AetherBlocks.GOLDEN_OAK_LEAVES.get().asItem(),
                AetherBlocks.CRYSTAL_LEAVES.get().asItem(),
                AetherBlocks.CRYSTAL_FRUIT_LEAVES.get().asItem(),
                AetherBlocks.HOLIDAY_LEAVES.get().asItem(),
                AetherBlocks.DECORATED_HOLIDAY_LEAVES.get().asItem());
        tag(ItemTags.SMALL_FLOWERS).add(
                AetherBlocks.PURPLE_FLOWER.get().asItem(),
                AetherBlocks.WHITE_FLOWER.get().asItem());
        tag(ItemTags.BEDS).add(AetherBlocks.SKYROOT_BED.get().asItem());
        tag(ItemTags.PIGLIN_LOVED).add(
                AetherItems.GOLDEN_RING.get(),
                AetherItems.GOLDEN_PENDANT.get(),
                AetherItems.GOLDEN_GLOVES.get());
        tag(ItemTags.FOX_FOOD).add(
                AetherItems.BLUE_BERRY.get(),
                AetherItems.ENCHANTED_BERRY.get());
        tag(ItemTags.SIGNS).add(AetherBlocks.SKYROOT_SIGN.get().asItem());
        tag(ItemTags.MUSIC_DISCS).add(
                AetherItems.MUSIC_DISC_AETHER_TUNE.get(),
                AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(),
                AetherItems.MUSIC_DISC_WELCOMING_SKIES.get(),
                AetherItems.MUSIC_DISC_LEGACY.get());
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
                AetherItems.ZANITE_GEMSTONE.get(),
                AetherBlocks.ENCHANTED_GRAVITITE.get().asItem());

        //forge
        tag(Tags.Items.BOOKSHELVES).add(AetherBlocks.SKYROOT_BOOKSHELF.get().asItem());
        tag(Tags.Items.FENCE_GATES_WOODEN).add(AetherBlocks.SKYROOT_FENCE_GATE.get().asItem());
        tag(Tags.Items.FENCES_WOODEN).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        tag(Tags.Items.FENCE_GATES).add(AetherBlocks.SKYROOT_FENCE_GATE.get().asItem());
        tag(Tags.Items.FENCES).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        tag(Tags.Items.EGGS).add(
                AetherItems.BLUE_MOA_EGG.get(),
                AetherItems.WHITE_MOA_EGG.get(),
                AetherItems.BLACK_MOA_EGG.get(),
                AetherItems.ORANGE_MOA_EGG.get());
        tag(Tags.Items.GEMS).add(AetherItems.ZANITE_GEMSTONE.get());
        tag(Tags.Items.GLASS_COLORLESS).add(AetherBlocks.QUICKSOIL_GLASS.get().asItem());
        tag(Tags.Items.ORE_RATES_SINGULAR).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        tag(Tags.Items.ORES).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        tag(Tags.Items.RODS_WOODEN).add(AetherItems.SKYROOT_STICK.get());
        tag(Tags.Items.SAND_COLORLESS).add(AetherBlocks.QUICKSOIL.get().asItem());
        tag(Tags.Items.STORAGE_BLOCKS).add(AetherBlocks.ZANITE_BLOCK.get().asItem());
    }

    @Nonnull
    protected TagsProvider.TagAppender<Item> tag(@Nonnull Tag.Named<Item> tag) {
        return super.tag(tag);
    }
}
