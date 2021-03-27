package com.gildedgames.aether.core.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.data.provider.AetherRecipeProvider;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class AetherRecipeData extends AetherRecipeProvider
{
    public AetherRecipeData(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        makeWood(AetherBlocks.SKYROOT_WOOD, AetherBlocks.SKYROOT_LOG).save(consumer);
        makeWood(AetherBlocks.GOLDEN_OAK_WOOD, AetherBlocks.GOLDEN_OAK_LOG).save(consumer);
        makeWood(AetherBlocks.STRIPPED_SKYROOT_WOOD, AetherBlocks.STRIPPED_SKYROOT_LOG).save(consumer);

        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.SKYROOT_LOG).save(consumer);
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.STRIPPED_SKYROOT_LOG).save(consumer, name("stripped_log_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.SKYROOT_WOOD).save(consumer, name("wood_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.STRIPPED_SKYROOT_WOOD).save(consumer, name("stripped_wood_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.GOLDEN_OAK_LOG).save(consumer, name("golden_log_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.GOLDEN_OAK_WOOD).save(consumer, name("golden_wood_to_planks"));
        makeBricks(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE).save(consumer);

        makeOreToBlock(AetherBlocks.ZANITE_BLOCK, AetherItems.ZANITE_GEMSTONE).save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.ALTAR.get(), 1)
                .pattern("HHH")
                .pattern("HZH")
                .pattern("HHH")
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('Z', AetherItems.ZANITE_GEMSTONE.get())
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.FREEZER.get(), 1)
                .pattern("HHH")
                .pattern("HIH")
                .pattern("SSS")
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('I', AetherBlocks.ICESTONE.get())
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.INCUBATOR.get(), 1)
                .pattern("HHH")
                .pattern("HAH")
                .pattern("HHH")
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('A', AetherBlocks.AMBROSIUM_TORCH.get())
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.AMBROSIUM_TORCH.get(), 4)
                .pattern("A")
                .pattern("S")
                .define('A', AetherItems.AMBROSIUM_SHARD.get())
                .define('S', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_holystone_pickaxe", has(AetherItems.HOLYSTONE_PICKAXE.get()))
                .save(consumer);

        makeFence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeFenceGate(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS).save(consumer);

        makeWall(AetherBlocks.CARVED_WALL, AetherBlocks.CARVED_STONE).save(consumer);
        makeWall(AetherBlocks.ANGELIC_WALL, AetherBlocks.ANGELIC_STONE).save(consumer);
        makeWall(AetherBlocks.HELLFIRE_WALL, AetherBlocks.HELLFIRE_STONE).save(consumer);
        makeWall(AetherBlocks.HOLYSTONE_WALL, AetherBlocks.HOLYSTONE).save(consumer);
        makeWall(AetherBlocks.MOSSY_HOLYSTONE_WALL, AetherBlocks.MOSSY_HOLYSTONE).save(consumer);
        makeWall(AetherBlocks.HOLYSTONE_BRICK_WALL, AetherBlocks.HOLYSTONE_BRICKS).save(consumer);
        makeWall(AetherBlocks.AEROGEL_WALL, AetherBlocks.AEROGEL).save(consumer);

        makeStairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeStairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE).save(consumer);
        makeStairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE).save(consumer);
        makeStairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE).save(consumer);
        makeStairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE).save(consumer);
        makeStairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE).save(consumer);
        makeStairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS).save(consumer);
        makeStairs(AetherBlocks.AEROGEL_STAIRS, AetherBlocks.AEROGEL).save(consumer);

        makeSlab(AetherBlocks.SKYROOT_SLAB, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeSlab(AetherBlocks.CARVED_SLAB, AetherBlocks.CARVED_STONE).save(consumer);
        makeSlab(AetherBlocks.ANGELIC_SLAB, AetherBlocks.ANGELIC_STONE).save(consumer);
        makeSlab(AetherBlocks.HELLFIRE_SLAB, AetherBlocks.HELLFIRE_STONE).save(consumer);
        makeSlab(AetherBlocks.HOLYSTONE_SLAB, AetherBlocks.HOLYSTONE).save(consumer);
        makeSlab(AetherBlocks.MOSSY_HOLYSTONE_SLAB, AetherBlocks.MOSSY_HOLYSTONE).save(consumer);
        makeSlab(AetherBlocks.HOLYSTONE_BRICK_SLAB, AetherBlocks.HOLYSTONE_BRICKS).save(consumer);
        makeSlab(AetherBlocks.AEROGEL_SLAB, AetherBlocks.AEROGEL).save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.SKYROOT_BOOKSHELF.get(), 1)
                .pattern("SSS")
                .pattern("BBB")
                .pattern("SSS")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .define('B', Items.BOOK)
                .unlockedBy("has_book", has(Items.BOOK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.SKYROOT_BED.get(), 1)
                .pattern("WWW")
                .pattern("SSS")
                .define('W', ItemTags.WOOL)
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_book", has(ItemTags.WOOL))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(Items.PURPLE_DYE)
                .requires(AetherBlocks.PURPLE_FLOWER.get())
                .unlockedBy("has_purple_flower", has(AetherBlocks.PURPLE_FLOWER.get()))
                .group("purple_dye")
                .save(consumer, name("flower_to_purple_dye"));

        ShapelessRecipeBuilder.shapeless(Items.WHITE_DYE)
                .requires(AetherBlocks.WHITE_FLOWER.get())
                .unlockedBy("has_white_flower", has(AetherBlocks.WHITE_FLOWER.get()))
                .group("white_dye")
                .save(consumer, name("flower_to_white_dye"));

        makeBlockToOre(AetherItems.ZANITE_GEMSTONE, AetherBlocks.ZANITE_BLOCK).save(consumer);


        makePickaxeWithBlock(AetherItems.SKYROOT_PICKAXE, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeAxeWithBlock(AetherItems.SKYROOT_AXE, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeShovelWithBlock(AetherItems.SKYROOT_SHOVEL, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeHoeWithBlock(AetherItems.SKYROOT_HOE, AetherBlocks.SKYROOT_PLANKS).save(consumer);

        makePickaxeWithBlock(AetherItems.HOLYSTONE_PICKAXE, AetherBlocks.HOLYSTONE).save(consumer);
        makeAxeWithBlock(AetherItems.HOLYSTONE_AXE, AetherBlocks.HOLYSTONE).save(consumer);
        makeShovelWithBlock(AetherItems.HOLYSTONE_SHOVEL, AetherBlocks.HOLYSTONE).save(consumer);
        makeHoeWithBlock(AetherItems.HOLYSTONE_HOE, AetherBlocks.HOLYSTONE).save(consumer);

        makePickaxe(AetherItems.ZANITE_PICKAXE, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeAxe(AetherItems.ZANITE_AXE, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeShovel(AetherItems.ZANITE_SHOVEL, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeHoe(AetherItems.ZANITE_HOE, AetherItems.ZANITE_GEMSTONE).save(consumer);

        makePickaxeWithBlock(AetherItems.GRAVITITE_PICKAXE, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeAxeWithBlock(AetherItems.GRAVITITE_AXE, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeShovelWithBlock(AetherItems.GRAVITITE_SHOVEL, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeHoeWithBlock(AetherItems.GRAVITITE_HOE, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);

        makeSwordWithBlock(AetherItems.SKYROOT_SWORD, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        makeSwordWithBlock(AetherItems.HOLYSTONE_SWORD, AetherBlocks.HOLYSTONE).save(consumer);
        makeSword(AetherItems.ZANITE_SWORD, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeSwordWithBlock(AetherItems.GRAVITITE_SWORD, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);

        ShapedRecipeBuilder.shaped(AetherItems.GOLDEN_DART.get(), 4)
                .pattern("F")
                .pattern("S")
                .pattern("G")
                .define('F', Tags.Items.FEATHERS)
                .define('S', AetherTags.Items.SKYROOT_STICKS)
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .unlockedBy("has_amber", has(AetherItems.GOLDEN_AMBER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherItems.GOLDEN_DART_SHOOTER.get(), 1)
                .pattern("S")
                .pattern("S")
                .pattern("G")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(AetherBlocks.SKYROOT_BUTTON.get(), 1)
                .requires(AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_button")
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(AetherBlocks.HOLYSTONE_BUTTON.get(), 1)
                .requires(AetherBlocks.HOLYSTONE.get())
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .group("button")
                .save(consumer);

        makeHelmet(AetherItems.ZANITE_HELMET, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeChestplate(AetherItems.ZANITE_CHESTPLATE, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeLeggings(AetherItems.ZANITE_LEGGINGS, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeBoots(AetherItems.ZANITE_BOOTS, AetherItems.ZANITE_GEMSTONE).save(consumer);

        makeHelmetWithBlock(AetherItems.GRAVITITE_HELMET, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeChestplateWithBlock(AetherItems.GRAVITITE_CHESTPLATE, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeLeggingsWithBlock(AetherItems.GRAVITITE_LEGGINGS, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);
        makeBootsWithBlock(AetherItems.GRAVITITE_BOOTS, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);

        makeRing(AetherItems.IRON_RING, Items.IRON_INGOT).save(consumer);
        makeRing(AetherItems.GOLD_RING, Items.GOLD_INGOT).save(consumer);
        makeRing(AetherItems.ZANITE_RING, AetherItems.ZANITE_GEMSTONE.get()).save(consumer);

        ShapedRecipeBuilder.shaped(AetherItems.SKYROOT_STICK.get(), 4)
                .pattern("S")
                .pattern("S")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(AetherItems.BOOK_OF_LORE.get())
                .requires(Items.BOOK)
                .requires(AetherTags.Items.BOOK_OF_LORE_MATERIALS)
                .unlockedBy("has_book", has(Items.BOOK))
                .group("book_of_lore")
                .save(consumer);


        ShapedRecipeBuilder.shaped(Items.SADDLE, 1)
                .pattern("LLL")
                .pattern("LSL")
                .define('L', Items.LEATHER)
                .define('S', Items.STRING)
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer, name("aether_saddle"));

        ShapedRecipeBuilder.shaped(Items.LEAD, 2)
                .pattern("SS ")
                .pattern("SB ")
                .pattern("  S")
                .define('B', AetherItems.SWET_BALL.get())
                .define('S', Tags.Items.STRING)
                .unlockedBy("has_swet_ball", has(AetherItems.SWET_BALL.get()))
                .group("lead")
                .save(consumer, name("swet_lead"));

        ShapedRecipeBuilder.shaped(AetherBlocks.SKYROOT_DOOR.get(), 3)
                .pattern("SS")
                .pattern("SS")
                .pattern("SS")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_door")
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.SKYROOT_TRAPDOOR.get(), 2)
                .pattern("SSS")
                .pattern("SSS")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_trapdoor")
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.SKYROOT_PRESSURE_PLATE.get(), 1)
                .pattern("SS")
                .define('S', AetherBlocks.SKYROOT_PLANKS.get())
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_pressure_plate")
                .save(consumer);

        ShapedRecipeBuilder.shaped(AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get(), 1)
                .pattern("HH")
                .define('H', AetherBlocks.HOLYSTONE.get())
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .group("pressure_plate")
                .save(consumer);

        ShapedRecipeBuilder.shaped(Blocks.BARREL, 1)
                .pattern("SHS")
                .pattern("S S")
                .pattern("SHS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('H', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .unlockedBy("has_skyroot_slab", has(AetherBlocks.SKYROOT_SLAB.get()))
                .group("barrel")
                .save(consumer, name("skyroot_barrel"));

        ShapedRecipeBuilder.shaped(Blocks.BEEHIVE, 1)
                .pattern("SSS")
                .pattern("CCC")
                .pattern("SSS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Items.HONEYCOMB)
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .group("beehive")
                .save(consumer, name("skyroot_beehive"));

        ShapedRecipeBuilder.shaped(Items.BOWL, 4)
                .pattern("S S")
                .pattern(" S ")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_brown_mushroom", has(Blocks.BROWN_MUSHROOM))
                .unlockedBy("has_red_mushroom", has(Blocks.RED_MUSHROOM))
                .unlockedBy("has_mushroom_stew", has(Items.MUSHROOM_STEW))
                .group("bowl")
                .save(consumer, name("skyroot_bowl"));

        ShapedRecipeBuilder.shaped(Blocks.CARTOGRAPHY_TABLE, 1)
                .pattern("PP")
                .pattern("SS")
                .pattern("SS")
                .define('P', Items.PAPER)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_paper", has(Items.PAPER))
                .group("cartography_table")
                .save(consumer, name("skyroot_cartography_table"));

        ShapedRecipeBuilder.shaped(Blocks.CHEST, 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.atLeast(10), MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, new ItemPredicate[0]))
                .group("chest")
                .save(consumer, name("skyroot_chest"));

        ShapedRecipeBuilder.shaped(Blocks.CRAFTING_TABLE, 1)
                .pattern("SS")
                .pattern("SS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_skyroot", has(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("crafting_table")
                .save(consumer, name("skyroot_crafting_table"));

        ShapedRecipeBuilder.shaped(Blocks.FLETCHING_TABLE, 1)
                .pattern("FF")
                .pattern("SS")
                .pattern("SS")
                .define('F', Items.FLINT)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_flint", has(Items.FLINT))
                .group("fletching_table")
                .save(consumer, name("skyroot_fletching_table"));

        ShapedRecipeBuilder.shaped(Blocks.GRINDSTONE, 1)
                .pattern("THT")
                .pattern("S S")
                .define('T', Tags.Items.RODS_WOODEN)
                .define('H', Blocks.STONE_SLAB)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_stone_slab", has(Blocks.STONE_SLAB))
                .group("grindstone")
                .save(consumer, name("skyroot_grindstone"));

        ShapedRecipeBuilder.shaped(Blocks.JUKEBOX, 1)
                .pattern("SSS")
                .pattern("SDS")
                .pattern("SSS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .group("jukebox")
                .save(consumer, name("skyroot_jukebox"));

        ShapedRecipeBuilder.shaped(Blocks.LOOM, 1)
                .pattern("TT")
                .pattern("SS")
                .define('T', Tags.Items.STRING)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .group("loom")
                .save(consumer, name("skyroot_loom"));

        ShapedRecipeBuilder.shaped(Blocks.NOTE_BLOCK, 1)
                .pattern("SSS")
                .pattern("SRS")
                .pattern("SSS")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .group("note_block")
                .save(consumer, name("skyroot_note_block"));

        ShapedRecipeBuilder.shaped(Blocks.PISTON, 1)
                .pattern("SSS")
                .pattern("CIC")
                .pattern("CRC")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Blocks.COBBLESTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .group("piston")
                .save(consumer, name("skyroot_piston"));

        ShapedRecipeBuilder.shaped(Items.SHIELD, 1)
                .pattern("SIS")
                .pattern("SSS")
                .pattern(" S ")
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .group("shield")
                .save(consumer, name("skyroot_shield"));

        ShapedRecipeBuilder.shaped(Blocks.SMITHING_TABLE, 1)
                .pattern("II")
                .pattern("SS")
                .pattern("SS")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .group("smithing_table")
                .save(consumer, name("skyroot_smithing_table"));

        ShapedRecipeBuilder.shaped(Blocks.TRIPWIRE_HOOK, 2)
                .pattern("I")
                .pattern("T")
                .pattern("S")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('T', Tags.Items.RODS_WOODEN)
                .define('S', AetherTags.Items.PLANKS_CRAFTING)
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .group("tripwire_hook")
                .save(consumer, name("skyroot_tripwire_hook"));

        ShapedRecipeBuilder.shaped(Blocks.BREWING_STAND, 1)
                .pattern(" B ")
                .pattern("HHH")
                .define('B', Tags.Items.RODS_BLAZE)
                .define('H', AetherTags.Items.STONE_CRAFTING)
                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
                .group("brewing_stand")
                .save(consumer, name("holystone_brewing_stand"));

        ShapedRecipeBuilder.shaped(Blocks.FURNACE, 1)
                .pattern("HHH")
                .pattern("H H")
                .pattern("HHH")
                .define('H', AetherTags.Items.STONE_CRAFTING)
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .group("furnace")
                .save(consumer, name("holystone_furnace"));

        smeltingRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, name("smelt_ambrosium"));
        blastingRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, name("blast_ambrosium"));

        smeltingRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, name("smelt_zanite"));
        blastingRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, name("blast_zanite"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICKS.get()).save(consumer, name("holystone_brick_stonecutting"));

        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_WALL.get()).save(consumer, name("carved_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_STAIRS.get()).save(consumer, name("carved_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_SLAB.get(), 2).save(consumer, name("carved_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_WALL.get()).save(consumer, name("angelic_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_STAIRS.get()).save(consumer, name("angelic_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_SLAB.get(), 2).save(consumer, name("angelic_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_WALL.get()).save(consumer, name("hellfire_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_STAIRS.get()).save(consumer, name("hellfire_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_SLAB.get(), 2).save(consumer, name("hellfire_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_WALL.get()).save(consumer, name("holystone_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_STAIRS.get()).save(consumer, name("holystone_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_SLAB.get(), 2).save(consumer, name("holystone_slab_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_WALL.get()).save(consumer, name("holystone_to_holystone_brick_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get()).save(consumer, name("holystone_to_holystone_brick_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), 2).save(consumer, name("holystone_to_holystone_brick_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_WALL.get()).save(consumer, name("mossy_holystone_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get()).save(consumer, name("mossy_holystone_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), 2).save(consumer, name("mossy_holystone_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_WALL.get()).save(consumer, name("holystone_brick_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get()).save(consumer, name("holystone_brick_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), 2).save(consumer, name("holystone_brick_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_WALL.get()).save(consumer, name("aerogel_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_STAIRS.get()).save(consumer, name("aerogel_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_SLAB.get(), 2).save(consumer, name("aerogel_slab_stonecutting"));

        //repairingRecipe(AetherItems.SKYROOT_AXE.get(), 225).save(consumer, name("skyroot_axe_repairing"));

        enchantingRecipe(AetherItems.ENCHANTED_DART.get(), AetherItems.GOLDEN_DART.get(), 250).save(consumer, name("enchanted_dart_enchanting"));
        enchantingRecipe(AetherItems.ENCHANTED_DART_SHOOTER.get(), AetherItems.GOLDEN_DART_SHOOTER.get(), 500).save(consumer, name("enchanted_dart_shooter_enchanting"));

        enchantingRecipe(AetherItems.HEALING_STONE.get(), AetherBlocks.HOLYSTONE.get(), 750).save(consumer, name("healing_stone_enchanting"));
        enchantingRecipe(AetherBlocks.ENCHANTED_GRAVITITE.get(), AetherBlocks.GRAVITITE_ORE.get(), 1000).save(consumer, name("enchanted_gravitite_enchanting"));
        enchantingRecipe(AetherBlocks.QUICKSOIL_GLASS.get(), AetherBlocks.QUICKSOIL.get(), 250).save(consumer, name("quicksoil_glass_enchanting"));

        enchantingRecipe(AetherItems.ENCHANTED_BERRY.get(), AetherItems.BLUE_BERRY.get(), 300).save(consumer, name("enchanted_berry_enchanting"));

        enchantingRecipe(AetherItems.MUSIC_DISC_AETHER_TUNE.get(), AetherTags.Items.ACCEPTED_MUSIC_DISCS, 2500).save(consumer, name("aether_tune_enchanting"));
        enchantingRecipe(AetherItems.MUSIC_DISC_LEGACY.get(), Items.MUSIC_DISC_CAT, 2500).save(consumer, name("legacy_enchanting"));

        freezingRecipe(AetherBlocks.BLUE_AERCLOUD.get(), AetherBlocks.COLD_AERCLOUD.get(), 100).save(consumer, name("blue_aercloud_freezing"));
        freezingRecipe(AetherBlocks.CRYSTAL_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get(), 150).save(consumer, name("crystal_leaves_freezing"));

        freezingRecipe(AetherItems.MUSIC_DISC_WELCOMING_SKIES.get(), AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(), 800).save(consumer, name("welcoming_skies_freezing"));

        freezingRecipe(Blocks.ICE, Blocks.PACKED_ICE, 750).save(consumer, name("packed_ice_freezing"));
        freezingRecipe(Items.WATER_BUCKET, Blocks.ICE, 500).save(consumer, name("ice_from_bucket_freezing"));
        freezingRecipe(Items.LAVA_BUCKET, Blocks.OBSIDIAN, 500).save(consumer, name("obsidian_from_bucket_freezing"));

        freezingRecipe(AetherItems.ICE_RING.get(), AetherItems.IRON_RING.get(), 2500).save(consumer, name("ice_ring_from_iron_freezing"));
        freezingRecipe(AetherItems.ICE_RING.get(), AetherItems.GOLD_RING.get(), 2500).save(consumer, name("ice_ring_from_gold_freezing"));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(Aether.MODID, name);
    }
}
