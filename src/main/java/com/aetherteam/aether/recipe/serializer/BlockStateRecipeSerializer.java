package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.BlockPropertyPair;
import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.block.AbstractBlockStateRecipe;
import com.aetherteam.aether.recipe.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.commands.CommandFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public class BlockStateRecipeSerializer<T extends AbstractBlockStateRecipe> implements RecipeSerializer<T> {
    private final BlockStateRecipeSerializer.CookieBaker<T> factory;

    public BlockStateRecipeSerializer(BlockStateRecipeSerializer.CookieBaker<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        if (!json.has("ingredient")) throw new JsonSyntaxException("Missing ingredient, expected to find an object or array");
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);

        BlockPropertyPair result;
        if (!json.has("result")) {
            throw new JsonSyntaxException("Missing result, expected to find a string or object");
        }
        if (json.get("result").isJsonObject()) {
            JsonObject resultObject = json.getAsJsonObject("result");
            result = BlockStateRecipeUtil.pairFromJson(resultObject);
        } else {
            throw new JsonSyntaxException("Expected result to be object");
        }

        String functionString = GsonHelper.getAsString(json, "mcfunction", null);
        ResourceLocation functionLocation = functionString == null ? null : new ResourceLocation(functionString);
        CommandFunction.CacheableFunction function = functionLocation == null ? CommandFunction.CacheableFunction.NONE : new CommandFunction.CacheableFunction(functionLocation);

        return this.factory.create(id, ingredient, result, function);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        CommandFunction.CacheableFunction function = BlockStateRecipeUtil.readFunction(buffer);
        return this.factory.create(id, ingredient, result, function);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        recipe.getIngredient().toNetwork(buffer);
        BlockStateRecipeUtil.writePair(buffer, recipe.getResult());
        CommandFunction.CacheableFunction function = recipe.getFunction();
        buffer.writeUtf(function != null && function.getId() != null ? function.getId().toString() : "");
    }

    public interface CookieBaker<T extends AbstractBlockStateRecipe> {
        T create(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function);
    }
}
