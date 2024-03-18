package com.aetherteam.aether.data.generators.tags;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.Nullable;
import java.util.concurrent.CompletableFuture;

public class AetherItemTagData extends ItemTagsProvider {
    public AetherItemTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, Aether.MODID, helper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags(HolderLookup.Provider provider) {
        // Aether
        this.copy(AetherTags.Blocks.AETHER_DIRT, AetherTags.Items.AETHER_DIRT);
        this.copy(AetherTags.Blocks.HOLYSTONE, AetherTags.Items.HOLYSTONE);
        this.copy(AetherTags.Blocks.AERCLOUDS, AetherTags.Items.AERCLOUDS);
        this.copy(AetherTags.Blocks.SKYROOT_LOGS, AetherTags.Items.SKYROOT_LOGS);
        this.copy(AetherTags.Blocks.GOLDEN_OAK_LOGS, AetherTags.Items.GOLDEN_OAK_LOGS);
        this.copy(AetherTags.Blocks.AEROGEL, AetherTags.Items.AEROGEL);
        this.copy(AetherTags.Blocks.DUNGEON_BLOCKS, AetherTags.Items.DUNGEON_BLOCKS);
        this.copy(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS, AetherTags.Items.LOCKED_DUNGEON_BLOCKS);
        this.copy(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS, AetherTags.Items.TRAPPED_DUNGEON_BLOCKS);
        this.copy(AetherTags.Blocks.BOSS_DOORWAY_DUNGEON_BLOCKS, AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS);
        this.copy(AetherTags.Blocks.TREASURE_DOORWAY_DUNGEON_BLOCKS, AetherTags.Items.TREASURE_DOORWAY_DUNGEON_BLOCKS);
        this.copy(AetherTags.Blocks.SENTRY_BLOCKS, AetherTags.Items.SENTRY_BLOCKS);
        this.copy(AetherTags.Blocks.ANGELIC_BLOCKS, AetherTags.Items.ANGELIC_BLOCKS);
        this.copy(AetherTags.Blocks.HELLFIRE_BLOCKS, AetherTags.Items.HELLFIRE_BLOCKS);

        this.tag(AetherTags.Items.CRAFTS_SKYROOT_PLANKS).addTags(
                AetherTags.Items.SKYROOT_LOGS,
                AetherTags.Items.GOLDEN_OAK_LOGS);
        this.tag(AetherTags.Items.PLANKS_CRAFTING).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        this.tag(AetherTags.Items.SKYROOT_STICK_CRAFTING).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        this.tag(AetherTags.Items.SKYROOT_TOOL_CRAFTING).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        this.tag(AetherTags.Items.MILK_BUCKET_CRAFTING).add(
                AetherItems.SKYROOT_MILK_BUCKET.get(),
                Items.MILK_BUCKET);
        this.tag(AetherTags.Items.WATER_BUCKET_CRAFTING).add(
                AetherItems.SKYROOT_WATER_BUCKET.get(),
                Items.WATER_BUCKET);

        this.tag(AetherTags.Items.AETHER_PORTAL_ACTIVATION_ITEMS);
        this.tag(AetherTags.Items.BOOK_OF_LORE_MATERIALS).addTag(Tags.Items.DUSTS_GLOWSTONE).add(
                Items.FLINT,
                AetherItems.AMBROSIUM_SHARD.get());
        this.tag(AetherTags.Items.SKYROOT_STICKS).add(AetherItems.SKYROOT_STICK.get());
        this.tag(AetherTags.Items.SWET_BALLS).add(AetherItems.SWET_BALL.get());
        this.tag(AetherTags.Items.GOLDEN_AMBER_HARVESTERS).add(
                AetherItems.ZANITE_AXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.VALKYRIE_AXE.get());
        this.tag(AetherTags.Items.TREATED_AS_AETHER_ITEM);
        this.tag(AetherTags.Items.NO_SKYROOT_DOUBLE_DROPS).addTag(AetherTags.Items.DUNGEON_KEYS).add(
                AetherItems.VICTORY_MEDAL.get(),
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.IRON_RING.get(),
                AetherItems.GOLDEN_AMBER.get(),
                AetherItems.ZANITE_GEMSTONE.get(),
                AetherItems.HOLYSTONE_PICKAXE.get(),
                Items.PLAYER_HEAD,
                Items.SKELETON_SKULL,
                Items.CREEPER_HEAD,
                Items.ZOMBIE_HEAD,
                Items.WITHER_SKELETON_SKULL,
                Items.DRAGON_HEAD,
                Items.NETHER_STAR);
        this.tag(AetherTags.Items.PIG_DROPS).add(
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP);
        this.tag(AetherTags.Items.DARTS).add(
                AetherItems.GOLDEN_DART.get(),
                AetherItems.POISON_DART.get(),
                AetherItems.ENCHANTED_DART.get());
        this.tag(AetherTags.Items.DART_SHOOTERS).add(
                AetherItems.GOLDEN_DART_SHOOTER.get(),
                AetherItems.POISON_DART_SHOOTER.get(),
                AetherItems.ENCHANTED_DART_SHOOTER.get());
        this.tag(AetherTags.Items.DEPLOYABLE_PARACHUTES).add(
                AetherItems.COLD_PARACHUTE.get(),
                AetherItems.GOLDEN_PARACHUTE.get());
        this.tag(AetherTags.Items.DUNGEON_KEYS).add(
                AetherItems.BRONZE_DUNGEON_KEY.get(),
                AetherItems.SILVER_DUNGEON_KEY.get(),
                AetherItems.GOLD_DUNGEON_KEY.get());
        this.tag(AetherTags.Items.ACCEPTED_MUSIC_DISCS).add(
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
        this.tag(AetherTags.Items.SAVE_NBT_IN_RECIPE).add(
                AetherItems.ENCHANTED_DART_SHOOTER.get(),
                AetherItems.ICE_RING.get(),
                AetherItems.ICE_PENDANT.get());
        this.tag(AetherTags.Items.MOA_EGGS).add(
                AetherItems.BLUE_MOA_EGG.get(),
                AetherItems.WHITE_MOA_EGG.get(),
                AetherItems.BLACK_MOA_EGG.get());
        this.tag(AetherTags.Items.FREEZABLE_BUCKETS)
                .add(Items.WATER_BUCKET)
                .add(AetherItems.SKYROOT_WATER_BUCKET.get());
        this.tag(AetherTags.Items.FREEZABLE_RINGS)
                .add(AetherItems.IRON_RING.get())
                .add(AetherItems.GOLDEN_RING.get());
        this.tag(AetherTags.Items.FREEZABLE_PENDANTS)
                .add(AetherItems.IRON_PENDANT.get())
                .add(AetherItems.GOLDEN_PENDANT.get());
        this.tag(AetherTags.Items.SLIDER_DAMAGING_ITEMS).addTag(ItemTags.PICKAXES);
        this.tag(AetherTags.Items.BRONZE_DUNGEON_LOOT).add(
                AetherItems.VALKYRIE_LANCE.get(),
                AetherItems.FLAMING_SWORD.get(),
                AetherItems.HAMMER_OF_KINGBDOGZ.get(),
                AetherItems.NEPTUNE_HELMET.get(),
                AetherItems.NEPTUNE_CHESTPLATE.get(),
                AetherItems.NEPTUNE_LEGGINGS.get(),
                AetherItems.NEPTUNE_BOOTS.get(),
                AetherItems.NEPTUNE_GLOVES.get(),
                AetherItems.SENTRY_BOOTS.get(),
                AetherItems.AGILITY_CAPE.get(),
                AetherItems.SWET_CAPE.get(),
                AetherItems.SHIELD_OF_REPULSION.get(),
                AetherItems.MUSIC_DISC_AETHER_TUNE.get(),
                AetherItems.CLOUD_STAFF.get(),
                AetherItems.LIGHTNING_KNIFE.get(),
                AetherItems.PHOENIX_BOW.get(),
                AetherItems.BLUE_GUMMY_SWET.get(),
                AetherItems.GOLDEN_GUMMY_SWET.get());
        this.tag(AetherTags.Items.SILVER_DUNGEON_LOOT).add(
                AetherItems.LIGHTNING_SWORD.get(),
                AetherItems.HOLY_SWORD.get(),
                AetherItems.VALKYRIE_HELMET.get(),
                AetherItems.VALKYRIE_CHESTPLATE.get(),
                AetherItems.VALKYRIE_LEGGINGS.get(),
                AetherItems.VALKYRIE_BOOTS.get(),
                AetherItems.VALKYRIE_GLOVES.get(),
                AetherItems.INVISIBILITY_CLOAK.get(),
                AetherItems.VALKYRIE_CAPE.get(),
                AetherItems.GOLDEN_FEATHER.get(),
                AetherItems.REGENERATION_STONE.get(),
                AetherItems.MUSIC_DISC_AETHER_TUNE.get(),
                AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(),
                AetherItems.BLUE_GUMMY_SWET.get(),
                AetherItems.GOLDEN_GUMMY_SWET.get(),
                AetherItems.VALKYRIE_PICKAXE.get(),
                AetherItems.VALKYRIE_AXE.get(),
                AetherItems.VALKYRIE_SHOVEL.get(),
                AetherItems.VALKYRIE_HOE.get());
        this.tag(AetherTags.Items.GOLD_DUNGEON_LOOT).add(
                AetherItems.VAMPIRE_BLADE.get(),
                AetherItems.PIG_SLAYER.get(),
                AetherItems.PHOENIX_HELMET.get(),
                AetherItems.PHOENIX_CHESTPLATE.get(),
                AetherItems.PHOENIX_LEGGINGS.get(),
                AetherItems.PHOENIX_BOOTS.get(),
                AetherItems.PHOENIX_GLOVES.get(),
                AetherItems.IRON_BUBBLE.get(),
                AetherItems.LIFE_SHARD.get());

        this.tag(AetherTags.Items.PHYG_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        this.tag(AetherTags.Items.FLYING_COW_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        this.tag(AetherTags.Items.SHEEPUFF_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        this.tag(AetherTags.Items.AERBUNNY_TEMPTATION_ITEMS).add(AetherItems.BLUE_BERRY.get());
        this.tag(AetherTags.Items.MOA_TEMPTATION_ITEMS).add(AetherItems.NATURE_STAFF.get());
        this.tag(AetherTags.Items.MOA_FOOD_ITEMS).add(AetherItems.AECHOR_PETAL.get());

        this.tag(AetherTags.Items.SKYROOT_REPAIRING).add(AetherBlocks.SKYROOT_PLANKS.get().asItem());
        this.tag(AetherTags.Items.HOLYSTONE_REPAIRING).add(AetherBlocks.HOLYSTONE.get().asItem());
        this.tag(AetherTags.Items.ZANITE_REPAIRING).add(AetherItems.ZANITE_GEMSTONE.get());
        this.tag(AetherTags.Items.GRAVITITE_REPAIRING).add(AetherBlocks.ENCHANTED_GRAVITITE.get().asItem());
        this.tag(AetherTags.Items.VALKYRIE_REPAIRING);
        this.tag(AetherTags.Items.FLAMING_REPAIRING);
        this.tag(AetherTags.Items.LIGHTNING_REPAIRING);
        this.tag(AetherTags.Items.HOLY_REPAIRING);
        this.tag(AetherTags.Items.VAMPIRE_REPAIRING);
        this.tag(AetherTags.Items.PIG_SLAYER_REPAIRING);
        this.tag(AetherTags.Items.HAMMER_OF_KINGBDOGZ_REPAIRING);
        this.tag(AetherTags.Items.CANDY_CANE_REPAIRING).add(AetherItems.CANDY_CANE.get());
        this.tag(AetherTags.Items.NEPTUNE_REPAIRING);
        this.tag(AetherTags.Items.PHOENIX_REPAIRING);
        this.tag(AetherTags.Items.OBSIDIAN_REPAIRING);
        this.tag(AetherTags.Items.SENTRY_REPAIRING);
        this.tag(AetherTags.Items.ICE_REPAIRING);

        this.tag(AetherTags.Items.GEMS_ZANITE).add(AetherItems.ZANITE_GEMSTONE.get());
        this.tag(AetherTags.Items.PROCESSED_GRAVITITE).add(AetherBlocks.ENCHANTED_GRAVITITE.get().asItem());

        this.tag(AetherTags.Items.TOOLS_LANCES).add(AetherItems.VALKYRIE_LANCE.get());
        this.tag(AetherTags.Items.TOOLS_HAMMERS).add(AetherItems.HAMMER_OF_KINGBDOGZ.get());

        this.tag(AetherTags.Items.ACCESSORIES_RINGS).add(
                AetherItems.IRON_RING.get(),
                AetherItems.GOLDEN_RING.get(),
                AetherItems.ZANITE_RING.get(),
                AetherItems.ICE_RING.get());
        this.tag(AetherTags.Items.ACCESSORIES_PENDANTS).add(
                AetherItems.IRON_PENDANT.get(),
                AetherItems.GOLDEN_PENDANT.get(),
                AetherItems.ZANITE_PENDANT.get(),
                AetherItems.ICE_PENDANT.get());
        this.tag(AetherTags.Items.ACCESSORIES_GLOVES).add(
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
        this.tag(AetherTags.Items.ACCESSORIES_CAPES).add(
                AetherItems.RED_CAPE.get(),
                AetherItems.BLUE_CAPE.get(),
                AetherItems.YELLOW_CAPE.get(),
                AetherItems.WHITE_CAPE.get(),
                AetherItems.SWET_CAPE.get(),
                AetherItems.INVISIBILITY_CLOAK.get(),
                AetherItems.AGILITY_CAPE.get(),
                AetherItems.VALKYRIE_CAPE.get());
        this.tag(AetherTags.Items.ACCESSORIES_MISCELLANEOUS).add(
                AetherItems.GOLDEN_FEATHER.get(),
                AetherItems.REGENERATION_STONE.get(),
                AetherItems.IRON_BUBBLE.get());
        this.tag(AetherTags.Items.ACCESSORIES_SHIELDS).add(AetherItems.SHIELD_OF_REPULSION.get());

        this.tag(AetherTags.Items.AETHER_RING).addTag(AetherTags.Items.ACCESSORIES_RINGS);
        this.tag(AetherTags.Items.AETHER_PENDANT).addTag(AetherTags.Items.ACCESSORIES_PENDANTS);
        this.tag(AetherTags.Items.AETHER_GLOVES).addTag(AetherTags.Items.ACCESSORIES_GLOVES);
        this.tag(AetherTags.Items.AETHER_CAPE).addTag(AetherTags.Items.ACCESSORIES_CAPES);
        this.tag(AetherTags.Items.AETHER_ACCESSORY).addTag(AetherTags.Items.ACCESSORIES_MISCELLANEOUS);
        this.tag(AetherTags.Items.AETHER_SHIELD).addTag(AetherTags.Items.ACCESSORIES_SHIELDS);

        this.tag(AetherTags.Items.ACCESSORIES).addTags(
                AetherTags.Items.ACCESSORIES_RINGS,
                AetherTags.Items.ACCESSORIES_PENDANTS,
                AetherTags.Items.ACCESSORIES_GLOVES,
                AetherTags.Items.ACCESSORIES_CAPES,
                AetherTags.Items.AETHER_ACCESSORY,
                AetherTags.Items.ACCESSORIES_SHIELDS);

        // Forge
        this.tag(Tags.Items.BOOKSHELVES).add(AetherBlocks.SKYROOT_BOOKSHELF.get().asItem());
        this.tag(Tags.Items.FENCE_GATES_WOODEN).add(AetherBlocks.SKYROOT_FENCE_GATE.get().asItem());
        this.tag(Tags.Items.FENCES_WOODEN).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        this.tag(Tags.Items.FENCE_GATES).add(AetherBlocks.SKYROOT_FENCE_GATE.get().asItem());
        this.tag(Tags.Items.FENCES).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        this.tag(Tags.Items.EGGS).addTag(AetherTags.Items.MOA_EGGS);
        this.tag(Tags.Items.GEMS).add(AetherItems.ZANITE_GEMSTONE.get());
        this.tag(Tags.Items.GLASS_COLORLESS).add(AetherBlocks.QUICKSOIL_GLASS.get().asItem());
        this.tag(Tags.Items.GLASS_PANES_COLORLESS).add(AetherBlocks.QUICKSOIL_GLASS_PANE.get().asItem());
        this.tag(Tags.Items.ORE_RATES_SINGULAR).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        this.tag(Tags.Items.ORES).add(
                AetherBlocks.AMBROSIUM_ORE.get().asItem(),
                AetherBlocks.ZANITE_ORE.get().asItem(),
                AetherBlocks.GRAVITITE_ORE.get().asItem());
        this.tag(Tags.Items.RODS_WOODEN).add(AetherItems.SKYROOT_STICK.get());
        this.tag(Tags.Items.SLIMEBALLS).addTag(AetherTags.Items.SWET_BALLS);
        this.tag(Tags.Items.STORAGE_BLOCKS).add(
                AetherBlocks.AMBROSIUM_BLOCK.get().asItem(),
                AetherBlocks.ZANITE_BLOCK.get().asItem());
        this.tag(Tags.Items.TOOLS).addTag(AetherTags.Items.TOOLS_HAMMERS);
        this.tag(Tags.Items.TOOLS_BOWS).add(AetherItems.PHOENIX_BOW.get());
        this.tag(Tags.Items.ARMORS_HELMETS).add(
                AetherItems.ZANITE_HELMET.get(),
                AetherItems.GRAVITITE_HELMET.get(),
                AetherItems.NEPTUNE_HELMET.get(),
                AetherItems.PHOENIX_HELMET.get(),
                AetherItems.OBSIDIAN_HELMET.get(),
                AetherItems.VALKYRIE_HELMET.get());
        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(
                AetherItems.ZANITE_CHESTPLATE.get(),
                AetherItems.GRAVITITE_CHESTPLATE.get(),
                AetherItems.NEPTUNE_CHESTPLATE.get(),
                AetherItems.PHOENIX_CHESTPLATE.get(),
                AetherItems.OBSIDIAN_CHESTPLATE.get(),
                AetherItems.VALKYRIE_CHESTPLATE.get());
        this.tag(Tags.Items.ARMORS_LEGGINGS).add(
                AetherItems.ZANITE_LEGGINGS.get(),
                AetherItems.GRAVITITE_LEGGINGS.get(),
                AetherItems.NEPTUNE_LEGGINGS.get(),
                AetherItems.PHOENIX_LEGGINGS.get(),
                AetherItems.OBSIDIAN_LEGGINGS.get(),
                AetherItems.VALKYRIE_LEGGINGS.get());
        this.tag(Tags.Items.ARMORS_BOOTS).add(
                AetherItems.ZANITE_BOOTS.get(),
                AetherItems.GRAVITITE_BOOTS.get(),
                AetherItems.NEPTUNE_BOOTS.get(),
                AetherItems.PHOENIX_BOOTS.get(),
                AetherItems.OBSIDIAN_BOOTS.get(),
                AetherItems.VALKYRIE_BOOTS.get(),
                AetherItems.SENTRY_BOOTS.get());

        this.tag(AetherTags.Items.RANDOMIUM_BLACKLIST).addTags(
                AetherTags.Items.LOCKED_DUNGEON_BLOCKS,
                AetherTags.Items.TRAPPED_DUNGEON_BLOCKS,
                AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS,
                AetherTags.Items.TREASURE_DOORWAY_DUNGEON_BLOCKS).add(
                AetherBlocks.CHEST_MIMIC.get().asItem(),
                AetherBlocks.TREASURE_CHEST.get().asItem()
        );

        // Vanilla
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(AetherBlocks.HOLYSTONE.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(AetherBlocks.SKYROOT_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS).add(AetherBlocks.SKYROOT_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(AetherBlocks.SKYROOT_FENCE.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS).add(AetherBlocks.SKYROOT_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(AetherBlocks.SKYROOT_TRAPDOOR.get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(AetherBlocks.SKYROOT_BUTTON.get().asItem());
        this.tag(ItemTags.STONE_BUTTONS).add(AetherBlocks.HOLYSTONE_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(AetherBlocks.SKYROOT_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.SAPLINGS).add(
                AetherBlocks.SKYROOT_SAPLING.get().asItem(),
                AetherBlocks.GOLDEN_OAK_SAPLING.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN).addTags( // Charcoal Recipes
                AetherTags.Items.SKYROOT_LOGS,
                AetherTags.Items.GOLDEN_OAK_LOGS);
        this.tag(ItemTags.STAIRS).add(
                AetherBlocks.SKYROOT_STAIRS.get().asItem(),
                AetherBlocks.CARVED_STAIRS.get().asItem(),
                AetherBlocks.ANGELIC_STAIRS.get().asItem(),
                AetherBlocks.HELLFIRE_STAIRS.get().asItem(),
                AetherBlocks.HOLYSTONE_STAIRS.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get().asItem(),
                AetherBlocks.ICESTONE_STAIRS.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_STAIRS.get().asItem(),
                AetherBlocks.AEROGEL_STAIRS.get().asItem());
        this.tag(ItemTags.SLABS).add(
                AetherBlocks.SKYROOT_SLAB.get().asItem(),
                AetherBlocks.CARVED_SLAB.get().asItem(),
                AetherBlocks.ANGELIC_SLAB.get().asItem(),
                AetherBlocks.HELLFIRE_SLAB.get().asItem(),
                AetherBlocks.HOLYSTONE_SLAB.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_SLAB.get().asItem(),
                AetherBlocks.ICESTONE_SLAB.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_SLAB.get().asItem(),
                AetherBlocks.AEROGEL_SLAB.get().asItem());
        this.tag(ItemTags.WALLS).add(
                AetherBlocks.CARVED_WALL.get().asItem(),
                AetherBlocks.ANGELIC_WALL.get().asItem(),
                AetherBlocks.HELLFIRE_WALL.get().asItem(),
                AetherBlocks.HOLYSTONE_WALL.get().asItem(),
                AetherBlocks.MOSSY_HOLYSTONE_WALL.get().asItem(),
                AetherBlocks.ICESTONE_WALL.get().asItem(),
                AetherBlocks.HOLYSTONE_BRICK_WALL.get().asItem(),
                AetherBlocks.AEROGEL_WALL.get().asItem());
        this.tag(ItemTags.LEAVES).add(
                AetherBlocks.SKYROOT_LEAVES.get().asItem(),
                AetherBlocks.GOLDEN_OAK_LEAVES.get().asItem(),
                AetherBlocks.CRYSTAL_LEAVES.get().asItem(),
                AetherBlocks.CRYSTAL_FRUIT_LEAVES.get().asItem(),
                AetherBlocks.HOLIDAY_LEAVES.get().asItem(),
                AetherBlocks.DECORATED_HOLIDAY_LEAVES.get().asItem());
        this.tag(ItemTags.SMALL_FLOWERS).add(
                AetherBlocks.PURPLE_FLOWER.get().asItem(),
                AetherBlocks.WHITE_FLOWER.get().asItem());
        this.tag(ItemTags.BEDS).add(AetherBlocks.SKYROOT_BED.get().asItem());
        this.tag(ItemTags.PIGLIN_LOVED).add(
                AetherItems.VICTORY_MEDAL.get(),
                AetherItems.GOLDEN_RING.get(),
                AetherItems.GOLDEN_PENDANT.get(),
                AetherItems.GOLDEN_GLOVES.get());
        this.tag(ItemTags.FOX_FOOD).add(
                AetherItems.BLUE_BERRY.get(),
                AetherItems.ENCHANTED_BERRY.get());
        this.tag(ItemTags.SIGNS).add(AetherBlocks.SKYROOT_SIGN.get().asItem());
        this.tag(ItemTags.HANGING_SIGNS).add(AetherBlocks.SKYROOT_HANGING_SIGN.get().asItem());
        this.tag(ItemTags.MUSIC_DISCS).add(
                AetherItems.MUSIC_DISC_AETHER_TUNE.get(),
                AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(),
                AetherItems.MUSIC_DISC_CHINCHILLA.get(),
                AetherItems.MUSIC_DISC_HIGH.get());
        this.tag(ItemTags.BOOKSHELF_BOOKS).add(AetherItems.BOOK_OF_LORE.get());
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
                AetherItems.ZANITE_GEMSTONE.get(),
                AetherBlocks.ENCHANTED_GRAVITITE.get().asItem());
        this.tag(ItemTags.BOATS).add(AetherItems.SKYROOT_BOAT.get());
        this.tag(ItemTags.CHEST_BOATS).add(AetherItems.SKYROOT_CHEST_BOAT.get());
        this.tag(ItemTags.CLUSTER_MAX_HARVESTABLES).add(
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.HOLYSTONE_PICKAXE.get(),
                AetherItems.ZANITE_PICKAXE.get(),
                AetherItems.GRAVITITE_PICKAXE.get(),
                AetherItems.VALKYRIE_PICKAXE.get());
        this.tag(ItemTags.SWORDS).add(
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
        this.tag(ItemTags.AXES).add(
                AetherItems.SKYROOT_AXE.get(),
                AetherItems.HOLYSTONE_AXE.get(),
                AetherItems.ZANITE_AXE.get(),
                AetherItems.GRAVITITE_AXE.get(),
                AetherItems.VALKYRIE_AXE.get());
        this.tag(ItemTags.PICKAXES).add(
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.HOLYSTONE_PICKAXE.get(),
                AetherItems.ZANITE_PICKAXE.get(),
                AetherItems.GRAVITITE_PICKAXE.get(),
                AetherItems.VALKYRIE_PICKAXE.get());
        this.tag(ItemTags.SHOVELS).add(
                AetherItems.SKYROOT_SHOVEL.get(),
                AetherItems.HOLYSTONE_SHOVEL.get(),
                AetherItems.ZANITE_SHOVEL.get(),
                AetherItems.GRAVITITE_SHOVEL.get(),
                AetherItems.VALKYRIE_SHOVEL.get());
        this.tag(ItemTags.HOES).add(
                AetherItems.SKYROOT_HOE.get(),
                AetherItems.HOLYSTONE_HOE.get(),
                AetherItems.ZANITE_HOE.get(),
                AetherItems.GRAVITITE_HOE.get(),
                AetherItems.VALKYRIE_HOE.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR).add(
                AetherItems.ZANITE_HELMET.get(),
                AetherItems.ZANITE_CHESTPLATE.get(),
                AetherItems.ZANITE_LEGGINGS.get(),
                AetherItems.ZANITE_BOOTS.get(),
                AetherItems.ZANITE_GLOVES.get(),
                AetherItems.GRAVITITE_HELMET.get(),
                AetherItems.GRAVITITE_CHESTPLATE.get(),
                AetherItems.GRAVITITE_LEGGINGS.get(),
                AetherItems.GRAVITITE_BOOTS.get(),
                AetherItems.GRAVITITE_GLOVES.get(),
                AetherItems.NEPTUNE_HELMET.get(),
                AetherItems.NEPTUNE_CHESTPLATE.get(),
                AetherItems.NEPTUNE_LEGGINGS.get(),
                AetherItems.NEPTUNE_BOOTS.get(),
                AetherItems.NEPTUNE_GLOVES.get(),
                AetherItems.PHOENIX_HELMET.get(),
                AetherItems.PHOENIX_CHESTPLATE.get(),
                AetherItems.PHOENIX_LEGGINGS.get(),
                AetherItems.PHOENIX_BOOTS.get(),
                AetherItems.PHOENIX_GLOVES.get(),
                AetherItems.OBSIDIAN_HELMET.get(),
                AetherItems.OBSIDIAN_CHESTPLATE.get(),
                AetherItems.OBSIDIAN_LEGGINGS.get(),
                AetherItems.OBSIDIAN_BOOTS.get(),
                AetherItems.OBSIDIAN_GLOVES.get(),
                AetherItems.LEATHER_GLOVES.get(),
                AetherItems.IRON_GLOVES.get(),
                AetherItems.GOLDEN_GLOVES.get(),
                AetherItems.DIAMOND_GLOVES.get(),
                AetherItems.NETHERITE_GLOVES.get(),
                AetherItems.CHAINMAIL_GLOVES.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(
                AetherItems.ZANITE_GEMSTONE.get(),
                AetherBlocks.ENCHANTED_GRAVITITE.get().asItem(),
                AetherItems.GOLDEN_AMBER.get());
    }
}
