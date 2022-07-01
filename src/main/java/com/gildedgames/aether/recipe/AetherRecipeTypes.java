package com.gildedgames.aether.recipe;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.recipe.recipes.EnchantingRecipe;
import com.gildedgames.aether.recipe.recipes.FreezingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Aether.MODID);
    public static RegistryObject<RecipeType<EnchantingRecipe>> ENCHANTING = RECIPE_TYPES.register("enchanting", () -> new RecipeType<>() {
        public String toString() {
            return "enchanting";
        }
    });
    public static RegistryObject<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> new RecipeType<>() {
        public String toString() {
            return "freezing";
        }
    });
}
