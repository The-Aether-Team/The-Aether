package com.gildedgames.aether.common.recipe.util;

import com.gildedgames.aether.core.util.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

public class BlockStateRecipeSerializer<T extends AbstractBlockStateRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
    private final BlockStateRecipeSerializer.CookieBaker<T> factory;

    public BlockStateRecipeSerializer(BlockStateRecipeSerializer.CookieBaker<T> factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public T fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
        JsonElement jsonElement = GsonHelper.isArrayNode(serializedRecipe, "ingredient") ? GsonHelper.getAsJsonArray(serializedRecipe, "ingredient") : GsonHelper.getAsJsonObject(serializedRecipe, "ingredient");
        BlockStateIngredient ingredient = BlockStateIngredient.fromJson(jsonElement);

        if (!serializedRecipe.has("result")) throw new JsonSyntaxException("Missing result, expected to find a string or object");
        Block blockResult;
        Map<Property<?>, Comparable<?>> propertiesResult;
        if (serializedRecipe.get("result").isJsonObject()) {
            JsonObject object = serializedRecipe.getAsJsonObject("result");
            blockResult = BlockStateRecipeUtil.blockFromJson(object);
            propertiesResult = BlockStateRecipeUtil.propertiesFromJson(object, blockResult); //TODO: Verify
        } else {
            throw new JsonSyntaxException("Expected result to be object");
        }

        return this.factory.create(recipeId, ingredient, blockResult, propertiesResult);
    }

    @Nullable
    @Override
    public T fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
        Block blockResult = BlockStateRecipeUtil.readBlock(buf);
        Map<Property<?>, Comparable<?>> propertiesResult = BlockStateRecipeUtil.readProperties(buf, blockResult);
        return this.factory.create(recipeId, ingredient, blockResult, propertiesResult);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buf, T recipe) {
        recipe.ingredient.toNetwork(buf);
        BlockStateRecipeUtil.writeBlock(buf, recipe.resultBlock);
        BlockStateRecipeUtil.writeProperties(buf, recipe.resultProperties);
    }

    public interface CookieBaker<T extends AbstractBlockStateRecipe> {
        T create(ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties);
    }
}
