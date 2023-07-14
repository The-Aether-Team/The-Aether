package com.aetherteam.aether.recipe;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.*;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Aether.MODID);

    public static final RegistryObject<RecipeType<EnchantingRecipe>> ENCHANTING = RECIPE_TYPES.register("enchanting", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "enchanting")));
    public static final RegistryObject<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "freezing")));
    public static final RegistryObject<RecipeType<IncubationRecipe>> INCUBATION = RECIPE_TYPES.register("incubation", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "incubation")));
    public static final RegistryObject<RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "ambrosium_enchanting")));
    public static final RegistryObject<RecipeType<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_TYPES.register("swet_ball_conversion", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "swet_ball_conversion")));
    public static final RegistryObject<RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "icestone_freezable")));
    public static final RegistryObject<RecipeType<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_TYPES.register("accessory_freezable", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "accessory_freezable")));
    public static final RegistryObject<RecipeType<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_TYPES.register("placement_conversion", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "placement_conversion")));
    public static final RegistryObject<RecipeType<ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_TYPES.register("item_placement_ban", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "item_placement_ban")));
    public static final RegistryObject<RecipeType<BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_TYPES.register("block_placement_ban", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "block_placement_ban")));
}
