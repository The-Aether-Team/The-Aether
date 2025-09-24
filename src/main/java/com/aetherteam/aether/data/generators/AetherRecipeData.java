package com.aetherteam.aether.data.generators;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherRecipeProvider;
import com.aetherteam.aether.data.resources.registries.AetherMoaTypes;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class AetherRecipeData extends AetherRecipeProvider {
    public AetherRecipeData(RecipeOutput output, HolderLookup.Provider provider) {
        super(output, provider, Aether.MODID);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> getter = this.registries.lookupOrThrow(Registries.ITEM);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherBlocks.HOLYSTONE.get())
                .requires(Blocks.VINE)
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output, this.name("mossy_holystone_with_vine"));
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE.get())
                .group("mossy_holystone")
                .requires(AetherBlocks.HOLYSTONE.get())
                .requires(Blocks.MOSS_BLOCK)
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output, this.name("mossy_holystone_with_moss"));

        this.woodFromLogs(AetherBlocks.SKYROOT_WOOD.get(), AetherBlocks.SKYROOT_LOG.get());
        this.woodFromLogs(AetherBlocks.GOLDEN_OAK_WOOD.get(), AetherBlocks.GOLDEN_OAK_LOG.get());
        this.woodFromLogs(AetherBlocks.STRIPPED_SKYROOT_WOOD.get(), AetherBlocks.STRIPPED_SKYROOT_LOG.get());
        this.planksFromLogs(AetherBlocks.SKYROOT_PLANKS.get(), AetherTags.Items.CRAFTS_SKYROOT_PLANKS, 4);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICKS.get(), 4)
                .define('#', AetherBlocks.HOLYSTONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output);

        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherItems.AMBROSIUM_SHARD.get(), RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AMBROSIUM_BLOCK.get(), "ambrosium_shard_from_ambrosium_block", "ambrosium_shard");
        this.oreBlockStorageRecipesRecipesWithCustomUnpacking(getter, this.output, RecipeCategory.MISC, AetherItems.ZANITE_GEMSTONE.get(), RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ZANITE_BLOCK.get(), "zanite_gemstone_from_zanite_block", "zanite_gemstone");

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.QUICKSOIL_GLASS_PANE.get(), 16)
                .define('#', AetherBlocks.QUICKSOIL_GLASS.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(AetherBlocks.QUICKSOIL_GLASS.get()), has(AetherBlocks.QUICKSOIL_GLASS.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.ALTAR.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('Z', AetherTags.Items.GEMS_ZANITE)
                .pattern("HHH")
                .pattern("HZH")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.FREEZER.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('I', AetherBlocks.ICESTONE.get())
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .pattern("HHH")
                .pattern("HIH")
                .pattern("PPP")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.INCUBATOR.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('T', AetherBlocks.AMBROSIUM_TORCH.get())
                .pattern("HHH")
                .pattern("HTH")
                .pattern("HHH")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.AMBROSIUM_TORCH.get(), 4)
                .define('A', AetherTags.Items.GEMS_AMBROSIUM)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("A")
                .pattern("/")
                .unlockedBy(getHasName(AetherItems.HOLYSTONE_PICKAXE.get()), has(AetherItems.HOLYSTONE_PICKAXE.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.SKYROOT_SIGN.get(), 3)
                .group("wooden_sign")
                .define('P', AetherBlocks.SKYROOT_PLANKS.get().asItem())
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.SKYROOT_HANGING_SIGN.get(), 6)
                .group("hanging_sign")
                .define('#', AetherBlocks.STRIPPED_SKYROOT_LOG.get())
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", has(AetherBlocks.STRIPPED_SKYROOT_LOG.get()))
                .save(this.output);

        this.fence(AetherBlocks.SKYROOT_FENCE, AetherBlocks.SKYROOT_PLANKS).save(this.output);
        this.fenceGate(AetherBlocks.SKYROOT_FENCE_GATE, AetherBlocks.SKYROOT_PLANKS).save(this.output);

        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.ICESTONE_WALL.get(), AetherBlocks.ICESTONE.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        this.wall(RecipeCategory.DECORATIONS, AetherBlocks.AEROGEL_WALL.get(), AetherBlocks.AEROGEL.get());

        this.stairs(AetherBlocks.SKYROOT_STAIRS, AetherBlocks.SKYROOT_PLANKS).group("wooden_stairs").save(this.output);
        this.stairs(AetherBlocks.CARVED_STAIRS, AetherBlocks.CARVED_STONE).save(this.output);
        this.stairs(AetherBlocks.ANGELIC_STAIRS, AetherBlocks.ANGELIC_STONE).save(this.output);
        this.stairs(AetherBlocks.HELLFIRE_STAIRS, AetherBlocks.HELLFIRE_STONE).save(this.output);
        this.stairs(AetherBlocks.HOLYSTONE_STAIRS, AetherBlocks.HOLYSTONE).save(this.output);
        this.stairs(AetherBlocks.MOSSY_HOLYSTONE_STAIRS, AetherBlocks.MOSSY_HOLYSTONE).save(this.output);
        this.stairs(AetherBlocks.ICESTONE_STAIRS, AetherBlocks.ICESTONE).save(this.output);
        this.stairs(AetherBlocks.HOLYSTONE_BRICK_STAIRS, AetherBlocks.HOLYSTONE_BRICKS).save(this.output);
        this.stairs(AetherBlocks.AEROGEL_STAIRS, AetherBlocks.AEROGEL).save(this.output);

        this.slabBuilder(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SKYROOT_SLAB.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get()))
                .group("wooden_slab")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(this.output);
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_SLAB.get(), AetherBlocks.CARVED_STONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_SLAB.get(), AetherBlocks.ANGELIC_STONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_SLAB.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_SLAB.get(), AetherBlocks.HOLYSTONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        this.slab(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_SLAB.get(), AetherBlocks.AEROGEL.get());

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SKYROOT_BOOKSHELF.get(), 1)
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .define('B', Items.BOOK)
                .pattern("PPP")
                .pattern("BBB")
                .pattern("PPP")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, AetherBlocks.SKYROOT_BED.get(), 1)
                .define('W', ItemTags.WOOL)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("WWW")
                .pattern("PPP")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(this.output);


        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, Items.PURPLE_DYE)
                .group("purple_dye")
                .requires(AetherBlocks.PURPLE_FLOWER.get())
                .unlockedBy(getHasName(AetherBlocks.PURPLE_FLOWER.get()), has(AetherBlocks.PURPLE_FLOWER.get()))
                .save(this.output, this.name("flower_to_purple_dye"));

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, Items.WHITE_DYE)
                .group("white_dye")
                .requires(AetherBlocks.WHITE_FLOWER.get())
                .unlockedBy(getHasName(AetherBlocks.WHITE_FLOWER.get()), has(AetherBlocks.WHITE_FLOWER.get()))
                .save(this.output, this.name("flower_to_white_dye"));


        this.makePickaxeWithTag(AetherItems.SKYROOT_PICKAXE, AetherTags.Items.SKYROOT_TOOL_CRAFTING, "has_planks").save(this.output);
        this.makeAxeWithTag(AetherItems.SKYROOT_AXE, AetherTags.Items.SKYROOT_TOOL_CRAFTING, "has_planks").save(this.output);
        this.makeShovelWithTag(AetherItems.SKYROOT_SHOVEL, AetherTags.Items.SKYROOT_TOOL_CRAFTING, "has_planks").save(this.output);
        this.makeHoeWithTag(AetherItems.SKYROOT_HOE, AetherTags.Items.SKYROOT_TOOL_CRAFTING, "has_planks").save(this.output);

        this.makePickaxeWithBlock(AetherItems.HOLYSTONE_PICKAXE, AetherBlocks.HOLYSTONE).save(this.output);
        this.makeAxeWithBlock(AetherItems.HOLYSTONE_AXE, AetherBlocks.HOLYSTONE).save(this.output);
        this.makeShovelWithBlock(AetherItems.HOLYSTONE_SHOVEL, AetherBlocks.HOLYSTONE).save(this.output);
        this.makeHoeWithBlock(AetherItems.HOLYSTONE_HOE, AetherBlocks.HOLYSTONE).save(this.output);

        this.makePickaxeWithTag(AetherItems.ZANITE_PICKAXE, AetherTags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeAxeWithTag(AetherItems.ZANITE_AXE, AetherTags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeShovelWithTag(AetherItems.ZANITE_SHOVEL, AetherTags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeHoeWithTag(AetherItems.ZANITE_HOE, AetherTags.Items.GEMS_ZANITE, "has_zanite").save(this.output);

        this.makePickaxeWithTag(AetherItems.GRAVITITE_PICKAXE, AetherTags.Items.PROCESSED_GRAVITITE, "has_gravitite").save(this.output);
        this.makeAxeWithTag(AetherItems.GRAVITITE_AXE, AetherTags.Items.PROCESSED_GRAVITITE, "has_gravitite").save(this.output);
        this.makeShovelWithTag(AetherItems.GRAVITITE_SHOVEL, AetherTags.Items.PROCESSED_GRAVITITE, "has_gravitite").save(this.output);
        this.makeHoeWithTag(AetherItems.GRAVITITE_HOE, AetherTags.Items.PROCESSED_GRAVITITE, "has_gravitite").save(this.output);

        this.makeSwordWithTag(AetherItems.SKYROOT_SWORD, AetherTags.Items.SKYROOT_TOOL_CRAFTING, "has_planks").save(this.output);
        this.makeSwordWithBlock(AetherItems.HOLYSTONE_SWORD, AetherBlocks.HOLYSTONE).save(this.output);
        this.makeSwordWithTag(AetherItems.ZANITE_SWORD, AetherTags.Items.GEMS_ZANITE, "has_zanite").save(this.output);
        this.makeSwordWithTag(AetherItems.GRAVITITE_SWORD, AetherTags.Items.PROCESSED_GRAVITITE, "has_gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, AetherItems.GOLDEN_DART.get(), 4)
                .define('F', Tags.Items.FEATHERS)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .pattern("F")
                .pattern("/")
                .pattern("G")
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .unlockedBy(getHasName(AetherItems.GOLDEN_AMBER.get()), has(AetherItems.GOLDEN_AMBER.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, AetherItems.GOLDEN_DART_SHOOTER.get(), 1)
                .define('P', AetherBlocks.SKYROOT_PLANKS.get())
                .define('G', AetherItems.GOLDEN_AMBER.get())
                .pattern("P")
                .pattern("P")
                .pattern("G")
                .unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get()))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, AetherItems.POISON_DART.get(), 8)
                .define('D', AetherItems.GOLDEN_DART.get())
                .define('B', AetherItems.SKYROOT_POISON_BUCKET.get())
                .pattern("DDD")
                .pattern("DBD")
                .pattern("DDD")
                .unlockedBy(getHasName(AetherItems.GOLDEN_DART.get()), has(AetherItems.GOLDEN_DART.get()))
                .unlockedBy(getHasName(AetherItems.SKYROOT_POISON_BUCKET.get()), has(AetherItems.SKYROOT_POISON_BUCKET.get()))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.COMBAT, AetherItems.POISON_DART_SHOOTER.get(), 1)
                .requires(AetherItems.GOLDEN_DART_SHOOTER.get())
                .requires(AetherItems.AECHOR_PETAL.get())
                .unlockedBy(getHasName(AetherItems.GOLDEN_DART_SHOOTER.get()), has(AetherItems.GOLDEN_DART_SHOOTER.get()))
                .unlockedBy(getHasName(AetherItems.AECHOR_PETAL.get()), has(AetherItems.AECHOR_PETAL.get()))
                .save(this.output);

        this.makeHelmetWithTag(getter, AetherItems.ZANITE_HELMET, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeChestplateWithTag(getter, AetherItems.ZANITE_CHESTPLATE, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherItems.ZANITE_LEGGINGS, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeBootsWithTag(getter, AetherItems.ZANITE_BOOTS, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);

        this.makeHelmetWithTag(getter, AetherItems.GRAVITITE_HELMET, AetherTags.Items.PROCESSED_GRAVITITE, "gravitite").save(this.output);
        this.makeChestplateWithTag(getter, AetherItems.GRAVITITE_CHESTPLATE, AetherTags.Items.PROCESSED_GRAVITITE, "gravitite").save(this.output);
        this.makeLeggingsWithTag(getter, AetherItems.GRAVITITE_LEGGINGS, AetherTags.Items.PROCESSED_GRAVITITE, "gravitite").save(this.output);
        this.makeBootsWithTag(getter, AetherItems.GRAVITITE_BOOTS, AetherTags.Items.PROCESSED_GRAVITITE, "gravitite").save(this.output);

        this.makeRing(getter, AetherItems.IRON_RING, Items.IRON_INGOT).save(this.output);
        this.makeRing(getter, AetherItems.GOLDEN_RING, Items.GOLD_INGOT).save(this.output);
        this.makeRingWithTag(getter, AetherItems.ZANITE_RING, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);

        this.makePendant(AetherItems.IRON_PENDANT, Items.IRON_INGOT).save(this.output);
        this.makePendant(AetherItems.GOLDEN_PENDANT, Items.GOLD_INGOT).save(this.output);
        this.makePendantWithTag(AetherItems.ZANITE_PENDANT, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);

        this.makeCape(AetherItems.RED_CAPE, Blocks.RED_WOOL.asItem()).save(this.output);
        this.makeCape(AetherItems.BLUE_CAPE, Blocks.BLUE_WOOL.asItem()).group("blue_cape").save(this.output, this.name("blue_cape_blue_wool"));
        this.makeCape(AetherItems.BLUE_CAPE, Blocks.LIGHT_BLUE_WOOL.asItem()).group("blue_cape").save(this.output, this.name("blue_cape_light_blue_wool"));
        this.makeCape(AetherItems.BLUE_CAPE, Blocks.CYAN_WOOL.asItem()).group("blue_cape").save(this.output, this.name("blue_cape_cyan_wool"));
        this.makeCape(AetherItems.YELLOW_CAPE, Blocks.YELLOW_WOOL.asItem()).save(this.output);
        this.makeCape(AetherItems.WHITE_CAPE, Blocks.WHITE_WOOL.asItem()).save(this.output);

        this.makeGlovesWithTag(getter, AetherItems.LEATHER_GLOVES, Tags.Items.LEATHERS, "leather").save(this.output);
        this.makeGlovesWithTag(getter, AetherItems.IRON_GLOVES, Tags.Items.INGOTS_IRON, "iron").save(this.output);
        this.makeGlovesWithTag(getter, AetherItems.GOLDEN_GLOVES, Tags.Items.INGOTS_GOLD, "gold").save(this.output);
        this.makeGlovesWithTag(getter, AetherItems.DIAMOND_GLOVES, Tags.Items.GEMS_DIAMOND, "diamond").save(this.output);
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(AetherItems.DIAMOND_GLOVES.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.COMBAT, AetherItems.NETHERITE_GLOVES.get()).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(this.output, this.name(getItemName(AetherItems.NETHERITE_GLOVES.get()) + "_smithing"));
        this.makeGlovesWithTag(getter, AetherItems.ZANITE_GLOVES, AetherTags.Items.GEMS_ZANITE, "zanite").save(this.output);
        this.makeGlovesWithTag(getter, AetherItems.GRAVITITE_GLOVES, AetherTags.Items.PROCESSED_GRAVITITE, "gravitite").save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherItems.SKYROOT_STICK.get(), 4)
                .group("sticks")
                .define('#', AetherTags.Items.SKYROOT_STICK_CRAFTING)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_planks", has(AetherTags.Items.SKYROOT_STICK_CRAFTING))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.MISC, AetherItems.SKYROOT_BUCKET.get(), 1)
                .define('#', AetherTags.Items.SKYROOT_TOOL_CRAFTING)
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_planks", has(AetherTags.Items.SKYROOT_TOOL_CRAFTING))
                .save(this.output);

        this.twoByTwoPacker(RecipeCategory.TOOLS, AetherItems.COLD_PARACHUTE.get(), AetherBlocks.COLD_AERCLOUD.get());
        this.twoByTwoPacker(RecipeCategory.TOOLS, AetherItems.GOLDEN_PARACHUTE.get(), AetherBlocks.GOLDEN_AERCLOUD.get());

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.TOOLS, AetherItems.NATURE_STAFF.get(), 1)
                .define('Z', AetherTags.Items.GEMS_ZANITE)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .pattern("Z")
                .pattern("/")
                .unlockedBy("has_zanite", has(AetherTags.Items.GEMS_ZANITE))
                .save(this.output);

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.MISC, AetherItems.BOOK_OF_LORE.get())
                .group("book_of_lore")
                .requires(Items.BOOK)
                .requires(AetherTags.Items.BOOK_OF_LORE_MATERIALS)
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(this.output);

        this.woodenBoat(AetherItems.SKYROOT_BOAT.get(), AetherBlocks.SKYROOT_PLANKS.get());
        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.TRANSPORTATION, AetherItems.SKYROOT_CHEST_BOAT.get())
                .group("chest_boat")
                .requires(Tags.Items.CHESTS_WOODEN)
                .requires(AetherItems.SKYROOT_BOAT.get())
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(this.output);

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.TOOLS, Items.SADDLE, 1)
                .define('L', Items.LEATHER)
                .define('S', Items.STRING)
                .pattern("LLL")
                .pattern("LSL")
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(this.output, this.name("aether_saddle"));

        this.doorBuilder(AetherBlocks.SKYROOT_DOOR.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_door").save(this.output);
        this.trapdoorBuilder(AetherBlocks.SKYROOT_TRAPDOOR.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_trapdoor").save(this.output);
        this.buttonBuilder(AetherBlocks.SKYROOT_BUTTON.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_button").save(this.output);
        this.buttonBuilder(AetherBlocks.HOLYSTONE_BUTTON.get(), Ingredient.of(AetherBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get())).save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherBlocks.SKYROOT_PRESSURE_PLATE.get(), Ingredient.of(AetherBlocks.SKYROOT_PLANKS.get())).unlockedBy(getHasName(AetherBlocks.SKYROOT_PLANKS.get()), has(AetherBlocks.SKYROOT_PLANKS.get())).group("wooden_pressure_plate").save(this.output);
        this.pressurePlateBuilder(RecipeCategory.REDSTONE, AetherBlocks.HOLYSTONE_PRESSURE_PLATE.get(), Ingredient.of(AetherBlocks.HOLYSTONE.get())).unlockedBy(getHasName(AetherBlocks.HOLYSTONE.get()), has(AetherBlocks.HOLYSTONE.get())).save(this.output);

        // The group IDs below match the IDs of the respective vanilla recipes
        ShapedRecipeBuilder.shaped(getter, RecipeCategory.TOOLS, Items.LEAD, 2)
                .group("minecraft:lead")
                .define('B', AetherTags.Items.SWET_BALLS)
                .define('S', Tags.Items.STRINGS)
                .pattern("SS ")
                .pattern("SB ")
                .pattern("  S")
                .unlockedBy("has_swet_balls", has(AetherTags.Items.SWET_BALLS))
                .save(this.output, this.name("swet_lead"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.STICKY_PISTON, 1)
                .group("minecraft:sticky_piston")
                .define('B', AetherTags.Items.SWET_BALLS)
                .define('P', Blocks.PISTON)
                .pattern("B")
                .pattern("P")
                .unlockedBy("has_swet_balls", has(AetherTags.Items.SWET_BALLS))
                .save(this.output, this.name("swet_sticky_piston"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.SLIME_BLOCK, 1)
                .group("minecraft:slime_block")
                .define('B', AetherTags.Items.SWET_BALLS)
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_swet_balls", has(AetherTags.Items.SWET_BALLS))
                .save(this.output, this.name("swet_slime_block"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.BARREL, 1)
                .group("minecraft:barrel")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('H', ItemTags.WOODEN_SLABS)
                .pattern("PHP")
                .pattern("P P")
                .pattern("PHP")
                .unlockedBy("has_planks", has(AetherTags.Items.PLANKS_CRAFTING))
                .unlockedBy("has_slabs", has(ItemTags.WOODEN_SLABS))
                .save(this.output, this.name("skyroot_barrel"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.BEEHIVE, 1)
                .group("minecraft:beehive")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Items.HONEYCOMB)
                .pattern("PPP")
                .pattern("CCC")
                .pattern("PPP")
                .unlockedBy(getHasName(Items.HONEYCOMB), has(Items.HONEYCOMB))
                .save(this.output, this.name("skyroot_beehive"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.CARTOGRAPHY_TABLE, 1)
                .group("minecraft:cartography_table")
                .define('#', Items.PAPER)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("##")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(this.output, this.name("skyroot_cartography_table"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.CHEST, 1)
                .group("minecraft:chest")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("PPP")
                .pattern("P P")
                .pattern("PPP")
                .unlockedBy("has_lots_of_items", CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), new InventoryChangeTrigger.TriggerInstance.Slots(MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY), List.of())))
                .save(this.output, this.name("skyroot_chest"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.CRAFTING_TABLE, 1)
                .group("minecraft:crafting_table")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("PP")
                .pattern("PP")
                .unlockedBy("has_planks", has(AetherTags.Items.PLANKS_CRAFTING))
                .save(this.output, this.name("skyroot_crafting_table"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.FLETCHING_TABLE, 1)
                .group("minecraft:fletching_table")
                .define('F', Items.FLINT)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("FF")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy(getHasName(Items.FLINT), has(Items.FLINT))
                .save(this.output, this.name("skyroot_fletching_table"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.GRINDSTONE, 1)
                .group("minecraft:grindstone")
                .define('/', Tags.Items.RODS_WOODEN)
                .define('H', Blocks.STONE_SLAB)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("/H/")
                .pattern("P P")
                .unlockedBy(getHasName(Blocks.STONE_SLAB), has(Blocks.STONE_SLAB))
                .save(this.output, this.name("skyroot_grindstone"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.GRINDSTONE, 1)
                .group("minecraft:grindstone")
                .define('/', Tags.Items.RODS_WOODEN)
                .define('H', AetherBlocks.HOLYSTONE_SLAB.get())
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("/H/")
                .pattern("P P")
                .unlockedBy(getHasName(AetherBlocks.HOLYSTONE_SLAB.get()), has(AetherBlocks.HOLYSTONE_SLAB.get()))
                .save(this.output, this.name("skyroot_grindstone_holystone_slab"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .pattern("PPP")
                .pattern("PDP")
                .pattern("PPP")
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND))
                .save(this.output, this.name("skyroot_jukebox"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', ItemTags.PLANKS)
                .define('G', AetherTags.Items.PROCESSED_GRAVITITE)
                .pattern("PPP")
                .pattern("PGP")
                .pattern("PPP")
                .unlockedBy("has_gravitite", has(AetherTags.Items.PROCESSED_GRAVITITE))
                .save(this.output, this.name("gravitite_jukebox"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.JUKEBOX, 1)
                .group("minecraft:jukebox")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('G', AetherTags.Items.PROCESSED_GRAVITITE)
                .pattern("PPP")
                .pattern("PGP")
                .pattern("PPP")
                .unlockedBy("has_gravitite", has(AetherTags.Items.PROCESSED_GRAVITITE))
                .save(this.output, this.name("skyroot_gravitite_jukebox"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.LOOM, 1)
                .group("minecraft:loom")
                .define('T', Tags.Items.STRINGS)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("TT")
                .pattern("PP")
                .unlockedBy("has_string", has(Tags.Items.STRINGS))
                .save(this.output, this.name("skyroot_loom"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.NOTE_BLOCK, 1)
                .group("minecraft:note_block")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("PPP")
                .pattern("PRP")
                .pattern("PPP")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(this.output, this.name("skyroot_note_block"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.PISTON, 1)
                .group("minecraft:piston")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('C', Blocks.COBBLESTONE)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .pattern("PPP")
                .pattern("CIC")
                .pattern("CRC")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .save(this.output, this.name("skyroot_piston"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, Items.SHIELD, 1)
                .group("minecraft:shield")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('I', Tags.Items.INGOTS_IRON)
                .pattern("PIP")
                .pattern("PPP")
                .pattern(" P ")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(this.output, this.name("skyroot_iron_vanilla_shield"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, Items.SHIELD, 1)
                .group("minecraft:shield")
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .define('Z', AetherTags.Items.GEMS_ZANITE)
                .pattern("PZP")
                .pattern("PPP")
                .pattern(" P ")
                .unlockedBy("has_zanite_gemstone", has(AetherTags.Items.GEMS_ZANITE))
                .save(this.output, this.name("skyroot_zanite_vanilla_shield"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.COMBAT, Items.SHIELD, 1)
                .group("minecraft:shield")
                .define('P', ItemTags.PLANKS)
                .define('Z', AetherTags.Items.GEMS_ZANITE)
                .pattern("PZP")
                .pattern("PPP")
                .pattern(" P ")
                .unlockedBy("has_zanite_gemstone", has(AetherTags.Items.GEMS_ZANITE))
                .save(this.output, this.name("wood_zanite_vanilla_shield"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.SMITHING_TABLE, 1)
                .group("minecraft:smithing_table")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("II")
                .pattern("PP")
                .pattern("PP")
                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .save(this.output, this.name("skyroot_smithing_table"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.TRIPWIRE_HOOK, 2)
                .group("minecraft:tripwire_hook")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('/', Tags.Items.RODS_WOODEN)
                .define('P', AetherTags.Items.PLANKS_CRAFTING)
                .pattern("I")
                .pattern("/")
                .pattern("P")
                .unlockedBy("has_string", has(Tags.Items.STRINGS))
                .save(this.output, this.name("skyroot_tripwire_hook"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.DECORATIONS, Blocks.STONECUTTER)
                .group("minecraft:stonecutter")
                .define('I', Items.IRON_INGOT)
                .define('#', AetherBlocks.HOLYSTONE.get())
                .pattern(" I ")
                .pattern("###")
                .unlockedBy("has_holystone", has(AetherBlocks.HOLYSTONE.get()))
                .save(this.output, this.name("holystone_stonecutter"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.REDSTONE, Blocks.LECTERN)
                .group("minecraft:lectern")
                .define('S', ItemTags.WOODEN_SLABS)
                .define('B', AetherBlocks.SKYROOT_BOOKSHELF.get())
                .pattern("SSS")
                .pattern(" B ")
                .pattern(" S ")
                .unlockedBy("has_book", has(Items.BOOK))
                .save(this.output, this.name("skyroot_lectern"));


        ShapedRecipeBuilder.shaped(getter, RecipeCategory.FOOD, Blocks.CAKE)
                .group("minecraft:cake")
                .define('A', AetherItems.SKYROOT_MILK_BUCKET.get())
                .define('B', Items.SUGAR)
                .define('C', Items.WHEAT)
                .define('E', Items.EGG)
                .pattern("AAA")
                .pattern("BEB")
                .pattern("CCC")
                .unlockedBy("has_egg", has(Items.EGG))
                .save(this.output, this.name("skyroot_milk_bucket_cake"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.FOOD, Blocks.CAKE)
                .group("minecraft:cake")
                .define('A', Items.MILK_BUCKET)
                .define('B', Items.SUGAR)
                .define('C', Items.WHEAT)
                .define('E', AetherTags.Items.MOA_EGGS)
                .pattern("AAA")
                .pattern("BEB")
                .pattern("CCC")
                .unlockedBy("has_moa_egg", has(AetherTags.Items.MOA_EGGS))
                .save(this.output, this.name("moa_egg_cake"));

        ShapedRecipeBuilder.shaped(getter, RecipeCategory.FOOD, Blocks.CAKE)
                .group("minecraft:cake")
                .define('A', AetherItems.SKYROOT_MILK_BUCKET.get())
                .define('B', Items.SUGAR)
                .define('C', Items.WHEAT)
                .define('E', AetherTags.Items.MOA_EGGS)
                .pattern("AAA")
                .pattern("BEB")
                .pattern("CCC")
                .unlockedBy("has_moa_egg", has(AetherTags.Items.MOA_EGGS))
                .save(this.output, this.name("skyroot_milk_bucket_moa_egg_cake"));

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.FOOD, Items.PUMPKIN_PIE)
                .group("minecraft:pumpkin_pie")
                .requires(Blocks.PUMPKIN)
                .requires(Items.SUGAR)
                .requires(AetherTags.Items.MOA_EGGS)
                .unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
                .unlockedBy("has_pumpkin", has(Blocks.PUMPKIN))
                .save(this.output, this.name("moa_egg_pumpkin_pie"));

        ShapelessRecipeBuilder.shapeless(getter, RecipeCategory.DECORATIONS, AetherItems.createSwetBannerItemStack(this.registries.lookupOrThrow(Registries.BANNER_PATTERN)))
            .requires(ItemTags.BANNERS)
            .requires(AetherItems.SWET_CAPE.get())
            .unlockedBy("has_swet_cape", has(AetherItems.SWET_CAPE.get()))
            .save(this.output, this.name("swet_banner"));


        this.smeltingOreRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(this.output, this.name("ambrosium_shard_from_smelting"));
        this.blastingOreRecipe(AetherItems.AMBROSIUM_SHARD.get(), AetherBlocks.AMBROSIUM_ORE.get(), 0.1F).save(this.output, this.name("ambrosium_shard_from_blasting"));

        this.smeltingOreRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(this.output, this.name("zanite_gemstone_from_smelting"));
        this.blastingOreRecipe(AetherItems.ZANITE_GEMSTONE.get(), AetherBlocks.ZANITE_ORE.get(), 0.7F).save(this.output, this.name("zanite_gemstone_from_blasting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherItems.GOLDEN_GLOVES.get(), AetherItems.GOLDEN_PENDANT.get(), AetherItems.GOLDEN_RING.get(), AetherItems.VICTORY_MEDAL.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 200).unlockedBy("has_golden_gloves", has(AetherItems.GOLDEN_GLOVES.get())).unlockedBy("has_golden_pendant", has(AetherItems.GOLDEN_PENDANT.get())).unlockedBy("has_golden_ring", has(AetherItems.GOLDEN_RING.get())).unlockedBy("has_victory_medal", has(AetherItems.VICTORY_MEDAL.get())).group(getSmeltingRecipeName(Items.GOLD_NUGGET)).save(this.output, this.name("aether_" + getSmeltingRecipeName(Items.GOLD_NUGGET)));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(AetherItems.IRON_GLOVES.get(), AetherItems.IRON_PENDANT.get(), AetherItems.IRON_RING.get(), AetherItems.CHAINMAIL_GLOVES.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 200).unlockedBy("has_iron_gloves", has(AetherItems.IRON_GLOVES.get())).unlockedBy("has_iron_pendant", has(AetherItems.IRON_PENDANT.get())).unlockedBy("has_iron_ring", has(AetherItems.IRON_RING.get())).unlockedBy("has_chainmail_gloves", has(AetherItems.CHAINMAIL_GLOVES.get())).group(getSmeltingRecipeName(Items.IRON_NUGGET)).save(this.output, this.name("aether_" + getSmeltingRecipeName(Items.IRON_NUGGET)));

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(AetherItems.GOLDEN_GLOVES.get(), AetherItems.GOLDEN_PENDANT.get(), AetherItems.GOLDEN_RING.get(), AetherItems.VICTORY_MEDAL.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1F, 100).unlockedBy("has_golden_gloves", has(AetherItems.GOLDEN_GLOVES.get())).unlockedBy("has_golden_pendant", has(AetherItems.GOLDEN_PENDANT.get())).unlockedBy("has_golden_ring", has(AetherItems.GOLDEN_RING.get())).unlockedBy("has_victory_medal", has(AetherItems.VICTORY_MEDAL.get())).group(getBlastingRecipeName(Items.GOLD_NUGGET)).save(this.output, this.name("aether_" + getBlastingRecipeName(Items.GOLD_NUGGET)));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(AetherItems.IRON_GLOVES.get(), AetherItems.IRON_PENDANT.get(), AetherItems.IRON_RING.get(), AetherItems.CHAINMAIL_GLOVES.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1F, 100).unlockedBy("has_iron_gloves", has(AetherItems.IRON_GLOVES.get())).unlockedBy("has_iron_pendant", has(AetherItems.IRON_PENDANT.get())).unlockedBy("has_iron_ring", has(AetherItems.IRON_RING.get())).unlockedBy("has_chainmail_gloves", has(AetherItems.CHAINMAIL_GLOVES.get())).group(getBlastingRecipeName(Items.IRON_NUGGET)).save(this.output, this.name("aether_" + getBlastingRecipeName(Items.IRON_NUGGET)));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MUSIC_DISC_STAL), RecipeCategory.MISC, AetherItems.MUSIC_DISC_KLEPTO, 1.0F, 200).unlockedBy("has_disc", has(AetherItems.MUSIC_DISC_KLEPTO.get())).save(this.output, this.name("klepto_smelting"));


        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.CARVED_WALL.get(), AetherBlocks.CARVED_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_STAIRS.get(), AetherBlocks.CARVED_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_SLAB.get(), AetherBlocks.CARVED_STONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.SENTRY_STONE.get(), AetherBlocks.CARVED_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CARVED_STONE.get(), AetherBlocks.SENTRY_STONE.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.ANGELIC_WALL.get(), AetherBlocks.ANGELIC_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STAIRS.get(), AetherBlocks.ANGELIC_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_SLAB.get(), AetherBlocks.ANGELIC_STONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR.get(), AetherBlocks.ANGELIC_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR_TOP.get(), AetherBlocks.ANGELIC_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.PILLAR_TOP.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR_TOP.get(), AetherBlocks.PILLAR.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.PILLAR.get(), AetherBlocks.PILLAR_TOP.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.LIGHT_ANGELIC_STONE.get(), AetherBlocks.ANGELIC_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ANGELIC_STONE.get(), AetherBlocks.LIGHT_ANGELIC_STONE.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.HELLFIRE_WALL.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_STAIRS.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_SLAB.get(), AetherBlocks.HELLFIRE_STONE.get(), 2);
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.LIGHT_HELLFIRE_STONE.get(), AetherBlocks.HELLFIRE_STONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HELLFIRE_STONE.get(), AetherBlocks.LIGHT_HELLFIRE_STONE.get());

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_WALL.get(), AetherBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_STAIRS.get(), AetherBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_SLAB.get(), AetherBlocks.HOLYSTONE.get(), 2);

        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICKS.get(), AetherBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherBlocks.HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE.get(), 2);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.MOSSY_HOLYSTONE_WALL.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(), AetherBlocks.MOSSY_HOLYSTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(), AetherBlocks.MOSSY_HOLYSTONE.get(), 2);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.HOLYSTONE_BRICK_WALL.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_STAIRS.get(), AetherBlocks.HOLYSTONE_BRICKS.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.HOLYSTONE_BRICK_SLAB.get(), AetherBlocks.HOLYSTONE_BRICKS.get(), 2);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.ICESTONE_WALL.get(), AetherBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ICESTONE_STAIRS.get(), AetherBlocks.ICESTONE.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ICESTONE_SLAB.get(), AetherBlocks.ICESTONE.get(), 2);

        this.stonecuttingRecipe(this.output, RecipeCategory.DECORATIONS, AetherBlocks.AEROGEL_WALL.get(), AetherBlocks.AEROGEL.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_STAIRS.get(), AetherBlocks.AEROGEL.get());
        this.stonecuttingRecipe(this.output, RecipeCategory.BUILDING_BLOCKS, AetherBlocks.AEROGEL_SLAB.get(), AetherBlocks.AEROGEL.get(), 2);


        this.repairingRecipe(RecipeCategory.TOOLS, Items.FISHING_ROD, 300).save(this.output, this.name("fishing_rod_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.FLINT_AND_STEEL, 300).save(this.output, this.name("flint_and_steel_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.BOW, 600).save(this.output, this.name("bow_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.CROSSBOW, 600).save(this.output, this.name("crossbow_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.SHIELD, 600).save(this.output, this.name("shield_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_PICKAXE.get(), 250).group("altar_pickaxe_repair").save(this.output, this.name("skyroot_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_AXE.get(), 250).group("altar_axe_repair").save(this.output, this.name("skyroot_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_SHOVEL.get(), 250).group("altar_shovel_repair").save(this.output, this.name("skyroot_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.SKYROOT_HOE.get(), 250).group("altar_hoe_repair").save(this.output, this.name("skyroot_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_PICKAXE.get(), 500).group("altar_pickaxe_repair").save(this.output, this.name("holystone_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_AXE.get(), 500).group("altar_axe_repair").save(this.output, this.name("holystone_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_SHOVEL.get(), 500).group("altar_shovel_repair").save(this.output, this.name("holystone_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.HOLYSTONE_HOE.get(), 500).group("altar_hoe_repair").save(this.output, this.name("holystone_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_PICKAXE.get(), 750).group("altar_pickaxe_repair").save(this.output, this.name("zanite_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_AXE.get(), 750).group("altar_axe_repair").save(this.output, this.name("zanite_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_SHOVEL.get(), 750).group("altar_shovel_repair").save(this.output, this.name("zanite_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.ZANITE_HOE.get(), 750).group("altar_hoe_repair").save(this.output, this.name("zanite_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_PICKAXE.get(), 1500).group("altar_pickaxe_repair").save(this.output, this.name("gravitite_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_AXE.get(), 1500).group("altar_axe_repair").save(this.output, this.name("gravitite_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_SHOVEL.get(), 1500).group("altar_shovel_repair").save(this.output, this.name("gravitite_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, AetherItems.GRAVITITE_HOE.get(), 1500).group("altar_hoe_repair").save(this.output, this.name("gravitite_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.SKYROOT_SWORD.get(), 250).group("altar_sword_repair").save(this.output, this.name("skyroot_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.HOLYSTONE_SWORD.get(), 500).group("altar_sword_repair").save(this.output, this.name("holystone_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_SWORD.get(), 750).group("altar_sword_repair").save(this.output, this.name("zanite_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_SWORD.get(), 1500).group("altar_sword_repair").save(this.output, this.name("gravitite_sword_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_HELMET.get(), 750).group("altar_helmet_repair").save(this.output, this.name("zanite_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_CHESTPLATE.get(), 750).group("altar_chestplate_repair").save(this.output, this.name("zanite_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_LEGGINGS.get(), 750).group("altar_leggings_repair").save(this.output, this.name("zanite_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_BOOTS.get(), 750).group("altar_boots_repair").save(this.output, this.name("zanite_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_GLOVES.get(), 750).group("altar_gloves_repair").save(this.output, this.name("zanite_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_HELMET.get(), 1500).group("altar_helmet_repair").save(this.output, this.name("gravitite_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_CHESTPLATE.get(), 1500).group("altar_chestplate_repair").save(this.output, this.name("gravitite_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_LEGGINGS.get(), 1500).group("altar_leggings_repair").save(this.output, this.name("gravitite_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_BOOTS.get(), 1500).group("altar_boots_repair").save(this.output, this.name("gravitite_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GRAVITITE_GLOVES.get(), 1500).group("altar_gloves_repair").save(this.output, this.name("gravitite_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_PICKAXE, 250).group("altar_pickaxe_repair").save(this.output, this.name("wooden_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_AXE, 250).group("altar_axe_repair").save(this.output, this.name("wooden_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_SHOVEL, 250).group("altar_shovel_repair").save(this.output, this.name("wooden_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.WOODEN_HOE, 250).group("altar_hoe_repair").save(this.output, this.name("wooden_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.STONE_PICKAXE, 500).group("altar_pickaxe_repair").save(this.output, this.name("stone_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.STONE_AXE, 500).group("altar_axe_repair").save(this.output, this.name("stone_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.STONE_SHOVEL, 500).group("altar_shovel_repair").save(this.output, this.name("stone_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.STONE_HOE, 500).group("altar_hoe_repair").save(this.output, this.name("stone_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.IRON_PICKAXE, 750).group("altar_pickaxe_repair").save(this.output, this.name("iron_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.IRON_AXE, 750).group("altar_axe_repair").save(this.output, this.name("iron_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.IRON_SHOVEL, 750).group("altar_shovel_repair").save(this.output, this.name("iron_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.IRON_HOE, 750).group("altar_hoe_repair").save(this.output, this.name("iron_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_PICKAXE, 300).group("altar_pickaxe_repair").save(this.output, this.name("golden_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_AXE, 300).group("altar_axe_repair").save(this.output, this.name("golden_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_SHOVEL, 300).group("altar_shovel_repair").save(this.output, this.name("golden_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.GOLDEN_HOE, 300).group("altar_hoe_repair").save(this.output, this.name("golden_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_PICKAXE, 1500).group("altar_pickaxe_repair").save(this.output, this.name("diamond_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_AXE, 1500).group("altar_axe_repair").save(this.output, this.name("diamond_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_SHOVEL, 1500).group("altar_shovel_repair").save(this.output, this.name("diamond_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.DIAMOND_HOE, 1500).group("altar_hoe_repair").save(this.output, this.name("diamond_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_PICKAXE, 2000).group("altar_pickaxe_repair").save(this.output, this.name("netherite_pickaxe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_AXE, 2000).group("altar_axe_repair").save(this.output, this.name("netherite_axe_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_SHOVEL, 2000).group("altar_shovel_repair").save(this.output, this.name("netherite_shovel_repairing"));
        this.repairingRecipe(RecipeCategory.TOOLS, Items.NETHERITE_HOE, 2000).group("altar_hoe_repair").save(this.output, this.name("netherite_hoe_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.WOODEN_SWORD, 250).group("altar_sword_repair").save(this.output, this.name("wooden_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.STONE_SWORD, 500).group("altar_sword_repair").save(this.output, this.name("stone_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.IRON_SWORD, 750).group("altar_sword_repair").save(this.output, this.name("iron_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_SWORD, 300).group("altar_sword_repair").save(this.output, this.name("golden_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_SWORD, 1500).group("altar_sword_repair").save(this.output, this.name("diamond_sword_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_SWORD, 2000).group("altar_sword_repair").save(this.output, this.name("netherite_sword_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_HELMET, 250).group("altar_helmet_repair").save(this.output, this.name("leather_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_CHESTPLATE, 250).group("altar_chestplate_repair").save(this.output, this.name("leather_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_LEGGINGS, 250).group("altar_leggings_repair").save(this.output, this.name("leather_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.LEATHER_BOOTS, 250).group("altar_boots_repair").save(this.output, this.name("leather_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.LEATHER_GLOVES.get(), 250).group("altar_gloves_repair").save(this.output, this.name("leather_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.IRON_HELMET, 750).group("altar_helmet_repair").save(this.output, this.name("iron_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.IRON_CHESTPLATE, 750).group("altar_chestplate_repair").save(this.output, this.name("iron_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.IRON_LEGGINGS, 750).group("altar_leggings_repair").save(this.output, this.name("iron_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.IRON_BOOTS, 750).group("altar_boots_repair").save(this.output, this.name("iron_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.IRON_GLOVES.get(), 750).group("altar_gloves_repair").save(this.output, this.name("iron_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_HELMET, 300).group("altar_helmet_repair").save(this.output, this.name("golden_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_CHESTPLATE, 300).group("altar_chestplate_repair").save(this.output, this.name("golden_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_LEGGINGS, 300).group("altar_leggings_repair").save(this.output, this.name("golden_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.GOLDEN_BOOTS, 300).group("altar_boots_repair").save(this.output, this.name("golden_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.GOLDEN_GLOVES.get(), 300).group("altar_gloves_repair").save(this.output, this.name("golden_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_HELMET, 700).group("altar_helmet_repair").save(this.output, this.name("chainmail_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_CHESTPLATE, 700).group("altar_chestplate_repair").save(this.output, this.name("chainmail_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_LEGGINGS, 700).group("altar_leggings_repair").save(this.output, this.name("chainmail_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.CHAINMAIL_BOOTS, 700).group("altar_boots_repair").save(this.output, this.name("chainmail_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.CHAINMAIL_GLOVES.get(), 700).group("altar_gloves_repair").save(this.output, this.name("chainmail_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_HELMET, 1500).group("altar_helmet_repair").save(this.output, this.name("diamond_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_CHESTPLATE, 1500).group("altar_chestplate_repair").save(this.output, this.name("diamond_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_LEGGINGS, 1500).group("altar_leggings_repair").save(this.output, this.name("diamond_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.DIAMOND_BOOTS, 1500).group("altar_boots_repair").save(this.output, this.name("diamond_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.DIAMOND_GLOVES.get(), 1500).group("altar_gloves_repair").save(this.output, this.name("diamond_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_HELMET, 2000).group("altar_helmet_repair").save(this.output, this.name("netherite_helmet_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_CHESTPLATE, 2000).group("altar_chestplate_repair").save(this.output, this.name("netherite_chestplate_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_LEGGINGS, 2000).group("altar_leggings_repair").save(this.output, this.name("netherite_leggings_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, Items.NETHERITE_BOOTS, 2000).group("altar_boots_repair").save(this.output, this.name("netherite_boots_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.NETHERITE_GLOVES.get(), 2000).group("altar_gloves_repair").save(this.output, this.name("netherite_gloves_repairing"));

        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_RING.get(), 500).group("altar_ring_repair").save(this.output, this.name("zanite_ring_repairing"));
        this.repairingRecipe(RecipeCategory.COMBAT, AetherItems.ZANITE_PENDANT.get(), 500).group("altar_pendant_repair").save(this.output, this.name("zanite_pendant_repairing"));

        this.enchantingRecipe(RecipeCategory.MISC, AetherItems.ENCHANTED_DART.get(), AetherItems.GOLDEN_DART.get(), 0.15F, 50).save(this.output, this.name("enchanted_dart_enchanting"));
        this.enchantingRecipe(RecipeCategory.MISC, AetherItems.ENCHANTED_DART_SHOOTER.get(), AetherItems.GOLDEN_DART_SHOOTER.get(), 1.0F, 750).save(this.output, this.name("enchanted_dart_shooter_enchanting"));

        this.enchantingRecipe(RecipeCategory.FOOD, AetherItems.HEALING_STONE.get(), AetherBlocks.HOLYSTONE.get(), 0.35F, 500).save(this.output, this.name("healing_stone_enchanting"));
        this.enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.ENCHANTED_GRAVITITE.get(), AetherBlocks.GRAVITITE_ORE.get(), 1.0F, 750).save(this.output, this.name("enchanted_gravitite_enchanting"));
        this.enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.QUICKSOIL_GLASS.get(), AetherBlocks.QUICKSOIL.get(), 0.1F, 250).save(this.output, this.name("quicksoil_glass_enchanting"));
        this.enchantingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.GOLDEN_AERCLOUD.get(), AetherBlocks.COLD_AERCLOUD.get(), 0.1F, 1000).save(this.output, this.name("golden_aercloud_enchanting"));

        this.enchantingRecipe(RecipeCategory.FOOD, AetherItems.ENCHANTED_BERRY.get(), AetherItems.BLUE_BERRY.get(), 0.35F, 250).save(this.output, this.name("enchanted_berry_enchanting"));

        this.enchantingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_AETHER_TUNE.get(), AetherTags.Items.ACCEPTED_MUSIC_DISCS, 1.0F, 500, "disc").save(this.output, this.name("aether_tune_enchanting"));
        this.hiddenEnchantingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_CHINCHILLA.get(), Items.MUSIC_DISC_STRAD, 1.0F, 500).save(this.output, this.name("chinchilla_enchanting"));

        this.enchantingRecipe(RecipeCategory.MISC, AetherItems.SKYROOT_REMEDY_BUCKET.get(), AetherItems.SKYROOT_POISON_BUCKET.get(), 0.35F, 500).save(this.output, this.name("remedy_bucket_enchanting"));

        this.freezingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.BLUE_AERCLOUD.get(), AetherBlocks.COLD_AERCLOUD.get(), 0.1F, 400).save(this.output, this.name("blue_aercloud_freezing"));
        this.freezingRecipe(RecipeCategory.BUILDING_BLOCKS, AetherBlocks.CRYSTAL_LEAVES.get(), AetherBlocks.SKYROOT_LEAVES.get(), 0.1F, 500).save(this.output, this.name("crystal_leaves_freezing"));

        this.hiddenFreezingRecipe(RecipeCategory.MISC, AetherItems.MUSIC_DISC_HIGH.get(), AetherItems.MUSIC_DISC_CHINCHILLA.get(), 1.0F, 500).save(this.output, this.name("high_freezing"));

        this.freezingRecipeWithUnlockTag(RecipeCategory.BUILDING_BLOCKS, Blocks.BLUE_ICE, Blocks.PACKED_ICE, Tags.Items.BUCKETS_WATER, 0.1F, 500, "water_bucket").save(this.output, this.name("blue_ice_freezing"));
        this.freezingRecipeWithUnlockTag(RecipeCategory.BUILDING_BLOCKS, Blocks.PACKED_ICE, Blocks.ICE, Tags.Items.BUCKETS_WATER, 0.1F, 300, "water_bucket").save(this.output, this.name("packed_ice_freezing"));
        this.freezingRecipeWithTag(RecipeCategory.BUILDING_BLOCKS, Blocks.ICE, Tags.Items.BUCKETS_WATER, 0.1F, 200, "water_bucket").save(this.output, this.name("ice_from_bucket_freezing"));
        this.freezingRecipeWithTag(RecipeCategory.BUILDING_BLOCKS, Blocks.OBSIDIAN, Tags.Items.BUCKETS_LAVA, 0.1F, 200, "lava_bucket").save(this.output, this.name("obsidian_from_bucket_freezing"));

        this.freezingRecipeWithTag(RecipeCategory.MISC, AetherItems.ICE_RING.get(), AetherTags.Items.FREEZABLE_RINGS, 1.0F, 800, "ring").save(this.output, this.name("ice_ring_from_freezing"));

        this.freezingRecipeWithTag(RecipeCategory.MISC, AetherItems.ICE_PENDANT.get(), AetherTags.Items.FREEZABLE_PENDANTS, 1.0F, 800, "pendant").save(this.output, this.name("ice_pendant_from_freezing"));

        this.moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.BLUE, AetherItems.BLUE_MOA_EGG.get()).save(this.output, this.name("blue_moa_incubation"));
        this.moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.WHITE, AetherItems.WHITE_MOA_EGG.get()).save(this.output, this.name("white_moa_incubation"));
        this.moaIncubationRecipe(AetherEntityTypes.MOA.get(), AetherMoaTypes.BLACK, AetherItems.BLACK_MOA_EGG.get()).save(this.output, this.name("black_moa_incubation"));

        this.ambrosiumEnchanting(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_GRASS_BLOCK.get()).save(this.output, this.name("ambrosium_enchant_aether_grass_to_enchanted_aether_grass"));

        this.swetBallConversion(Blocks.GRASS_BLOCK, Blocks.DIRT).save(this.output, this.name("swet_ball_dirt_to_grass"));
        this.swetBallConversion(AetherBlocks.AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get()).save(this.output, this.name("swet_ball_aether_dirt_to_aether_grass"));
        this.swetBallConversionTag(Blocks.MYCELIUM, Blocks.DIRT, AetherTags.Biomes.MYCELIUM_CONVERSION).save(this.output, this.name("swet_ball_dirt_to_mycelium"));
        this.swetBallConversionTag(Blocks.PODZOL, Blocks.GRASS_BLOCK, AetherTags.Biomes.PODZOL_CONVERSION).save(this.output, this.name("swet_ball_grass_to_podzol"));
        this.swetBallConversionTag(Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK, AetherTags.Biomes.CRIMSON_NYLIUM_CONVERSION).save(this.output, this.name("swet_ball_netherrack_to_crimson_nylium"));
        this.swetBallConversionTag(Blocks.WARPED_NYLIUM, Blocks.NETHERRACK, AetherTags.Biomes.WARPED_NYLIUM_CONVERSION).save(this.output, this.name("swet_ball_netherrack_to_warped_nylium"));

        this.icestoneFreezable(Blocks.ICE, Blocks.WATER).save(this.output, this.name("icestone_freeze_water"));
        this.icestoneFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(this.output, this.name("icestone_freeze_lava"));

        this.accessoryFreezable(Blocks.ICE, Blocks.WATER).save(this.output, this.name("accessory_freeze_water"));
        this.accessoryFreezable(Blocks.OBSIDIAN, Blocks.LAVA).save(this.output, this.name("accessory_freeze_lava"));

        this.convertPlacement(AetherBlocks.AEROGEL.get(), Blocks.LAVA, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("aerogel_conversion"));
        this.convertPlacementWithProperties(Blocks.CAMPFIRE, this.map(CampfireBlock.LIT, false), Blocks.CAMPFIRE, this.map(CampfireBlock.LIT, true), AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("campfire_conversion"));

        List<Block> candles = List.of(Blocks.CANDLE, Blocks.WHITE_CANDLE, Blocks.ORANGE_CANDLE, Blocks.MAGENTA_CANDLE, Blocks.LIGHT_BLUE_CANDLE, Blocks.YELLOW_CANDLE, Blocks.LIME_CANDLE, Blocks.PINK_CANDLE, Blocks.GRAY_CANDLE, Blocks.LIGHT_GRAY_CANDLE, Blocks.CYAN_CANDLE, Blocks.PURPLE_CANDLE, Blocks.BLUE_CANDLE, Blocks.BROWN_CANDLE, Blocks.GREEN_CANDLE, Blocks.RED_CANDLE, Blocks.BLACK_CANDLE);
        for (Block candle : candles) {
            this.convertPlacementWithProperties(candle, this.map(CandleBlock.LIT, false), candle, this.map(CandleBlock.LIT, true), AetherTags.Biomes.ULTRACOLD).save(this.output, this.name(candle.getDescriptionId().replace(".", "_").replace("block_minecraft_", "") + "_conversion"));
        }

        List<Block> candleCakes = List.of(Blocks.CANDLE_CAKE, Blocks.WHITE_CANDLE_CAKE, Blocks.ORANGE_CANDLE_CAKE, Blocks.MAGENTA_CANDLE_CAKE, Blocks.LIGHT_BLUE_CANDLE_CAKE, Blocks.YELLOW_CANDLE_CAKE, Blocks.LIME_CANDLE_CAKE, Blocks.PINK_CANDLE_CAKE, Blocks.GRAY_CANDLE_CAKE, Blocks.LIGHT_GRAY_CANDLE_CAKE, Blocks.CYAN_CANDLE_CAKE, Blocks.PURPLE_CANDLE_CAKE, Blocks.BLUE_CANDLE_CAKE, Blocks.BROWN_CANDLE_CAKE, Blocks.GREEN_CANDLE_CAKE, Blocks.RED_CANDLE_CAKE, Blocks.BLACK_CANDLE_CAKE);
        for (Block candleCake : candleCakes) {
            this.convertPlacementWithProperties(candleCake, this.map(CandleCakeBlock.LIT, false), candleCake, this.map(CandleCakeBlock.LIT, true), AetherTags.Biomes.ULTRACOLD).save(this.output, this.name(candleCake.getDescriptionId().replace(".", "_").replace("block_minecraft_", "") + "_conversion"));
        }

        this.convertPlacement(Blocks.CARVED_PUMPKIN, Blocks.JACK_O_LANTERN, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("jack_o_lantern_conversion"));

        this.banItemPlacementWithBypass(Items.FLINT_AND_STEEL, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("flint_and_steel_item_ban"));
        this.banItemPlacementWithBypass(Items.FIRE_CHARGE, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("fire_charge_item_ban"));
        this.banItemPlacement(Items.TORCH, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("torch_item_ban"));
        this.banItemPlacement(Items.LANTERN, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("lantern_item_ban"));

        this.banBlockPlacementWithBypass(Blocks.FIRE, AetherTags.Blocks.ALLOWED_FLAMMABLES, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("fire_block_ban"));
        this.banBlockPlacement(Blocks.TORCH, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("torch_block_ban"));
        this.banBlockPlacement(Blocks.LANTERN, AetherTags.Biomes.ULTRACOLD).save(this.output, this.name("lantern_block_ban"));
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new AetherRecipeData(output, provider);
        }

        public String getName() {
            return "Aether Recipes";
        }
    }
}
