package com.gildedgames.aether.data.generators;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.api.AetherMoaTypes;
import com.gildedgames.aether.data.providers.AetherRecipeProvider;
import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.entity.AetherEntityTypes;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.AetherTags;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Consumer;

public class AetherRecipeData extends AetherRecipeProvider {
    public AetherRecipeData(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherBlocks.HOLYSTONE.get())
                .requires(Blocks.VINE)
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer, name("mossy_holystone_with_vine"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherBlocks.HOLYSTONE.get())
                .requires(Blocks.MOSS_BLOCK)
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer, name("mossy_holystone_with_moss"));

        woodFromLogs(consumer, AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.SKYROOT_LOG.get());
        woodFromLogs(consumer, AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.GOLDEN_OAK_LOG.get());
        woodFromLogs(consumer, AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get());
        planksFromLogs(consumer, AetherBlocks.SKYROOT_PLANKS.get(), AetherTags.Items.CRAFTS_SKYROOT_PLANKS, 4);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICKS.get(), 4)
                .define('#', AetherBlocks.HOLYSTONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, AetherItems.AMBROSIUM_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AMBROSIUM_BLOCK.get(), "ambrosium_shard_from_ambrosium_block", "ambrosium_shard");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer, RecipeCategory.MISC, AetherItems.ZANITE_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ZANITE_BLOCK.get(), "zanite_gemstone_from_zanite_block", "zanite_gemstone");

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.QUICKSOIL_GLASS_PANE.get(), 16)
                .define('#', AetherBlocks.QUICKSOIL_GLASS.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(AetherBlocks.QUICKSOIL_GLASS.get()), has(AetherBlocks.QUICKSOIL_GLASS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.ALTAR.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('Z', AetherItems.ZANITE_GEMSTONE.get())
                .pattern("HHH")
                .pattern("HZH")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.FREEZER.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('I', AetherBlocks.ICESTONE.get())
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .pattern("HHH")
                .pattern("HIH")
                .pattern("PPP")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.INCUBATOR.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('T', AetherBlocks.AMBROSIUM_TORCH.get())
                .pattern("HHH")
                .pattern("HTH")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.AMBROSIUM_TORCH.get(), 4)
                .define('A', AetherItems.AMBROSIUM_SHARD.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("A")
                .pattern("/")
                .unlockedBy(getHasName(AetherItems.HOLYSTONE_PICKAXE.get()), has(AetherItems.HOLYSTONE_PICKAXE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.SKYROOT_SIGN.get(), 3)
                .group("sign")
                .define('P', AetherBlocks.SKYROOT_PLANKS.get().asItem())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        fence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        fenceGate(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS).save(consumer);

        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.ICESTONE_WALL.get(), AetherBlocks.ICESTONE.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        wall(consumer, RecipeCategory.DECORATIONS, AetherBlocks.AEROGEL_WALL.get(), AetherBlocks.AEROGEL.get());

        stairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS).save(consumer);
        stairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE).save(consumer);
        stairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE).save(consumer);
        stairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE).save(consumer);
        stairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE).save(consumer);
        stairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE).save(consumer);
        stairs(AetherBlocks.ICESTONE_STAIRS, AetherBlocks.ICESTONE).save(consumer);
        stairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS).save(consumer);
        stairs(AetherBlocks.AEROGEL_STAIRS, AetherBlocks.AEROGEL).save(consumer);

        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SKYROOT_SLAB.get(), AetherBlocks.SKYROOT_PLANKS.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_SLAB.get(), AetherBlocks.CARVED_STONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_SLAB.get(), AetherBlocks.ANGELIC_STONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_SLAB.get(), AetherBlocks.HELLFIRE_STONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_SLAB.get(), AetherBlocks.HOLYSTONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        slab(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_SLAB.get(), AetherBlocks.AEROGEL.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SKYROOT_BOOKSHELF.get(), 1)
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .define('B', Items.BOOK)
                .pattern("PPP")
                .pattern("BBB")
                .pattern("PPP")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, AetherBlocks.SKYROOT_BED.get(), 1)
                .define('W', ItemTags.WOOL)
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .pattern("WWW")
                .pattern("PPP")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PURPLE_DYE)
                .group("purple_dye")
                .requires(AetherBlocks.PURPLE_FLOWER.get())
                .unlockedBy(getHasName(AetherBlocks.PURPLE_FLOWER.get()), has(AetherBlocks.PURPLE_FLOWER.get()))
                .save(consumer, name("flower_to_purple_dye"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.WHITE_DYE)
                .group("white_dye")
                .requires(AetherBlocks.WHITE_FLOWER.get())
                .unlockedBy(getHasName(AetherBlocks.WHITE_FLOWER.get()), has(AetherBlocks.WHITE_FLOWER.get()))
                .save(consumer, name("flower_to_white_dye"));


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

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AetherItems.GOLDEN_DART.get(), 4)
                .define('F', Tags.Items.FEATHERS)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .pattern("F")
                .pattern("/")
                .pattern("G")
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .unlockedBy(getHasName(AetherItems.GOLDEN_AMBER.get()), has(AetherItems.GOLDEN_AMBER.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AetherItems.GOLDEN_DART_SHOOTER.get(), 1)
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .pattern("P")
                .pattern("P")
                .pattern("G")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AetherItems.POISON_DART.get(), 8)
                .define('D', AetherItems.GOLDEN_DART.get())
                .define('B', AetherItems.SKYROOT_POISON_BUCKET.get())
                .pattern("DDD")
                .pattern("DBD")
                .pattern("DDD")
                .unlockedBy(getHasName(AetherItems.GOLDEN_DART.get()), has(AetherItems.GOLDEN_DART.get()))
                .unlockedBy(getHasName(AetherItems.SKYROOT_POISON_BUCKET.get()), has(AetherItems.SKYROOT_POISON_BUCKET.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.COMBAT, AetherItems.POISON_DART_SHOOTER.get(), 1)
                .requires(AetherItems.GOLDEN_DART_SHOOTER.get())
                .requires(AetherItems.AECHOR_PETAL.get())
                .unlockedBy(getHasName(AetherItems.GOLDEN_DART_SHOOTER.get()), has(AetherItems.GOLDEN_DART_SHOOTER.get()))
                .unlockedBy(getHasName(AetherItems.AECHOR_PETAL.get()), has(AetherItems.AECHOR_PETAL.get()))
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
        makeRing(AetherItems.GOLDEN_RING, Items.GOLD_INGOT).save(consumer);
        makeRing(AetherItems.ZANITE_RING, AetherItems.ZANITE_GEMSTONE.get()).save(consumer);

        makePendant(AetherItems.IRON_PENDANT, Items.IRON_INGOT).save(consumer);
        makePendant(AetherItems.GOLDEN_PENDANT, Items.GOLD_INGOT).save(consumer);
        makePendant(AetherItems.ZANITE_PENDANT, AetherItems.ZANITE_GEMSTONE.get()).save(consumer);

        makeCape(AetherItems.RED_CAPE, Blocks.RED_WOOL.asItem()).save(consumer);
        makeCape(AetherItems.BLUE_CAPE, Blocks.BLUE_WOOL.asItem()).save(consumer);
        makeCape(AetherItems.YELLOW_CAPE, Blocks.YELLOW_WOOL.asItem()).save(consumer);
        makeCape(AetherItems.WHITE_CAPE, Blocks.WHITE_WOOL.asItem()).save(consumer);

        makeGlovesWithTag(AetherItems.LEATHER_GLOVES, Tags.Items.LEATHER, "leather").save(consumer);
        makeGlovesWithTag(AetherItems.IRON_GLOVES, Tags.Items.INGOTS_IRON, "iron").save(consumer);
        makeGlovesWithTag(AetherItems.GOLDEN_GLOVES, Tags.Items.INGOTS_GOLD, "gold").save(consumer);
        makeGlovesWithTag(AetherItems.DIAMOND_GLOVES, Tags.Items.GEMS_DIAMOND, "diamond").save(consumer);
        netheriteSmithing(consumer, AetherItems.DIAMOND_GLOVES.get(), RecipeCategory.COMBAT, AetherItems.NETHERITE_GLOVES.get());
        makeGloves(AetherItems.ZANITE_GLOVES, AetherItems.ZANITE_GEMSTONE).save(consumer);
        makeGlovesWithBlock(AetherItems.GRAVITITE_GLOVES, AetherBlocks.ENCHANTED_GRAVITITE).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherItems.SKYROOT_STICK.get(), 4)
                .define('#', AetherBlocks.SKYROOT_PLANKS.get())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, AetherItems.SKYROOT_BUCKET.get(), 1)
                .define('#', AetherBlocks.SKYROOT_PLANKS.get())
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer);

        twoByTwoPacker(consumer, RecipeCategory.TOOLS, AetherItems.COLD_PARACHUTE.get(), AetherBlocks.COLD_AERCLOUD.get());
        twoByTwoPacker(consumer, RecipeCategory.TOOLS, AetherItems.GOLDEN_PARACHUTE.get(), AetherBlocks.GOLDEN_AERCLOUD.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AetherItems.NATURE_STAFF.get(), 1)
                .define('Z', AetherItems.ZANITE_GEMSTONE.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("Z")
                .pattern("/")
                .unlockedBy(getHasName(AetherItems.ZANITE_GEMSTONE.get()), has(AetherItems.ZANITE_GEMSTONE.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AetherItems.BOOK_OF_LORE.get())
                .group("book_of_lore")
                .requires(Items.BOOK)
                .requires(AetherTags.Items.BOOK_OF_LORE_MATERIALS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(consumer);

        woodenBoat(consumer, AetherItems.SKYROOT_BOAT.get(), AetherBlocks.SKYROOT_PLANKS.get());
        chestBoat(consumer, AetherItems.SKYROOT_CHEST_BOAT.get(), AetherItems.SKYROOT_BOAT.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.SADDLE, 1)
                .define('L', Items.LEATHER)
                .define('S', Items.STRING)
                .pattern("LLL")
                .pattern("LSL")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(consumer, name("aether_saddle"));

        doorBuilder(AetherBlocks.SKYROOT_DOOR.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_door").save(consumer);
        trapdoorBuilder(AetherBlocks.SKYROOT_TRAPDOOR.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_trapdoor").save(consumer);
        buttonBuilder(AetherBlocks.SKYROOT_BUTTON.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_button").save(consumer);
        buttonBuilder(AetherBlocks.HOLYSTONE_BUTTON.get(), Ingredient.of(AetherBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get())).save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherBlocks.SKYROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_pressure_plate").save(consumer);
        pressurePlateBuilder(RecipeCategory.REDSTONE, AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get(), Ingredient.of(AetherBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get())).save(consumer);

        // The group IDs below match the IDs of the respective vanilla recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, Items.LEAD, 2)
                .group("minecraft:lead")
                .define('B', AetherItems.SWET_BALL.get())
                .define('S', Tags.Items.STRING)
                .pattern("SS ")
                .pattern("SB ")
                .pattern("  S")
                .unlockedBy(getHasName(AetherItems.SWET_BALL.get()), has(AetherItems.SWET_BALL.get()))
                .save(consumer, name("swet_lead"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.BARREL, 1)
                .group("minecraft:barrel")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('H', ItemTags.WOODEN_SLABS)
                .pattern("PHP")
                .pattern("P P")
                .pattern("PHP")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_SLAB.get()), has(AetherBlocks.SKYROOT_SLAB.get()))
                .save(consumer, name("skyroot_barrel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.BEEHIVE, 1)
                .group("minecraft:beehive")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Items.HONEYCOMB)
                .pattern("PPP")
                .pattern("CCC")
                .pattern("PPP")
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(consumer, name("skyroot_beehive"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.CARTOGRAPHY_TABLE, 1)
                .group("minecraft:cartography_table")
                .define('#', Items.PAPER)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("##")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(consumer, name("skyroot_cartography_table"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.CHEST, 1)
                .group("minecraft:chest")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("PPP")
                .pattern("P P")
                .pattern("PPP")
                .unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]))
                .save(consumer, name("skyroot_chest"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.CRAFTING_TABLE, 1)
                .group("minecraft:crafting_table")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("PP")
                .pattern("PP")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(consumer, name("skyroot_crafting_table"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.FLETCHING_TABLE, 1)
                .group("minecraft:fletching_table")
                .define('F', Items.FLINT)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("FF")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy(getHasName(Items.FLINT), has(Items.FLINT))
                .save(consumer, name("skyroot_fletching_table"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.GRINDSTONE, 1)
                .group("minecraft:grindstone")
                .define('/', Tags.Items.RODS_WOODEN)
                .define('H', Blocks.STONE_SLAB)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("/H/")
                .pattern("P P")
                .unlockedBy(getHasName(Blocks.STONE_SLAB), has(Blocks.STONE_SLAB))
                .save(consumer, name("skyroot_grindstone"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .pattern("PPP")
                .pattern("PDP")
                .pattern("PPP")
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(consumer, name("skyroot_jukebox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', ItemTags.PLANKS)
                .define('G', AetherBlocks.ENCHANTED_GRAVITITE.get())
                .pattern("PPP")
                .pattern("PGP")
                .pattern("PPP")
                .unlockedBy(getHasName(AetherBlocks.ENCHANTED_GRAVITITE.get()), has(AetherBlocks.ENCHANTED_GRAVITITE.get()))
                .save(consumer, name("gravitite_jukebox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('G', AetherBlocks.ENCHANTED_GRAVITITE.get())
                .pattern("PPP")
                .pattern("PGP")
                .pattern("PPP")
                .unlockedBy(getHasName(AetherBlocks.ENCHANTED_GRAVITITE.get()), has(AetherBlocks.ENCHANTED_GRAVITITE.get()))
                .save(consumer, name("skyroot_gravitite_jukebox"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.LOOM, 1)
                .group("minecraft:loom")
                .define('T', Tags.Items.STRING)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("TT")
                .pattern("PP")
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .save(consumer, name("skyroot_loom"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.NOTE_BLOCK, 1)
                .group("minecraft:note_block")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("PPP")
                .pattern("PRP")
                .pattern("PPP")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, name("skyroot_note_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.PISTON, 1)
                .group("minecraft:piston")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Blocks.COBBLESTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("PPP")
                .pattern("CIC")
                .pattern("CRC")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(consumer, name("skyroot_piston"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, Items.SHIELD, 1)
                .group("minecraft:shield")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("PIP")
                .pattern("PPP")
                .pattern(" P ")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(consumer, name("skyroot_shield"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Blocks.SMITHING_TABLE, 1)
                .group("minecraft:smithing_table")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("II")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(consumer, name("skyroot_smithing_table"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, Blocks.TRIPWIRE_HOOK, 2)
                .group("minecraft:tripwire_hook")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('/', Tags.Items.RODS_WOODEN)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("I")
                .pattern("/")
                .pattern("P")
                .unlockedBy("has_string", has(Tags.Items.STRING))
                .save(consumer, name("skyroot_tripwire_hook"));

        smeltingOreRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, name("ambrosium_shard_from_smelting"));
        blastingOreRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(consumer, name("ambrosium_shard_from_blasting"));

        smeltingOreRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, name("zanite_gemstone_from_smelting"));
        blastingOreRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(consumer, name("zanite_gemstone_from_blasting"));

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_STAIRS.get(), AetherBlocks.CARVED_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_SLAB.get(), AetherBlocks.CARVED_STONE.get(), 2);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SENTRY_STONE.get(), AetherBlocks.CARVED_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_STONE.get(), AetherBlocks.SENTRY_STONE.get());

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STAIRS.get(), AetherBlocks.ANGELIC_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_SLAB.get(), AetherBlocks.ANGELIC_STONE.get(), 2);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR.get(), AetherBlocks.ANGELIC_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR_TOP.get(), AetherBlocks.ANGELIC_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.PILLAR.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.PILLAR_TOP.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR_TOP.get(), AetherBlocks.PILLAR.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR.get(), AetherBlocks.PILLAR_TOP.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.LIGHT_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_STAIRS.get(), AetherBlocks.HELLFIRE_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_SLAB.get(), AetherBlocks.HELLFIRE_STONE.get(), 2);
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.LIGHT_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_STAIRS.get(), AetherBlocks.HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_SLAB.get(), AetherBlocks.HOLYSTONE.get(), 2);

        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICKS.get(), AetherBlocks.HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherBlocks.HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE.get(), 2);

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), 2);

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), 2);

        stonecutterResultFromBase(consumer, RecipeCategory.DECORATIONS, AetherBlocks.AEROGEL_WALL.get(), AetherBlocks.AEROGEL.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_STAIRS.get(), AetherBlocks.AEROGEL.get());
        stonecutterResultFromBase(consumer, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_SLAB.get(), AetherBlocks.AEROGEL.get(), 2);


        repairingRecipe(RecipeCategory.TOOLS, Items.FISHING_ROD, 600).save(consumer, name("fishing_rod_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.BOW, 4000).save(consumer, name("bow_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.SHIELD, 3000).save(consumer, name("shield_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_PICKAXE.get(), 225).group("altar_pickaxe_repair").save(consumer, name("skyroot_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_AXE.get(), 225).group("altar_axe_repair").save(consumer, name("skyroot_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_SHOVEL.get(), 225).group("altar_shovel_repair").save(consumer, name("skyroot_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_HOE.get(), 225).group("altar_hoe_repair").save(consumer, name("skyroot_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_PICKAXE.get(), 550).group("altar_pickaxe_repair").save(consumer, name("holystone_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_AXE.get(), 550).group("altar_axe_repair").save(consumer, name("holystone_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_SHOVEL.get(), 550).group("altar_shovel_repair").save(consumer, name("holystone_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_HOE.get(), 550).group("altar_hoe_repair").save(consumer, name("holystone_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_PICKAXE.get(), 2250).group("altar_pickaxe_repair").save(consumer, name("zanite_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_AXE.get(), 2250).group("altar_axe_repair").save(consumer, name("zanite_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_SHOVEL.get(), 2250).group("altar_shovel_repair").save(consumer, name("zanite_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_HOE.get(), 2250).group("altar_hoe_repair").save(consumer, name("zanite_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_PICKAXE.get(), 5500).group("altar_pickaxe_repair").save(consumer, name("gravitite_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_AXE.get(), 5500).group("altar_axe_repair").save(consumer, name("gravitite_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_SHOVEL.get(), 5500).group("altar_shovel_repair").save(consumer, name("gravitite_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_HOE.get(), 5500).group("altar_hoe_repair").save(consumer, name("gravitite_hoe_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, AetherItems.SKYROOT_SWORD.get(), 225).group("altar_sword_repair").save(consumer, name("skyroot_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.HOLYSTONE_SWORD.get(), 550).group("altar_sword_repair").save(consumer, name("holystone_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_SWORD.get(), 2250).group("altar_sword_repair").save(consumer, name("zanite_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_SWORD.get(), 5500).group("altar_sword_repair").save(consumer, name("gravitite_sword_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_HELMET.get(), 6000).group("altar_helmet_repair").save(consumer, name("zanite_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_CHESTPLATE.get(), 6000).group("altar_chestplate_repair").save(consumer, name("zanite_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_LEGGINGS.get(), 6000).group("altar_leggings_repair").save(consumer, name("zanite_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_BOOTS.get(), 6000).group("altar_boots_repair").save(consumer, name("zanite_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_GLOVES.get(), 6000).group("altar_gloves_repair").save(consumer, name("zanite_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_HELMET.get(), 13000).group("altar_helmet_repair").save(consumer, name("gravitite_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_CHESTPLATE.get(), 13000).group("altar_chestplate_repair").save(consumer, name("gravitite_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_LEGGINGS.get(), 13000).group("altar_leggings_repair").save(consumer, name("gravitite_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_BOOTS.get(), 13000).group("altar_boots_repair").save(consumer, name("gravitite_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_GLOVES.get(), 13000).group("altar_gloves_repair").save(consumer, name("gravitite_gloves_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_PICKAXE, 225).group("altar_pickaxe_repair").save(consumer, name("wooden_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_AXE, 225).group("altar_axe_repair").save(consumer, name("wooden_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_SHOVEL, 225).group("altar_shovel_repair").save(consumer, name("wooden_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_HOE, 225).group("altar_hoe_repair").save(consumer, name("wooden_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.STONE_PICKAXE, 550).group("altar_pickaxe_repair").save(consumer, name("stone_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.STONE_AXE, 550).group("altar_axe_repair").save(consumer, name("stone_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.STONE_SHOVEL, 550).group("altar_shovel_repair").save(consumer, name("stone_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.STONE_HOE, 550).group("altar_hoe_repair").save(consumer, name("stone_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.IRON_PICKAXE, 2250).group("altar_pickaxe_repair").save(consumer, name("iron_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.IRON_AXE, 2250).group("altar_axe_repair").save(consumer, name("iron_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.IRON_SHOVEL, 2250).group("altar_shovel_repair").save(consumer, name("iron_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.IRON_HOE, 2250).group("altar_hoe_repair").save(consumer, name("iron_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_PICKAXE, 1075).group("altar_pickaxe_repair").save(consumer, name("golden_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_AXE, 1075).group("altar_axe_repair").save(consumer, name("golden_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_SHOVEL, 1075).group("altar_shovel_repair").save(consumer, name("golden_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_HOE, 1075).group("altar_hoe_repair").save(consumer, name("golden_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE, 5500).group("altar_pickaxe_repair").save(consumer, name("diamond_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_AXE, 5500).group("altar_axe_repair").save(consumer, name("diamond_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL, 5500).group("altar_shovel_repair").save(consumer, name("diamond_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_HOE, 5500).group("altar_hoe_repair").save(consumer, name("diamond_hoe_repairing"));

        repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_PICKAXE, 15000).group("altar_pickaxe_repair").save(consumer, name("netherite_pickaxe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_AXE, 15000).group("altar_axe_repair").save(consumer, name("netherite_axe_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_SHOVEL, 15000).group("altar_shovel_repair").save(consumer, name("netherite_shovel_repairing"));
        repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_HOE, 15000).group("altar_hoe_repair").save(consumer, name("netherite_hoe_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.WOODEN_SWORD, 225).group("altar_sword_repair").save(consumer, name("wooden_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.STONE_SWORD, 550).group("altar_sword_repair").save(consumer, name("stone_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.IRON_SWORD, 2250).group("altar_sword_repair").save(consumer, name("iron_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_SWORD, 1075).group("altar_sword_repair").save(consumer, name("golden_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_SWORD, 5500).group("altar_sword_repair").save(consumer, name("diamond_sword_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_SWORD, 15000).group("altar_sword_repair").save(consumer, name("netherite_sword_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_HELMET, 550).group("altar_helmet_repair").save(consumer, name("leather_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_CHESTPLATE, 550).group("altar_chestplate_repair").save(consumer, name("leather_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_LEGGINGS, 550).group("altar_leggings_repair").save(consumer, name("leather_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_BOOTS, 550).group("altar_boots_repair").save(consumer, name("leather_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.LEATHER_GLOVES.get(), 550).group("altar_gloves_repair").save(consumer, name("leather_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.IRON_HELMET, 6000).group("altar_helmet_repair").save(consumer, name("iron_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.IRON_CHESTPLATE, 6000).group("altar_chestplate_repair").save(consumer, name("iron_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.IRON_LEGGINGS, 6000).group("altar_leggings_repair").save(consumer, name("iron_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.IRON_BOOTS, 6000).group("altar_boots_repair").save(consumer, name("iron_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.IRON_GLOVES.get(), 6000).group("altar_gloves_repair").save(consumer, name("iron_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_HELMET, 2250).group("altar_helmet_repair").save(consumer, name("golden_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_CHESTPLATE, 2250).group("altar_chestplate_repair").save(consumer, name("golden_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_LEGGINGS, 2250).group("altar_leggings_repair").save(consumer, name("golden_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_BOOTS, 2250).group("altar_boots_repair").save(consumer, name("golden_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.GOLDEN_GLOVES.get(), 2250).group("altar_gloves_repair").save(consumer, name("golden_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET, 2250).group("altar_helmet_repair").save(consumer, name("chainmail_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE, 2250).group("altar_chestplate_repair").save(consumer, name("chainmail_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS, 2250).group("altar_leggings_repair").save(consumer, name("chainmail_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS, 2250).group("altar_boots_repair").save(consumer, name("chainmail_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.CHAINMAIL_GLOVES.get(), 2250).group("altar_gloves_repair").save(consumer, name("chainmail_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_HELMET, 10000).group("altar_helmet_repair").save(consumer, name("diamond_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE, 10000).group("altar_chestplate_repair").save(consumer, name("diamond_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS, 10000).group("altar_leggings_repair").save(consumer, name("diamond_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS, 10000).group("altar_boots_repair").save(consumer, name("diamond_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.DIAMOND_GLOVES.get(), 10000).group("altar_gloves_repair").save(consumer, name("diamond_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_HELMET, 30000).group("altar_helmet_repair").save(consumer, name("netherite_helmet_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE, 30000).group("altar_chestplate_repair").save(consumer, name("netherite_chestplate_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS, 30000).group("altar_leggings_repair").save(consumer, name("netherite_leggings_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_BOOTS, 30000).group("altar_boots_repair").save(consumer, name("netherite_boots_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.NETHERITE_GLOVES.get(), 30000).group("altar_gloves_repair").save(consumer, name("netherite_gloves_repairing"));

        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_RING.get(), 2250).group("altar_ring_repair").save(consumer, name("zanite_ring_repairing"));
        repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_PENDANT.get(), 2250).group("altar_pendant_repair").save(consumer, name("zanite_pendant_repairing"));

        enchantingRecipe(RecipeCategory.MISC, AetherItems.ENCHANTED_DART.get(), AetherItems.GOLDEN_DART.get(), 0.15F, 250).save(consumer, name("enchanted_dart_enchanting"));
        enchantingRecipe(RecipeCategory.MISC, AetherItems.ENCHANTED_DART_SHOOTER.get(), AetherItems.GOLDEN_DART_SHOOTER.get(), 1.0F, 500).save(consumer, name("enchanted_dart_shooter_enchanting"));

        enchantingRecipe(RecipeCategory.FOOD, AetherItems.HEALING_STONE.get(), AetherBlocks.HOLYSTONE.get(), 0.35F, 750).save(consumer, name("healing_stone_enchanting"));
        enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ENCHANTED_GRAVITITE.get(), AetherBlocks.GRAVITITE_ORE.get(), 1.0F, 1000).save(consumer, name("enchanted_gravitite_enchanting"));
        enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.QUICKSOIL_GLASS.get(), AetherBlocks.QUICKSOIL.get(), 0.1F, 250).save(consumer, name("quicksoil_glass_enchanting"));

        enchantingRecipe(RecipeCategory.FOOD, AetherItems.ENCHANTED_BERRY.get(), AetherItems.BLUE_BERRY.get(), 0.35F, 300).save(consumer, name("enchanted_berry_enchanting"));

        enchantingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_AETHER_TUNE.get(), AetherTags.Items.ACCEPTED_MUSIC_DISCS, 1.0F, 2500, "disc").save(consumer, name("aether_tune_enchanting"));
        hiddenEnchantingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_LEGACY.get(), Items.MUSIC_DISC_CAT, 1.0F, 2500).save(consumer, name("legacy_enchanting"));
        hiddenEnchantingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_CHINCHILLA.get(), Items.MUSIC_DISC_STRAD, 1.0F, 2500).save(consumer, name("chinchilla_enchanting"));

        enchantingRecipe(RecipeCategory.MISC, AetherItems.SKYROOT_REMEDY_BUCKET.get(), AetherItems.SKYROOT_POISON_BUCKET.get(), 0.35F, 1000).save(consumer, name("remedy_bucket_enchanting"));

        freezingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.BLUE_AERCLOUD.get(), AetherBlocks.COLD_AERCLOUD.get(), 0.1F, 100).save(consumer, name("blue_aercloud_freezing"));
        freezingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CRYSTAL_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get(), 0.1F, 150).save(consumer, name("crystal_leaves_freezing"));

        hiddenFreezingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_WELCOMING_SKIES.get(), AetherItems.MUSIC_DISC_ASCENDING_DAWN.get(), 1.0F, 800).save(consumer, name("welcoming_skies_freezing"));

        freezingRecipeWithUnlockTag(RecipeCategory.BUILDING_BLOCKS, Blocks.BLUE_ICE, Blocks.PACKED_ICE, AetherTags.Items.FREEZABLE_BUCKETS, 0.1F, 1000, "water_bucket").save(consumer, name("blue_ice_freezing"));
        freezingRecipeWithUnlockTag(RecipeCategory.BUILDING_BLOCKS, Blocks.PACKED_ICE, Blocks.ICE, AetherTags.Items.FREEZABLE_BUCKETS, 0.1F, 750, "water_bucket").save(consumer, name("packed_ice_freezing"));
        freezingRecipeWithTag(RecipeCategory.BUILDING_BLOCKS, Blocks.ICE, AetherTags.Items.FREEZABLE_BUCKETS, 0.1F, 500, "water_bucket").save(consumer, name("ice_from_bucket_freezing"));
        freezingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.OBSIDIAN, Items.LAVA_BUCKET, 0.1F, 500).save(consumer, name("obsidian_from_bucket_freezing"));

        freezingRecipeWithTag(RecipeCategory.MISC, AetherItems.ICE_RING.get(), AetherTags.Items.FREEZABLE_RINGS, 1.0F, 2500, "ring").save(consumer, name("ice_ring_from_freezing"));

        freezingRecipeWithTag(RecipeCategory.MISC, AetherItems.ICE_PENDANT.get(), AetherTags.Items.FREEZABLE_PENDANTS, 1.0F, 2500, "pendant").save(consumer, name("ice_pendant_from_freezing"));

        moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.BLUE, AetherItems.BLUE_MOA_EGG.get()).save(consumer, name("blue_moa_incubation"));
        moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.WHITE, AetherItems.WHITE_MOA_EGG.get()).save(consumer, name("white_moa_incubation"));
        moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.BLACK, AetherItems.BLACK_MOA_EGG.get()).save(consumer, name("black_moa_incubation"));
        moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.ORANGE, AetherItems.ORANGE_MOA_EGG.get()).save(consumer, name("orange_moa_incubation"));

        ambrosiumEnchanting(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_GRASS_BLOCK.get()).save(consumer, name("ambrosium_enchant_aether_grass_to_enchanted_aether_grass"));

        swetBallConversion(Blocks.GRASS_BLOCK, Blocks.DIRT).save(consumer, name("swet_ball_dirt_to_grass"));
        swetBallConversion(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get()).save(consumer, name("swet_ball_aether_dirt_to_aether_grass"));
        swetBallConversionTag(Blocks.MYCELIUM, Blocks.DIRT, AetherTags.Biomes.MYCELIUM_CONVERSION).save(consumer, name("swet_ball_dirt_to_mycelium"));
        swetBallConversionTag(Blocks.PODZOL, Blocks.GRASS_BLOCK, AetherTags.Biomes.PODZOL_CONVERSION).save(consumer, name("swet_ball_grass_to_podzol"));
        swetBallConversionTag(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, AetherTags.Biomes.CRIMSON_NYLIUM_CONVERSION).save(consumer, name("swet_ball_netherrack_to_crimson_nylium"));
        swetBallConversionTag(Blocks.WARPED_NYLIUM, Blocks.NETHERRACK, AetherTags.Biomes.WARPED_NYLIUM_CONVERSION).save(consumer, name("swet_ball_netherrack_to_warped_nylium"));

        icestoneFreezable(Blocks.ICE, Blocks.WATER).save(consumer, name("icestone_freeze_water"));
        icestoneFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(consumer, name("icestone_freeze_lava"));

        conditionalAccessoryFreezing(accessoryFreezable(Blocks.FROSTED_ICE, Blocks.WATER), accessoryFreezable(Blocks.ICE, Blocks.WATER)).build(consumer, name("accessory_freeze_water"));
        conditionalAccessoryFreezing(accessoryFreezable(AetherBlocks.UNSTABLE_OBSIDIAN.get(), Blocks.LAVA), accessoryFreezable(Blocks.OBSIDIAN, Blocks.LAVA)).build(consumer, name("accessory_freeze_lava"));

        convertPlacement(AetherBlocks.AEROGEL.get(), Blocks.LAVA, AetherTags.Biomes.ULTRACOLD).save(consumer, name("aerogel_conversion"));
        convertPlacementWithProperties(Blocks.CAMPFIRE, Map.of(CampfireBlock.LIT, false), Blocks.CAMPFIRE, Map.of(CampfireBlock.LIT, true), AetherTags.Biomes.ULTRACOLD).save(consumer, name("campfire_conversion"));
        convertPlacementWithProperties(Blocks.CANDLE, Map.of(CandleBlock.LIT, false), Blocks.CANDLE, Map.of(CandleBlock.LIT, true), AetherTags.Biomes.ULTRACOLD).save(consumer, name("candle_conversion"));
        convertPlacement(Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, AetherTags.Biomes.ULTRACOLD).save(consumer, name("jack_o_lantern_conversion"));

        banItemPlacementWithBypass(Items.FLINT_AND_STEEL, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(consumer, name("flint_and_steel_item_ban"));
        banItemPlacementWithBypass(Items.FIRE_CHARGE, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(consumer, name("fire_charge_item_ban"));
        banItemPlacement(Items.TORCH, AetherTags.Biomes.ULTRACOLD).save(consumer, name("torch_item_ban"));
        banItemPlacement(Items.LANTERN, AetherTags.Biomes.ULTRACOLD).save(consumer, name("lantern_item_ban"));

        banBlockPlacementWithBypass(Blocks.FIRE, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(consumer, name("fire_block_ban"));
        banBlockPlacement(Blocks.TORCH, AetherTags.Biomes.ULTRACOLD).save(consumer, name("torch_block_ban"));
        banBlockPlacement(Blocks.LANTERN, AetherTags.Biomes.ULTRACOLD).save(consumer, name("lantern_block_ban"));
    }

    private ResourceLocation name(String name) {
        return new ResourceLocation(Aether.MODID, name);
    }
}
