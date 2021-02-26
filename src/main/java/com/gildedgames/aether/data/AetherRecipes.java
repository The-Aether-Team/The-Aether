package com.gildedgames.aether.data;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.data.provider.AetherRecipesProvider;
import com.gildedgames.aether.registry.AetherBlocks;
import com.gildedgames.aether.registry.AetherItems;
import com.gildedgames.aether.registry.AetherTags;
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

public class AetherRecipes extends AetherRecipesProvider
{
    public AetherRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        makeWood(AetherBlocks.SKYROOT_WOOD, AetherBlocks.SKYROOT_LOG).build(consumer);
        makeWood(AetherBlocks.GOLDEN_OAK_WOOD, AetherBlocks.GOLDEN_OAK_LOG).build(consumer);
        makeWood(AetherBlocks.STRIPPED_SKYROOT_WOOD, AetherBlocks.STRIPPED_SKYROOT_LOG).build(consumer);

        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.SKYROOT_LOG).build(consumer);
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.STRIPPED_SKYROOT_LOG).build(consumer, name("stripped_log_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.SKYROOT_WOOD).build(consumer, name("wood_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.STRIPPED_SKYROOT_WOOD).build(consumer, name("stripped_wood_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.GOLDEN_OAK_LOG).build(consumer, name("golden_log_to_planks"));
        makePlanks(AetherBlocks.SKYROOT_PLANKS, AetherBlocks.GOLDEN_OAK_WOOD).build(consumer, name("golden_wood_to_planks"));
        makeBricks(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE).build(consumer);

        makeOreToBlock(AetherBlocks.ZANITE_BLOCK, AetherItems.ZANITE_GEMSTONE).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.ALTAR.get(), 1)
                .patternLine("HHH")
                .patternLine("HZH")
                .patternLine("HHH")
                .key('H', AetherBlocks.HOLYSTONE.get())
                .key('Z', AetherItems.ZANITE_GEMSTONE.get())
                .addCriterion("has_holystone", hasItem(AetherBlocks.HOLYSTONE.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.FREEZER.get(), 1)
                .patternLine("HHH")
                .patternLine("HIH")
                .patternLine("SSS")
                .key('H', AetherBlocks.HOLYSTONE.get())
                .key('I', AetherBlocks.ICESTONE.get())
                .key('S', AetherBlocks.SKYROOT_PLANKS.get())
                .addCriterion("has_holystone", hasItem(AetherBlocks.HOLYSTONE.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.INCUBATOR.get(), 1)
                .patternLine("HHH")
                .patternLine("HAH")
                .patternLine("HHH")
                .key('H', AetherBlocks.HOLYSTONE.get())
                .key('A', AetherBlocks.AMBROSIUM_TORCH.get())
                .addCriterion("has_holystone", hasItem(AetherBlocks.HOLYSTONE.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.AMBROSIUM_TORCH.get(), 1)
                .patternLine("A")
                .patternLine("S")
                .key('A', AetherItems.AMBROSIUM_SHARD.get())
                .key('S', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_holystone_pickaxe", hasItem(AetherItems.HOLYSTONE_PICKAXE.get()))
                .build(consumer);

        makeFence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeFenceGate(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS).build(consumer);

        makeWall(AetherBlocks.CARVED_WALL, AetherBlocks.CARVED_STONE).build(consumer);
        makeWall(AetherBlocks.ANGELIC_WALL, AetherBlocks.ANGELIC_STONE).build(consumer);
        makeWall(AetherBlocks.HELLFIRE_WALL, AetherBlocks.HELLFIRE_STONE).build(consumer);
        makeWall(AetherBlocks.HOLYSTONE_WALL, AetherBlocks.HOLYSTONE).build(consumer);
        makeWall(AetherBlocks.MOSSY_HOLYSTONE_WALL, AetherBlocks.MOSSY_HOLYSTONE).build(consumer);
        makeWall(AetherBlocks.HOLYSTONE_BRICK_WALL, AetherBlocks.HOLYSTONE_BRICKS).build(consumer);
        makeWall(AetherBlocks.AEROGEL_WALL, AetherBlocks.AEROGEL).build(consumer);

        makeStairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeStairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE).build(consumer);
        makeStairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE).build(consumer);
        makeStairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE).build(consumer);
        makeStairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE).build(consumer);
        makeStairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE).build(consumer);
        makeStairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS).build(consumer);
        makeStairs(AetherBlocks.AEROGEL_STAIRS, AetherBlocks.AEROGEL).build(consumer);

        makeSlab(AetherBlocks.SKYROOT_SLAB, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeSlab(AetherBlocks.CARVED_SLAB, AetherBlocks.CARVED_STONE).build(consumer);
        makeSlab(AetherBlocks.ANGELIC_SLAB, AetherBlocks.ANGELIC_STONE).build(consumer);
        makeSlab(AetherBlocks.HELLFIRE_SLAB, AetherBlocks.HELLFIRE_STONE).build(consumer);
        makeSlab(AetherBlocks.HOLYSTONE_SLAB, AetherBlocks.HOLYSTONE).build(consumer);
        makeSlab(AetherBlocks.MOSSY_HOLYSTONE_SLAB, AetherBlocks.MOSSY_HOLYSTONE).build(consumer);
        makeSlab(AetherBlocks.HOLYSTONE_BRICK_SLAB, AetherBlocks.HOLYSTONE_BRICKS).build(consumer);
        makeSlab(AetherBlocks.AEROGEL_SLAB, AetherBlocks.AEROGEL).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.SKYROOT_BOOKSHELF.get(), 1)
                .patternLine("SSS")
                .patternLine("BBB")
                .patternLine("SSS")
                .key('S', AetherBlocks.SKYROOT_PLANKS.get())
                .key('B', Items.BOOK)
                .addCriterion("has_book", hasItem(Items.BOOK))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherBlocks.SKYROOT_BED.get(), 1)
                .patternLine("WWW")
                .patternLine("SSS")
                .key('W', ItemTags.WOOL)
                .key('S', AetherBlocks.SKYROOT_PLANKS.get())
                .addCriterion("has_book", hasItem(ItemTags.WOOL))
                .build(consumer);


        ShapelessRecipeBuilder.shapelessRecipe(Items.PURPLE_DYE)
                .addIngredient(AetherBlocks.PURPLE_FLOWER.get())
                .addCriterion("has_purple_flower", hasItem(AetherBlocks.PURPLE_FLOWER.get()))
                .setGroup("purple_dye")
                .build(consumer, name("flower_to_purple_dye"));

        ShapelessRecipeBuilder.shapelessRecipe(Items.WHITE_DYE)
                .addIngredient(AetherBlocks.WHITE_FLOWER.get())
                .addCriterion("has_white_flower", hasItem(AetherBlocks.WHITE_FLOWER.get()))
                .setGroup("white_dye")
                .build(consumer, name("flower_to_white_dye"));

        makeBlockToOre(AetherItems.ZANITE_GEMSTONE, AetherBlocks.ZANITE_BLOCK).build(consumer);


        makePickaxeWithBlock(AetherItems.SKYROOT_PICKAXE, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeAxeWithBlock(AetherItems.SKYROOT_AXE, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeShovelWithBlock(AetherItems.SKYROOT_SHOVEL, AetherBlocks.SKYROOT_PLANKS).build(consumer);

        makePickaxeWithBlock(AetherItems.HOLYSTONE_PICKAXE, AetherBlocks.HOLYSTONE).build(consumer);
        makeAxeWithBlock(AetherItems.HOLYSTONE_AXE, AetherBlocks.HOLYSTONE).build(consumer);
        makeShovelWithBlock(AetherItems.HOLYSTONE_SHOVEL, AetherBlocks.HOLYSTONE).build(consumer);

        makePickaxe(AetherItems.ZANITE_PICKAXE, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeAxe(AetherItems.ZANITE_AXE, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeShovel(AetherItems.ZANITE_SHOVEL, AetherItems.ZANITE_GEMSTONE).build(consumer);

        makePickaxeWithBlock(AetherItems.GRAVITITE_PICKAXE, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);
        makeAxeWithBlock(AetherItems.GRAVITITE_AXE, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);
        makeShovelWithBlock(AetherItems.GRAVITITE_SHOVEL, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);

        makeSwordWithBlock(AetherItems.SKYROOT_SWORD, AetherBlocks.SKYROOT_PLANKS).build(consumer);
        makeSwordWithBlock(AetherItems.HOLYSTONE_SWORD, AetherBlocks.HOLYSTONE).build(consumer);
        makeSword(AetherItems.ZANITE_SWORD, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeSwordWithBlock(AetherItems.GRAVITITE_SWORD, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherItems.GOLDEN_DART.get(), 4)
                .patternLine("F")
                .patternLine("S")
                .patternLine("G")
                .key('F', Tags.Items.FEATHERS)
                .key('S', AetherTags.Items.SKYROOT_STICKS)
                .key('G', AetherItems.GOLDEN_AMBER.get())
                .addCriterion("has_feather", hasItem(Tags.Items.FEATHERS))
                .addCriterion("has_amber", hasItem(AetherItems.GOLDEN_AMBER.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherItems.GOLDEN_DART_SHOOTER.get(), 1)
                .patternLine("S")
                .patternLine("S")
                .patternLine("G")
                .key('S', AetherBlocks.SKYROOT_PLANKS.get())
                .key('G', AetherItems.GOLDEN_AMBER.get())
                .addCriterion("has_skyroot", hasItem(AetherBlocks.SKYROOT_PLANKS.get()))
                .build(consumer);

        makeHelmet(AetherItems.ZANITE_HELMET, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeChestplate(AetherItems.ZANITE_CHESTPLATE, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeLeggings(AetherItems.ZANITE_LEGGINGS, AetherItems.ZANITE_GEMSTONE).build(consumer);
        makeBoots(AetherItems.ZANITE_BOOTS, AetherItems.ZANITE_GEMSTONE).build(consumer);

        makeHelmetWithBlock(AetherItems.GRAVITITE_HELMET, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);
        makeChestplateWithBlock(AetherItems.GRAVITITE_CHESTPLATE, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);
        makeLeggingsWithBlock(AetherItems.GRAVITITE_LEGGINGS, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);
        makeBootsWithBlock(AetherItems.GRAVITITE_BOOTS, AetherBlocks.ENCHANTED_GRAVITITE).build(consumer);

        makeRing(AetherItems.IRON_RING, Items.IRON_INGOT).build(consumer);
        makeRing(AetherItems.GOLD_RING, Items.GOLD_INGOT).build(consumer);
        makeRing(AetherItems.ZANITE_RING, AetherItems.ZANITE_GEMSTONE.get()).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(AetherItems.SKYROOT_STICK.get(), 1)
                .patternLine("S")
                .patternLine("S")
                .key('S', AetherBlocks.SKYROOT_PLANKS.get())
                .addCriterion("has_skyroot", hasItem(AetherBlocks.SKYROOT_PLANKS.get()))
                .build(consumer);


        ShapedRecipeBuilder.shapedRecipe(Items.SADDLE, 1)
                .patternLine("LLL")
                .patternLine("LSL")
                .key('L', Items.LEATHER)
                .key('S', Items.STRING)
                .addCriterion("has_leather", hasItem(Items.LEATHER))
                .build(consumer, name("aether_saddle"));

        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2)
                .patternLine("SS ")
                .patternLine("SB ")
                .patternLine("  S")
                .key('B', AetherItems.SWET_BALL.get())
                .key('S', Tags.Items.STRING)
                .addCriterion("has_swet_ball", hasItem(AetherItems.SWET_BALL.get()))
                .setGroup("lead")
                .build(consumer, name("swet_lead"));


        ShapedRecipeBuilder.shapedRecipe(Blocks.BARREL, 1)
                .patternLine("SHS")
                .patternLine("S S")
                .patternLine("SHS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('H', ItemTags.WOODEN_SLABS)
                .addCriterion("has_skyroot", hasItem(AetherBlocks.SKYROOT_PLANKS.get()))
                .addCriterion("has_skyroot_slab", hasItem(AetherBlocks.SKYROOT_SLAB.get()))
                .setGroup("barrel")
                .build(consumer, name("skyroot_barrel"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.BEEHIVE, 1)
                .patternLine("SSS")
                .patternLine("CCC")
                .patternLine("SSS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('C', Items.HONEYCOMB)
                .addCriterion("has_honeycomb", hasItem(Items.HONEYCOMB))
                .setGroup("beehive")
                .build(consumer, name("skyroot_beehive"));

        ShapedRecipeBuilder.shapedRecipe(Items.BOWL, 4)
                .patternLine("S S")
                .patternLine(" S ")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_brown_mushroom", hasItem(Blocks.BROWN_MUSHROOM))
                .addCriterion("has_red_mushroom", hasItem(Blocks.RED_MUSHROOM))
                .addCriterion("has_mushroom_stew", hasItem(Items.MUSHROOM_STEW))
                .setGroup("bowl")
                .build(consumer, name("skyroot_bowl"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.CARTOGRAPHY_TABLE, 1)
                .patternLine("PP")
                .patternLine("SS")
                .patternLine("SS")
                .key('P', Items.PAPER)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .setGroup("cartography_table")
                .build(consumer, name("skyroot_cartography_table"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.CHEST, 1)
                .patternLine("SSS")
                .patternLine("S S")
                .patternLine("SSS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_lots_of_items", new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.atLeast(10), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, new ItemPredicate[0]))
                .setGroup("chest")
                .build(consumer, name("skyroot_chest"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.CRAFTING_TABLE, 1)
                .patternLine("SS")
                .patternLine("SS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_skyroot", hasItem(AetherBlocks.SKYROOT_PLANKS.get()))
                .setGroup("crafting_table")
                .build(consumer, name("skyroot_crafting_table"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.FLETCHING_TABLE, 1)
                .patternLine("FF")
                .patternLine("SS")
                .patternLine("SS")
                .key('F', Items.FLINT)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_flint", hasItem(Items.FLINT))
                .setGroup("fletching_table")
                .build(consumer, name("skyroot_fletching_table"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.GRINDSTONE, 1)
                .patternLine("THT")
                .patternLine("S S")
                .key('T', Tags.Items.RODS_WOODEN)
                .key('H', Blocks.STONE_SLAB)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_stone_slab", hasItem(Blocks.STONE_SLAB))
                .setGroup("grindstone")
                .build(consumer, name("skyroot_grindstone"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.JUKEBOX, 1)
                .patternLine("SSS")
                .patternLine("SDS")
                .patternLine("SSS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('D', Tags.Items.GEMS_DIAMOND)
                .addCriterion("has_diamond", hasItem(Tags.Items.GEMS_DIAMOND))
                .setGroup("jukebox")
                .build(consumer, name("skyroot_jukebox"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.LOOM, 1)
                .patternLine("TT")
                .patternLine("SS")
                .key('T', Tags.Items.STRING)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .setGroup("loom")
                .build(consumer, name("skyroot_loom"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.NOTE_BLOCK, 1)
                .patternLine("SSS")
                .patternLine("SRS")
                .patternLine("SSS")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Tags.Items.DUSTS_REDSTONE))
                .setGroup("note_block")
                .build(consumer, name("skyroot_note_block"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.PISTON, 1)
                .patternLine("SSS")
                .patternLine("CIC")
                .patternLine("CRC")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('C', Blocks.COBBLESTONE)
                .key('I', Tags.Items.INGOTS_IRON)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_redstone", hasItem(Tags.Items.DUSTS_REDSTONE))
                .setGroup("piston")
                .build(consumer, name("skyroot_piston"));

        ShapedRecipeBuilder.shapedRecipe(Items.SHIELD, 1)
                .patternLine("SIS")
                .patternLine("SSS")
                .patternLine(" S ")
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .key('I', Tags.Items.INGOTS_IRON)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .setGroup("shield")
                .build(consumer, name("skyroot_shield"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.SMITHING_TABLE, 1)
                .patternLine("II")
                .patternLine("SS")
                .patternLine("SS")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .setGroup("smithing_table")
                .build(consumer, name("skyroot_smithing_table"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.TRIPWIRE_HOOK, 2)
                .patternLine("I")
                .patternLine("T")
                .patternLine("S")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('T', Tags.Items.RODS_WOODEN)
                .key('S', AetherTags.Items.PLANKS_CRAFTING)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .setGroup("tripwire_hook")
                .build(consumer, name("skyroot_tripwire_hook"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.BREWING_STAND, 1)
                .patternLine(" B ")
                .patternLine("HHH")
                .key('B', Tags.Items.RODS_BLAZE)
                .key('H', AetherTags.Items.STONE_CRAFTING)
                .addCriterion("has_blaze_rod", hasItem(Items.BLAZE_ROD))
                .setGroup("brewing_stand")
                .build(consumer, name("holystone_brewing_stand"));

        ShapedRecipeBuilder.shapedRecipe(Blocks.FURNACE, 1)
                .patternLine("HHH")
                .patternLine("H H")
                .patternLine("HHH")
                .key('H', AetherTags.Items.STONE_CRAFTING)
                .addCriterion("has_holystone", hasItem(AetherBlocks.HOLYSTONE.get()))
                .setGroup("furnace")
                .build(consumer, name("holystone_furnace"));

        smeltingRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).build(consumer, name("smelt_ambrosium"));
        blastingRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).build(consumer, name("blast_ambrosium"));

        smeltingRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).build(consumer, name("smelt_zanite"));
        blastingRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).build(consumer, name("blast_zanite"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICKS.get()).build(consumer, name("holystone_brick_stonecutting"));

        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_WALL.get()).build(consumer, name("carved_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_STAIRS.get()).build(consumer, name("carved_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.CARVED_STONE, AetherBlocks.CARVED_SLAB.get(), 2).build(consumer, name("carved_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_WALL.get()).build(consumer, name("angelic_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_STAIRS.get()).build(consumer, name("angelic_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.ANGELIC_STONE, AetherBlocks.ANGELIC_SLAB.get(), 2).build(consumer, name("angelic_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_WALL.get()).build(consumer, name("hellfire_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_STAIRS.get()).build(consumer, name("hellfire_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HELLFIRE_STONE, AetherBlocks.HELLFIRE_SLAB.get(), 2).build(consumer, name("hellfire_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_WALL.get()).build(consumer, name("holystone_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_STAIRS.get()).build(consumer, name("holystone_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_SLAB.get(), 2).build(consumer, name("holystone_slab_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_WALL.get()).build(consumer, name("holystone_to_holystone_brick_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get()).build(consumer, name("holystone_to_holystone_brick_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), 2).build(consumer, name("holystone_to_holystone_brick_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_WALL.get()).build(consumer, name("mossy_holystone_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get()).build(consumer, name("mossy_holystone_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.MOSSY_HOLYSTONE, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), 2).build(consumer, name("mossy_holystone_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_WALL.get()).build(consumer, name("holystone_brick_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get()).build(consumer, name("holystone_brick_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.HOLYSTONE_BRICKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), 2).build(consumer, name("holystone_brick_slab_stonecutting"));

        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_WALL.get()).build(consumer, name("aerogel_wall_stonecutting"));
        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_STAIRS.get()).build(consumer, name("aerogel_stairs_stonecutting"));
        stonecuttingRecipe(AetherBlocks.AEROGEL, AetherBlocks.AEROGEL_SLAB.get(), 2).build(consumer, name("aerogel_slab_stonecutting"));

        //TODO: Enchanting recipes.
        //smelting recipes for ambrosium ore and zanite ore -> materials. also for blast furnace.
        //stonecutter recipes
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(Aether.MODID, name);
    }
}
