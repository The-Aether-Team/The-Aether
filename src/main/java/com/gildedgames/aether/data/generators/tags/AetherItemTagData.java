package com.gildedgames.aether.data.generators.tags;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.AetherTags;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
        copy(AetherTags.Blocks.BOSS_DOORWAY_DUNGEON_BLOCKS, AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS);
        copy(AetherTags.Blocks.TREASURE_DOORWAY_DUNGEON_BLOCKS, AetherTags.Items.TREASURE_DOORWAY_DUNGEON_BLOCKS);
        copy(AetherTags.Blocks.SENTRY_BLOCKS, AetherTags.Items.SENTRY_BLOCKS);
        copy(AetherTags.Blocks.ANGELIC_BLOCKS, AetherTags.Items.ANGELIC_BLOCKS);
        copy(AetherTags.Blocks.HELLFIRE_BLOCKS, AetherTags.Items.HELLFIRE_BLOCKS);

        tag(AetherTags.Items.PLANKS_CRAFTING).addTag(ItemTags.PLANKS).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        tag(AetherTags.Items.STONE_CRAFTING).addTag(ItemTags.STONE_CRAFTING_MATERIALS).add(AetherBlocks.HOLYSTONE.get().asItem());

        tag(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS);
        tag(AetherTags.Items.BOOK_OF_LORE_MATERIALS).addTag(Tags.Items.DUSTS_GLOWSTONE).add(
                Items.FLINT,
                AetherItems.AMBROSIUM_SHARD.get());
        tag(AetherTags.Items.SKYROOT_STICKS).add(AetherItems.SKYROOT_STICK.get());
        tag(AetherTags.Items.EFFECTIVE_IN_AETHER);
        tag(AetherTags.Items.SKYROOT_TOOLS).add(
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.SKYROOT_AXE.get(),
                AetherItems.SKYROOT_SHOVEL.get(),
                AetherItems.SKYROOT_HOE.get());
        tag(AetherTags.Items.SKYROOT_WEAPONS).add(AetherItems.SKYROOT_SWORD.get());
        tag(AetherTags.Items.HOLYSTONE_TOOLS).add(
                AetherItems.HOLYSTONE_PICKAXE.get(),
                AetherItems.HOLYSTONE_AXE.get(),
                AetherItems.HOLYSTONE_SHOVEL.get(),
                AetherItems.HOLYSTONE_HOE.get());
        tag(AetherTags.Items.HOLYSTONE_WEAPONS).add(AetherItems.HOLYSTONE_SWORD.get());
        tag(AetherTags.Items.ZANITE_TOOLS).add(
                AetherItems.ZANITE_PICKAXE.get(),
                AetherItems.ZANITE_AXE.get(),
                AetherItems.ZANITE_SHOVEL.get(),
                AetherItems.ZANITE_HOE.get());
        tag(AetherTags.Items.ZANITE_WEAPONS).add(AetherItems.ZANITE_SWORD.get());
        tag(AetherTags.Items.GRAVITITE_TOOLS).add(
                AetherItems.GRAVITITE_PICKAXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.GRAVITITE_SHOVEL.get(),
                AetherItems.GRAVITITE_HOE.get());
        tag(AetherTags.Items.GRAVITITE_WEAPONS).add(AetherItems.GRAVITITE_SWORD.get());
        tag(AetherTags.Items.VALKYRIE_TOOLS).add(
                AetherItems.VALKYRIE_PICKAXE.get(),
                AetherItems.VALKYRIE_AXE.get(),
                AetherItems.VALKYRIE_SHOVEL.get(),
                AetherItems.VALKYRIE_HOE.get());
        tag(AetherTags.Items.VALKYRIE_WEAPONS).add(AetherItems.VALKYRIE_LANCE.get());
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
        tag(AetherTags.Items.DEPLOYABLE_PARACHUTES).add(
                AetherItems.COLD_PARACHUTE.get(),
                AetherItems.GOLDEN_PARACHUTE.get());
        tag(AetherTags.Items.DUNGEON_KEYS).add(
                AetherItems.BRONZE_DUNGEON_KEY.get(),
                AetherItems.SILVER_DUNGEON_KEY.get(),
                AetherItems.GOLD_DUNGEON_KEY.get());
        tag(AetherTags.Items.ACCEPTED_MUSIC_DISCS).add(
                Items.MUSIC_DISC_11,
                Items.MUSIC_DISC_13,
                Items.MUSIC_DISC_BLOCKS,
                Items.MUSIC_DISC_CHIRP,
                Items.MUSIC_DISC_FAR,
                Items.MUSIC_DISC_MALL,
                Items.MUSIC_DISC_MELLOHI,
                Items.MUSIC_DISC_STAL,
                Items.MUSIC_DISC_WAIT,
                Items.MUSIC_DISC_WARD,
                Items.MUSIC_DISC_OTHERSIDE);
        tag(AetherTags.Items.SAVE_NBT_IN_RECIPE).add(
                AetherItems.ENCHANTED_DART_SHOOTER.get(),
                AetherItems.ICE_RING.get(),
                AetherItems.ICE_PENDANT.get());
        tag(AetherTags.Items.MOA_EGGS).add(
                AetherItems.BLUE_MOA_EGG.get(),
                AetherItems.WHITE_MOA_EGG.get(),
                AetherItems.BLACK_MOA_EGG.get(),
                AetherItems.ORANGE_MOA_EGG.get());
        tag(AetherTags.Items.FREEZABLE_BUCKETS)
                .add(Items.WATER_BUCKET)
                .add(AetherItems.SKYROOT_WATER_BUCKET.get());
        tag(AetherTags.Items.FREEZABLE_RINGS)
                .add(AetherItems.IRON_RING.get())
                .add(AetherItems.GOLDEN_RING.get());
        tag(AetherTags.Items.FREEZABLE_PENDANTS)
                .add(AetherItems.IRON_PENDANT.get())
                .add(AetherItems.GOLDEN_PENDANT.get());
        tag(AetherTags.Items.SLIDER_DAMAGING_ITEMS).addTag(Tags.Items.TOOLS_PICKAXES);

        tag(AetherTags.Items.PHYG_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        tag(AetherTags.Items.FLYING_COW_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        tag(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        tag(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        tag(AetherTags.Items.MOA_TEMPTATION_ITEMS).add(AetherItems.NATURE_STAFF.get());
        tag(AetherTags.Items.MOA_FOOD_ITEMS).add(AetherItems.AECHOR_PETAL.get());

        tag(AetherTags.Items.TOOLS_LANCES).add(AetherItems.VALKYRIE_LANCE.get());
        tag(AetherTags.Items.TOOLS_HAMMERS).add(AetherItems.HAMMER_OF_NOTCH.get());

        tag(AetherTags.Items.AETHER_RING).add(
                AetherItems.IRON_RING.get(),
                AetherItems.GOLDEN_RING.get(),
                AetherItems.ZANITE_RING.get(),
                AetherItems.ICE_RING.get());
        tag(AetherTags.Items.AETHER_PENDANT).add(
                AetherItems.IRON_PENDANT.get(),
                AetherItems.GOLDEN_PENDANT.get(),
                AetherItems.ZANITE_PENDANT.get(),
                AetherItems.ICE_PENDANT.get());
        tag(AetherTags.Items.AETHER_GLOVES).add(
                AetherItems.LEATHER_GLOVES.get(),
                AetherItems.CHAINMAIL_GLOVES.get(),
                AetherItems.IRON_GLOVES.get(),
                AetherItems.GOLDEN_GLOVES.get(),
                AetherItems.DIAMOND_GLOVES.get(),
                AetherItems.NETHERITE_GLOVES.get(),
                AetherItems.ZANITE_GLOVES.get(),
                AetherItems.GRAVITITE_GLOVES.get(),
                AetherItems.NEPTUNE_GLOVES.get(),
                AetherItems.PHOENIX_GLOVES.get(),
                AetherItems.OBSIDIAN_GLOVES.get(),
                AetherItems.VALKYRIE_GLOVES.get());
        tag(AetherTags.Items.AETHER_CAPE).add(
                AetherItems.RED_CAPE.get(),
                AetherItems.BLUE_CAPE.get(),
                AetherItems.YELLOW_CAPE.get(),
                AetherItems.WHITE_CAPE.get(),
                AetherItems.SWET_CAPE.get(),
                AetherItems.INVISIBILITY_CLOAK.get(),
                AetherItems.AGILITY_CAPE.get(),
                AetherItems.VALKYRIE_CAPE.get());
        tag(AetherTags.Items.AETHER_ACCESSORY).add(
                AetherItems.GOLDEN_FEATHER.get(),
                AetherItems.REGENERATION_STONE.get(),
                AetherItems.IRON_BUBBLE.get());
        tag(AetherTags.Items.AETHER_SHIELD).add(AetherItems.SHIELD_OF_REPULSION.get());

        tag(AetherTags.Items.ACCESSORIES).addTags(
                AetherTags.Items.AETHER_RING,
                AetherTags.Items.AETHER_PENDANT,
                AetherTags.Items.AETHER_GLOVES,
                AetherTags.Items.AETHER_CAPE,
                AetherTags.Items.AETHER_ACCESSORY,
                AetherTags.Items.AETHER_SHIELD);

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
        tag(ItemTags.BOATS).add(AetherItems.SKYROOT_BOAT.get());
        tag(ItemTags.CHEST_BOATS).add(AetherItems.SKYROOT_CHEST_BOAT.get());

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
        tag(Tags.Items.GLASS_PANES_COLORLESS).add(AetherBlocks.QUICKSOIL_GLASS_PANE.get().asItem());
        tag(Tags.Items.ORE_RATES_SINGULAR).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        tag(Tags.Items.ORES).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        tag(Tags.Items.RODS_WOODEN).add(AetherItems.SKYROOT_STICK.get());
        tag(Tags.Items.STORAGE_BLOCKS).add(
                AetherBlocks.AMBROSIUM_BLOCK.get().asItem(),
                AetherBlocks.ZANITE_BLOCK.get().asItem());
        tag(Tags.Items.TOOLS).addTag(AetherTags.Items.TOOLS_HAMMERS);
        tag(Tags.Items.TOOLS_SWORDS).add(
                AetherItems.SKYROOT_SWORD.get(),
                AetherItems.HOLYSTONE_SWORD.get(),
                AetherItems.ZANITE_SWORD.get(),
                AetherItems.GRAVITITE_SWORD.get(),
                AetherItems.FLAMING_SWORD.get(),
                AetherItems.LIGHTNING_SWORD.get(),
                AetherItems.HOLY_SWORD.get(),
                AetherItems.VAMPIRE_BLADE.get(),
                AetherItems.PIG_SLAYER.get(),
                AetherItems.CANDY_CANE_SWORD.get()).addTag(AetherTags.Items.TOOLS_LANCES);
        tag(Tags.Items.TOOLS_AXES).add(
                AetherItems.SKYROOT_AXE.get(),
                AetherItems.HOLYSTONE_AXE.get(),
                AetherItems.ZANITE_AXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.VALKYRIE_AXE.get());
        tag(Tags.Items.TOOLS_PICKAXES).add(
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.HOLYSTONE_PICKAXE.get(),
                AetherItems.ZANITE_PICKAXE.get(),
                AetherItems.GRAVITITE_PICKAXE.get(),
                AetherItems.VALKYRIE_PICKAXE.get());
        tag(Tags.Items.TOOLS_SHOVELS).add(
                AetherItems.SKYROOT_SHOVEL.get(),
                AetherItems.HOLYSTONE_SHOVEL.get(),
                AetherItems.ZANITE_SHOVEL.get(),
                AetherItems.GRAVITITE_SHOVEL.get(),
                AetherItems.VALKYRIE_SHOVEL.get());
        tag(Tags.Items.TOOLS_HOES).add(
                AetherItems.SKYROOT_HOE.get(),
                AetherItems.HOLYSTONE_HOE.get(),
                AetherItems.ZANITE_HOE.get(),
                AetherItems.GRAVITITE_HOE.get(),
                AetherItems.VALKYRIE_HOE.get());
        tag(Tags.Items.TOOLS_BOWS).add(AetherItems.PHOENIX_BOW.get());
        tag(Tags.Items.ARMORS_HELMETS).add(
                AetherItems.ZANITE_HELMET.get(),
                AetherItems.GRAVITITE_HELMET.get(),
                AetherItems.NEPTUNE_HELMET.get(),
                AetherItems.PHOENIX_HELMET.get(),
                AetherItems.OBSIDIAN_HELMET.get(),
                AetherItems.VALKYRIE_HELMET.get());
        tag(Tags.Items.ARMORS_CHESTPLATES).add(
                AetherItems.ZANITE_CHESTPLATE.get(),
                AetherItems.GRAVITITE_CHESTPLATE.get(),
                AetherItems.NEPTUNE_CHESTPLATE.get(),
                AetherItems.PHOENIX_CHESTPLATE.get(),
                AetherItems.OBSIDIAN_CHESTPLATE.get(),
                AetherItems.VALKYRIE_CHESTPLATE.get());
        tag(Tags.Items.ARMORS_LEGGINGS).add(
                AetherItems.ZANITE_LEGGINGS.get(),
                AetherItems.GRAVITITE_LEGGINGS.get(),
                AetherItems.NEPTUNE_LEGGINGS.get(),
                AetherItems.PHOENIX_LEGGINGS.get(),
                AetherItems.OBSIDIAN_LEGGINGS.get(),
                AetherItems.VALKYRIE_LEGGINGS.get());
        tag(Tags.Items.ARMORS_BOOTS).add(
                AetherItems.ZANITE_BOOTS.get(),
                AetherItems.GRAVITITE_BOOTS.get(),
                AetherItems.NEPTUNE_BOOTS.get(),
                AetherItems.PHOENIX_BOOTS.get(),
                AetherItems.OBSIDIAN_BOOTS.get(),
                AetherItems.VALKYRIE_BOOTS.get(),
                AetherItems.SENTRY_BOOTS.get());
    }

    @Nonnull
    protected TagsProvider.TagAppender<Item> tag(@Nonnull TagKey<Item> tag) {
        return super.tag(tag);
    }
}
