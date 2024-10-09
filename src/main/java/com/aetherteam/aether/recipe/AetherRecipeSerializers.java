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
import com.aetherteam.nitrogen.recipe.input.BlockStateRecipeInput;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AetherRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Aether.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AltarRepairRecipe>> REPAIRING = RECIPE_SERIALIZERS.register("repairing", AltarRepairRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, AetherCookingSerializer<EnchantingRecipe>> ENCHANTING = RECIPE_SERIALIZERS.register("enchanting", EnchantingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, AetherCookingSerializer<FreezingRecipe>> FREEZING = RECIPE_SERIALIZERS.register("freezing", FreezingRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IncubationRecipe>> INCUBATION = RECIPE_SERIALIZERS.register("incubation", IncubationRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<AmbrosiumRecipe>> AMBROSIUM_ENCHANTING = RECIPE_SERIALIZERS.register("ambrosium_enchanting", AmbrosiumRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BiomeParameterRecipeSerializer<SwetBallRecipe>> SWET_BALL_CONVERSION = RECIPE_SERIALIZERS.register("swet_ball_conversion", SwetBallRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<IcestoneFreezableRecipe>> ICESTONE_FREEZABLE = RECIPE_SERIALIZERS.register("icestone_freezable", IcestoneFreezableRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BlockStateRecipeSerializer<AccessoryFreezableRecipe>> ACCESSORY_FREEZABLE = RECIPE_SERIALIZERS.register("accessory_freezable", AccessoryFreezableRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, BiomeParameterRecipeSerializer<PlacementConversionRecipe>> PLACEMENT_CONVERSION = RECIPE_SERIALIZERS.register("placement_conversion", PlacementConversionRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, PlacementBanRecipeSerializer<ItemStack, Ingredient, SingleRecipeInput, ItemBanRecipe>> ITEM_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("item_placement_ban", ItemBanRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>, PlacementBanRecipeSerializer<BlockState, BlockStateIngredient, BlockStateRecipeInput, BlockBanRecipe>> BLOCK_PLACEMENT_BAN = RECIPE_SERIALIZERS.register("block_placement_ban", BlockBanRecipe.Serializer::new);
}
