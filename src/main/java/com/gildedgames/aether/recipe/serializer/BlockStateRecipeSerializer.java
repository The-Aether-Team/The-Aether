package com.gildedgames.aether.recipe.serializer;

import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.block.AbstractBlockStateRecipe;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.commands.CommandFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockStateRecipeSerializer<T extends AbstractBlockStateRecipe> implements RecipeSerializer<T> {
    private final BlockStateRecipeSerializer.CookieBaker<T> factory;

    public BlockStateRecipeSerializer(BlockStateRecipeSerializer.CookieBaker<T> factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public T fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
        if (!serializedRecipe.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
        JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
        BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);

        if (!serializedRecipe.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
        BlockPropertyPair result;
        if (serializedRecipe.get("result").isJsonObject()) {
            JsonObject resultObject = serializedRecipe.getAsJsonObject("result");
            result = BlockStateRecipeUtil.pairFromJson(resultObject);
        } else {
            throw new JsonSyntaxException("Expected result to be object");
        }

        String functionString = GsonHelper.getAsString(serializedRecipe, "mcfunction", null);
        ResourceLocation functionLocation = functionString == null ? null : new ResourceLocation(functionString);
        CommandFunction.CacheableFunction function = functionLocation == null ? CommandFunction.CacheableFunction.NONE : new CommandFunction.CacheableFunction(functionLocation);

        return this.factory.create(recipeId, ingredient, result, function);
    }

    @Nullable
    @Override
    public T fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buf);
        CommandFunction.CacheableFunction function = BlockStateRecipeUtil.readFunction(buf);
        return this.factory.create(recipeId, ingredient, result, function);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buf, T recipe) {
        recipe.getIngredient().toNetwork(buf);
        BlockStateRecipeUtil.writePair(buf, recipe.getResult());
        CommandFunction.CacheableFunction function = recipe.getFunction();
        buf.writeUtf(function != null && function.getId() != null ? function.getId().toString() : "");
    }

    public interface CookieBaker<T extends AbstractBlockStateRecipe> {
        T create(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function);
    }
}
