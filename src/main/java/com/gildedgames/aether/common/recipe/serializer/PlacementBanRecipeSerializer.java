package com.gildedgames.aether.common.recipe.serializer;

import com.gildedgames.aether.common.recipe.AbstractPlacementBanRecipe;
import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class PlacementBanRecipeSerializer<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public F fromJson(@Nonnull ResourceLocation recipeId, JsonObject serializedRecipe) {
        ResourceKey<DimensionType> dimensionTypeKey = null;
        TagKey<DimensionType> dimensionTypeTag = null;
        if (serializedRecipe.has("dimension_type")) {
            String biomeName = GsonHelper.getAsString(serializedRecipe, "dimension_type");
            if (biomeName.startsWith("#")) {
                dimensionTypeTag = BlockStateRecipeUtil.dimensionTypeTagFromJson(serializedRecipe);
            } else {
                dimensionTypeKey = BlockStateRecipeUtil.dimensionTypeKeyFromJson(serializedRecipe);
            }
        }
        BlockStateIngredient bypassBlock = BlockStateIngredient.EMPTY;
        if (serializedRecipe.has("bypass")) {
            boolean isBypassArray = GsonHelper.isArrayNode(serializedRecipe, "bypass");
            JsonElement bypassElement = isBypassArray ? GsonHelper.getAsJsonArray(serializedRecipe, "bypass") : GsonHelper.getAsJsonObject(serializedRecipe, "bypass");
            bypassBlock = BlockStateIngredient.fromJson(bypassElement);
        }
        return this.factory.create(recipeId, dimensionTypeKey, dimensionTypeTag, bypassBlock);
    }

    @Nullable
    @Override
    public F fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
        ResourceKey<DimensionType> dimensionTypeKey = BlockStateRecipeUtil.readDimensionTypeKey(buf);
        TagKey<DimensionType> dimensionTypeTag = BlockStateRecipeUtil.readDimensionTypeTag(buf);
        BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buf);
        return this.factory.create(recipeId, dimensionTypeKey, dimensionTypeTag, bypassBlock);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buf, F recipe) {
        BlockStateRecipeUtil.writeDimensionTypeKey(buf, recipe.getDimensionTypeKey());
        BlockStateRecipeUtil.writeDimensionTypeTag(buf, recipe.getDimensionTypeTag());
        recipe.getBypassBlock().toNetwork(buf);
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> {
        F create(ResourceLocation id, @Nullable ResourceKey<DimensionType> dimensionTypeKey, @Nullable TagKey<DimensionType> dimensionTypeTag, BlockStateIngredient bypassBlock);
    }
}
