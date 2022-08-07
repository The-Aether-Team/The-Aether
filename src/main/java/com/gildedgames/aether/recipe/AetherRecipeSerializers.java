package com.gildedgames.aether.recipe;

import com.gildedgames.aether.Aether;

import com.gildedgames.aether.recipe.recipes.ban.BlockBanRecipe;
import com.gildedgames.aether.recipe.recipes.ban.ItemBanRecipe;
import com.gildedgames.aether.recipe.recipes.block.AccessoryFreezableRecipe;
import com.gildedgames.aether.recipe.recipes.block.IcestoneFreezableRecipe;
import com.gildedgames.aether.recipe.recipes.block.PlacementConversionRecipe;
import com.gildedgames.aether.recipe.recipes.block.SwetBallRecipe;
import com.gildedgames.aether.recipe.recipes.item.AltarRepairRecipe;
import com.gildedgames.aether.recipe.recipes.item.EnchantingRecipe;
import com.gildedgames.aether.recipe.recipes.item.FreezingRecipe;
import com.gildedgames.aether.recipe.recipes.item.IncubationRecipe;
import com.gildedgames.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.recipe.serializer.PlacementBanRecipeSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraftforge.registries.RegistryObject;

public class AetherRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Aether.MODID);

	public static final RegistryObject<RecipeSerializer<AltarRepairRecipe>> REPAIRING = RECIPE_SERIALIZERS.register("repairing", AltarRepairRecipe.Serializer::new);
	public static final RegistryObject<SimpleCookingSerializer<EnchantingRecipe>> ENCHANTING = RECIPE_SERIALIZERS.register("enchanting", EnchantingRecipe.Serializer::new);
	public static final RegistryObject<SimpleCookingSerializer<FreezingRecipe>> FREEZING = RECIPE_SERIALIZERS.register("freezing", FreezingRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<IncubationRecipe>> INCUBATION = RECIPE_SERIALIZERS.register("incubation", IncubationRecipe.Serializer::new);
	public static final RegistryObject<BiomeParameterRecipeSerializer<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_SERIALIZERS.register("swet_ball_conversion", SwetBallRecipe.Serializer::new);
	public static final RegistryObject<BlockStateRecipeSerializer<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_SERIALIZERS.register("icestone_freezable", IcestoneFreezableRecipe.Serializer::new);
	public static final RegistryObject<BlockStateRecipeSerializer<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_SERIALIZERS.register("accessory_freezable", AccessoryFreezableRecipe.Serializer::new);
	public static final RegistryObject<BiomeParameterRecipeSerializer<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_SERIALIZERS.register("placement_conversion", PlacementConversionRecipe.Serializer::new);
	public static final RegistryObject<PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("item_placement_ban", ItemBanRecipe.Serializer::new);
	public static final RegistryObject<PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("block_placement_ban", BlockBanRecipe.Serializer::new);
}
