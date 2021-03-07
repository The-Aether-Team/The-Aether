package com.gildedgames.aether.core.data.provider;

import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public class AetherRecipeProvider extends RecipeProvider
{
    public AetherRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    public ShapedRecipeBuilder makeWood(Supplier<? extends Block> woodOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(woodOut.get(), 3)
                .patternLine("MM")
                .patternLine("MM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapelessRecipeBuilder makePlanks(Supplier<? extends Block> plankOut, Supplier<? extends Block> logIn) {
        return ShapelessRecipeBuilder.shapelessRecipe(plankOut.get(), 4)
                .addIngredient(logIn.get())
                .addCriterion("has_" + logIn.get().getRegistryName().getPath(), hasItem(logIn.get()));
    }

    public ShapedRecipeBuilder makeBricks(Supplier<? extends Block> bricksOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(bricksOut.get(), 4)
                .patternLine("MM")
                .patternLine("MM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeOreToBlock(Supplier<? extends Block> blockOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(blockOut.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .key('#', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapelessRecipeBuilder makeBlockToOre(Supplier<? extends Item> materialOut, Supplier<? extends Block> blockIn) {
        return ShapelessRecipeBuilder.shapelessRecipe(materialOut.get(), 9)
                .addIngredient(blockIn.get())
                .addCriterion("has_" + blockIn.get().getRegistryName().getPath(), hasItem(blockIn.get()));
    }

    public ShapedRecipeBuilder makeFence(Supplier<? extends Block> fenceOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(fenceOut.get(), 3)
                .patternLine("M/M")
                .patternLine("M/M")
                .key('M', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeFenceGate(Supplier<? extends Block> fenceGateOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(fenceGateOut.get())
                .patternLine("/M/")
                .patternLine("/M/")
                .key('M', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeWall(Supplier<? extends Block> wallOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(wallOut.get(), 6)
                .patternLine("MMM")
                .patternLine("MMM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeStairs(Supplier<? extends Block> stairsOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(stairsOut.get(), 4)
                .patternLine("M  ")
                .patternLine("MM ")
                .patternLine("MMM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSlab(Supplier<? extends Block> slabOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(slabOut.get(), 6)
                .patternLine("MMM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makePickaxeWithBlock(Supplier<? extends Item> pickaxeOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(pickaxeOut.get())
                .patternLine("###")
                .patternLine(" / ")
                .patternLine(" / ")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeAxeWithBlock(Supplier<? extends Item> axeOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(axeOut.get())
                .patternLine("##")
                .patternLine("#/")
                .patternLine(" /")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeShovelWithBlock(Supplier<? extends Item> shovelOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(shovelOut.get())
                .patternLine("#")
                .patternLine("/")
                .patternLine("/")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSwordWithBlock(Supplier<? extends Item> swordOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(swordOut.get())
                .patternLine("#")
                .patternLine("#")
                .patternLine("/")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makePickaxe(Supplier<? extends Item> pickaxeOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(pickaxeOut.get())
                .patternLine("###")
                .patternLine(" / ")
                .patternLine(" / ")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeAxe(Supplier<? extends Item> axeOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(axeOut.get())
                .patternLine("##")
                .patternLine("#/")
                .patternLine(" /")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeShovel(Supplier<? extends Item> shovelOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(shovelOut.get())
                .patternLine("#")
                .patternLine("/")
                .patternLine("/")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeSword(Supplier<? extends Item> swordOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(swordOut.get())
                .patternLine("#")
                .patternLine("#")
                .patternLine("/")
                .key('#', materialIn.get())
                .key('/', AetherTags.Items.SKYROOT_STICKS)
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeHelmet(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("MMM")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeChestplate(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("M M")
                .patternLine("MMM")
                .patternLine("MMM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeLeggings(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("MMM")
                .patternLine("M M")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeBoots(Supplier<? extends Item> helmetOut, Supplier<? extends Item> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("M M")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeHelmetWithBlock(Supplier<? extends Item> helmetOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("MMM")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeChestplateWithBlock(Supplier<? extends Item> helmetOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("M M")
                .patternLine("MMM")
                .patternLine("MMM")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeLeggingsWithBlock(Supplier<? extends Item> helmetOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("MMM")
                .patternLine("M M")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeBootsWithBlock(Supplier<? extends Item> helmetOut, Supplier<? extends Block> materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine("M M")
                .patternLine("M M")
                .key('M', materialIn.get())
                .addCriterion("has_" + materialIn.get().getRegistryName().getPath(), hasItem(materialIn.get()));
    }

    public ShapedRecipeBuilder makeRing(Supplier<? extends Item> helmetOut, Item materialIn) {
        return ShapedRecipeBuilder.shapedRecipe(helmetOut.get())
                .patternLine(" M ")
                .patternLine("M M")
                .patternLine(" M ")
                .key('M', materialIn)
                .addCriterion("has_" + materialIn.getRegistryName().getPath(), hasItem(materialIn));
    }

    public CookingRecipeBuilder smeltingRecipe(IItemProvider result, IItemProvider ingredient, float exp) {
        return CookingRecipeBuilder.smeltingRecipe(Ingredient.fromStacks(new ItemStack(ingredient, 1)), result, exp, 200)
                .addCriterion("has_" + ingredient.asItem().getRegistryName(), hasItem(ingredient));
    }

    public CookingRecipeBuilder blastingRecipe(IItemProvider result, IItemProvider ingredient, float exp) {
        return CookingRecipeBuilder.blastingRecipe(Ingredient.fromStacks(new ItemStack(ingredient, 1)), result, exp, 100)
                .addCriterion("has_" + ingredient.asItem().getRegistryName(), hasItem(ingredient));
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Supplier<Block> input, IItemProvider result) {
        return SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(input.get()), result)
                .addCriterion("has_" + input.get().getRegistryName(), hasItem(input.get()));
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Supplier<Block> input, IItemProvider result, int resultAmount) {
        return SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(input.get()), result, resultAmount)
                .addCriterion("has_" + input.get().getRegistryName(), hasItem(input.get()));
    }

    public CookingRecipeBuilder enchantingRecipe(IItemProvider result, IItemProvider ingredient, int duration) {
        return CookingRecipeBuilder.cookingRecipe(Ingredient.fromStacks(new ItemStack(ingredient, 1)), result, 0.0F, duration, AetherRecipes.ENCHANTING.get())
                .addCriterion("has_" + ingredient.asItem().getRegistryName(), hasItem(ingredient));
    }

    public CookingRecipeBuilder enchantingRecipe(IItemProvider result, ITag.INamedTag<Item> ingredient, int duration) {
        return CookingRecipeBuilder.cookingRecipe(Ingredient.fromTag(ingredient), result, 0.0F, duration, AetherRecipes.ENCHANTING.get())
                .addCriterion("has_disc", hasItem(ingredient));
    }

    public CookingRecipeBuilder freezingRecipe(IItemProvider result, IItemProvider ingredient, int duration) {
        return CookingRecipeBuilder.cookingRecipe(Ingredient.fromStacks(new ItemStack(ingredient, 1)), result, 0.0F, duration, AetherRecipes.FREEZING.get())
                .addCriterion("has_" + ingredient.asItem().getRegistryName(), hasItem(ingredient));
    }
}
