package com.aetherteam.aether.recipe;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.*;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.aetherteam.nitrogen.fabric.registries.DeferredHolder;
import com.aetherteam.nitrogen.fabric.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class AetherRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Aether.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<? extends AbstractAetherCookingRecipe>> ENCHANTING = RECIPE_TYPES.register("enchanting", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "freezing")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IncubationRecipe>> INCUBATION = RECIPE_TYPES.register("incubation", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "incubation")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "ambrosium_enchanting")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_TYPES.register("swet_ball_conversion", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "swet_ball_conversion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "icestone_freezable")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_TYPES.register("accessory_freezable", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "accessory_freezable")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_TYPES.register("placement_conversion", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "placement_conversion")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_TYPES.register("item_placement_ban", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "item_placement_ban")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_TYPES.register("block_placement_ban", () -> simple(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "block_placement_ban")));

    public static <T extends Recipe<?>> RecipeType<T> simple(ResourceLocation location) {
        return new RecipeType<>() {
            @Override
            public String toString() {
                return location.toString();
            }
        };
    }
}
