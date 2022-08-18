package com.gildedgames.aether.recipe;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.recipe.recipes.ban.*;
import com.gildedgames.aether.recipe.recipes.block.*;
import com.gildedgames.aether.recipe.recipes.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AetherRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Aether.MODID);

    public static RegistryObject<RecipeType<EnchantingRecipe>> ENCHANTING = RECIPE_TYPES.register("enchanting", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "enchanting")));
    public static RegistryObject<RecipeType<FreezingRecipe>> FREEZING = RECIPE_TYPES.register("freezing", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "freezing")));
    public static RegistryObject<RecipeType<IncubationRecipe>> INCUBATION = RECIPE_TYPES.register("incubation", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "incubation")));
    public static RegistryObject<RecipeType<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_TYPES.register("ambrosium_enchanting", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "ambrosium_enchanting")));
    public static RegistryObject<RecipeType<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_TYPES.register("swet_ball_conversion", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "swet_ball_conversion")));
    public static RegistryObject<RecipeType<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_TYPES.register("icestone_freezable", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "icestone_freezable")));
    public static RegistryObject<RecipeType<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_TYPES.register("accessory_freezable", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "accessory_freezable")));
    public static RegistryObject<RecipeType<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_TYPES.register("placement_conversion", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "placement_conversion")));
    public static RegistryObject<RecipeType<ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_TYPES.register("item_placement_ban", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "item_placement_ban")));
    public static RegistryObject<RecipeType<BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_TYPES.register("block_placement_ban", () -> RecipeType.simple(new ResourceLocation(Aether.MODID, "block_placement_ban")));
}
