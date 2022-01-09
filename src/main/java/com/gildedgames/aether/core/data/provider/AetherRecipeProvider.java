package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.common.recipe.builder.AltarRepairBuilder;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;

public class AetherRecipeProvider extends RecipeProvider
{
    public AetherRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    public ShapedRecipeBuilder makeWood(Supplier<? extends Block> woodOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(woodOut.get(), 3)
                .pattern("MM")
                .pattern("MM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapelessRecipeBuilder makePlanks(Supplier<? extends Block> plankOut, Supplier<? extends Block> logIn) {
        return ShapelessRecipeBuilder.shapeless(plankOut.get(), 4)
                .requires(logIn.get())
                .unlockedBy("has_" + logIn.get().getRegistryName().getPath(), has(logIn.get()));
    }

    public ShapedRecipeBuilder makeBricks(Supplier<? extends Block> bricksOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(bricksOut.get(), 4)
                .pattern("MM")
                .pattern("MM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeOreToBlock(Supplier<? extends Block> blockOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(blockOut.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapelessRecipeBuilder makeBlockToOre(Supplier<? extends Item> materialOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapeless(materialOut.get(), 9)
                .requires(blockIn.get())
                .unlockedBy("has_" + blockIn.get().getRegistryName().getPath(), has(blockIn.get()));
    }

    public ShapedRecipeBuilder makeFence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(fenceOut.get(), 3)
                .pattern("M/M")
                .pattern("M/M")
                .define('M', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeFenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(fenceGateOut.get())
                .pattern("/M/")
                .pattern("/M/")
                .define('M', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeWall(Supplier<? extends Block> wallOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(wallOut.get(), 6)
                .pattern("MMM")
                .pattern("MMM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeStairs(Supplier<? extends Block> stairsOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(stairsOut.get(), 4)
                .pattern("M  ")
                .pattern("MM ")
                .pattern("MMM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSlab(Supplier<? extends Block> slabOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(slabOut.get(), 6)
                .pattern("MMM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxeOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(pickaxeOut.get())
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axeOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(axeOut.get())
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovelOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(shovelOut.get())
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeHoeWithBlock(Supplier<? extends Item> hoeOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(hoeOut.get())
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> swordOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(swordOut.get())
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makePickaxe(Supplier<? extends Item> pickaxeOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(pickaxeOut.get())
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeAxe(Supplier<? extends Item> axeOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(axeOut.get())
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeShovel(Supplier<? extends Item> shovelOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(shovelOut.get())
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeHoe(Supplier<? extends Item> shovelOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(shovelOut.get())
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSword(Supplier<? extends Item> swordOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(swordOut.get())
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .define('#', materialIn.get())
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeHelmet(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(helmetOut.get())
                .pattern("MMM")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeChestplate(Supplier<? extends Item> chestplateOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(chestplateOut.get())
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeLeggings(Supplier<? extends Item> leggingsOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(leggingsOut.get())
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeBoots(Supplier<? extends Item> bootsOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(bootsOut.get())
                .pattern("M M")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeGloves(Supplier<? extends Item> glovesOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shaped(glovesOut.get())
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeGlovesWithTag(Supplier<? extends Item> glovesOut, Tags.IOptionalNamedTag<Item> materialTag, String advancementName) {
        return ShapedRecipeBuilder.shaped(glovesOut.get())
                .pattern("M M")
                .define('M', materialTag)
                .unlockedBy("has_" + advancementName, has(materialTag));
    }

    public ShapedRecipeBuilder makeHelmetWithBlock(Supplier<? extends Item> helmetOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(helmetOut.get())
                .pattern("MMM")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeChestplateWithBlock(Supplier<? extends Item> chestplateOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(chestplateOut.get())
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeLeggingsWithBlock(Supplier<? extends Item> leggingsOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(leggingsOut.get())
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeBootsWithBlock(Supplier<? extends Item> bootsOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(bootsOut.get())
                .pattern("M M")
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeGlovesWithBlock(Supplier<? extends Item> glovesOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shaped(glovesOut.get())
                .pattern("M M")
                .define('M', materialIn.get())
                .unlockedBy("has_" + materialIn.get().getRegistryName().getPath(), has(materialIn.get()));
    }

    public ShapedRecipeBuilder makeRing(Supplier<? extends Item> helmetOut, Item materialIn) {
        return ShapedRecipeBuilder.shaped(helmetOut.get())
                .pattern(" M ")
                .pattern("M M")
                .pattern(" M ")
                .define('M', materialIn)
                .unlockedBy("has_" + materialIn.getRegistryName().getPath(), has(materialIn));
    }

    public ShapedRecipeBuilder makePendant(Supplier<? extends Item> helmetOut, Item materialIn) {
        return ShapedRecipeBuilder.shaped(helmetOut.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern(" M ")
                .define('S', Tags.Items.STRING)
                .define('M', materialIn)
                .unlockedBy("has_" + materialIn.getRegistryName().getPath(), has(materialIn));
    }

    public ShapedRecipeBuilder makeCape(Supplier<? extends Item> capeOut, Item materialIn) {
        return ShapedRecipeBuilder.shaped(capeOut.get())
                .pattern("MM")
                .pattern("MM")
                .pattern("MM")
                .define('M', materialIn)
                .unlockedBy("has_" + materialIn.getRegistryName().getPath(), has(materialIn));
    }

    public SimpleCookingRecipeBuilder smeltingRecipe(ItemLike result, ItemLike ingredient, float exp) {
        return SimpleCookingRecipeBuilder.smelting(Ingredient.of(new ItemStack(ingredient, 1)), result, exp, 200)
                .unlockedBy("has_" + ingredient.asItem().getRegistryName(), has(ingredient));
    }

    public SimpleCookingRecipeBuilder blastingRecipe(ItemLike result, ItemLike ingredient, float exp) {
        return SimpleCookingRecipeBuilder.blasting(Ingredient.of(new ItemStack(ingredient, 1)), result, exp, 100)
                .unlockedBy("has_" + ingredient.asItem().getRegistryName(), has(ingredient));
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Supplier<Block> input, ItemLike result) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), result)
                .unlockedBy("has_" + input.get().getRegistryName(), has(input.get()));
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Supplier<Block> input, ItemLike result, int resultAmount) {
        return SingleItemRecipeBuilder.stonecutting(Ingredient.of(input.get()), result, resultAmount)
                .unlockedBy("has_" + input.get().getRegistryName(), has(input.get()));
    }

    public UpgradeRecipeBuilder smithingRecipe(Supplier<Item> input, Supplier<Item> upgradeItem, Supplier<Item> result) {
        return UpgradeRecipeBuilder.smithing(Ingredient.of(input.get()), Ingredient.of(upgradeItem.get()), result.get())
                .unlocks("has_" + upgradeItem.get().getRegistryName(), has(upgradeItem.get()));
    }

    public UpgradeRecipeBuilder smithingRecipeWithTag(Supplier<Item> input, Tags.IOptionalNamedTag<Item> upgradeTag, Supplier<Item> result, String advancementName) {
        return UpgradeRecipeBuilder.smithing(Ingredient.of(input.get()), Ingredient.of(upgradeTag), result.get())
                .unlocks("has_" + advancementName, has(upgradeTag));
    }

    public AltarRepairBuilder repairingRecipe(ItemLike item, int duration) {
        return AltarRepairBuilder.repair(Ingredient.of(new ItemStack(item, 1)), duration, AetherRecipes.REPAIRING.get())
                .unlockedBy("has_" + item.asItem().getRegistryName(), has(item));
    }

    public SimpleCookingRecipeBuilder enchantingRecipe(ItemLike result, ItemLike ingredient, int duration) {
        return SimpleCookingRecipeBuilder.cooking(Ingredient.of(new ItemStack(ingredient, 1)), result, 0.0F, duration, AetherRecipes.ENCHANTING.get())
                .unlockedBy("has_" + ingredient.asItem().getRegistryName(), has(ingredient));
    }

    public SimpleCookingRecipeBuilder enchantingRecipe(ItemLike result, Tag.Named<Item> ingredient, int duration) {
        return SimpleCookingRecipeBuilder.cooking(Ingredient.of(ingredient), result, 0.0F, duration, AetherRecipes.ENCHANTING.get())
                .unlockedBy("has_disc", has(ingredient));
    }

    public SimpleCookingRecipeBuilder freezingRecipe(ItemLike result, ItemLike ingredient, int duration) {
        return SimpleCookingRecipeBuilder.cooking(Ingredient.of(new ItemStack(ingredient, 1)), result, 0.0F, duration, AetherRecipes.FREEZING.get())
                .unlockedBy("has_" + ingredient.asItem().getRegistryName(), has(ingredient));
    }
}
