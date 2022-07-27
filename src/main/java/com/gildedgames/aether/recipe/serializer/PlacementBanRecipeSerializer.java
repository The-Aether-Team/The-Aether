package com.gildedgames.aether.recipe.serializer;

import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.AbstractPlacementBanRecipe;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class PlacementBanRecipeSerializer<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public F fromJson(@Nonnull ResourceLocation recipeId, JsonObject serializedRecipe) {
        ResourceKey<Biome> biomeKey = null;
        TagKey<Biome> biomeTag = null;
        if (serializedRecipe.has("biome")) {
            String biomeName = GsonHelper.getAsString(serializedRecipe, "biome");
            if (biomeName.startsWith("#")) {
                biomeTag = BlockStateRecipeUtil.biomeTagFromJson(serializedRecipe);
            } else {
                biomeKey = BlockStateRecipeUtil.biomeKeyFromJson(serializedRecipe);
            }
        }
        BlockStateIngredient bypassBlock = BlockStateIngredient.EMPTY;
        if (serializedRecipe.has("bypass")) {
            boolean isBypassArray = GsonHelper.isArrayNode(serializedRecipe, "bypass");
            JsonElement bypassElement = isBypassArray ? GsonHelper.getAsJsonArray(serializedRecipe, "bypass") : GsonHelper.getAsJsonObject(serializedRecipe, "bypass");
            bypassBlock = BlockStateIngredient.fromJson(bypassElement);
        }
        return this.factory.create(recipeId, biomeKey, biomeTag, bypassBlock);
    }

    @Nullable
    @Override
    public F fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
        BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
        return this.factory.create(recipeId, biomeKey, biomeTag, bypassBlock);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buf, F recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getBiomeTag());
        recipe.getBypassBlock().toNetwork(buf);
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> {
        F create(ResourceLocation id, @Nullable ResourceKey<Biome> dimensionTypeKey, @Nullable TagKey<Biome> dimensionTypeTag, BlockStateIngredient bypassBlock);
    }
}
