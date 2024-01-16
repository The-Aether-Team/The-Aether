package com.aetherteam.aether.recipe;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.aether.recipe.recipes.block.*;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.aetherteam.aether.recipe.recipes.item.EnchantingRecipe;
import com.aetherteam.aether.recipe.recipes.item.FreezingRecipe;
import com.aetherteam.aether.recipe.recipes.item.IncubationRecipe;
import com.aetherteam.aether.recipe.serializer.AetherCookingSerializer;
import com.aetherteam.aether.recipe.serializer.BiomeParameterRecipeSerializer;
import com.aetherteam.aether.recipe.serializer.PlacementBanRecipeSerializer;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;

public class AetherRecipeSerializers {
	public static final LazyRegistrar<RecipeSerializer<?>> RECIPE_SERIALIZERS = LazyRegistrar.create(Registries.RECIPE_SERIALIZER, Aether.MODID);

	public static final RegistryObject<RecipeSerializer<AltarRepairRecipe>> REPAIRING = RECIPE_SERIALIZERS.register("repairing", AltarRepairRecipe.Serializer::new);
	public static final RegistryObject<AetherCookingSerializer<EnchantingRecipe>> ENCHANTING = RECIPE_SERIALIZERS.register("enchanting", EnchantingRecipe.Serializer::new);
	public static final RegistryObject<AetherCookingSerializer<FreezingRecipe>> FREEZING = RECIPE_SERIALIZERS.register("freezing", FreezingRecipe.Serializer::new);
	public static final RegistryObject<RecipeSerializer<IncubationRecipe>> INCUBATION = RECIPE_SERIALIZERS.register("incubation", IncubationRecipe.Serializer::new);
	public static final RegistryObject<BlockStateRecipeSerializer<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_SERIALIZERS.register("ambrosium_enchanting", AmbrosiumRecipe.Serializer::new);
	public static final RegistryObject<BiomeParameterRecipeSerializer<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_SERIALIZERS.register("swet_ball_conversion", SwetBallRecipe.Serializer::new);
	public static final RegistryObject<BlockStateRecipeSerializer<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_SERIALIZERS.register("icestone_freezable", IcestoneFreezableRecipe.Serializer::new);
	public static final RegistryObject<BlockStateRecipeSerializer<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_SERIALIZERS.register("accessory_freezable", AccessoryFreezableRecipe.Serializer::new);
	public static final RegistryObject<BiomeParameterRecipeSerializer<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_SERIALIZERS.register("placement_conversion", PlacementConversionRecipe.Serializer::new);
	public static final RegistryObject<PlacementBanRecipeSerializer<ItemStack, Ingredient, ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("item_placement_ban", ItemBanRecipe.Serializer::new);
	public static final RegistryObject<PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("block_placement_ban", BlockBanRecipe.Serializer::new);
}
