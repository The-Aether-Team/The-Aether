package com.gildedgames.aether.recipe.serializer;

import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.gildedgames.aether.util.BlockStateRecipeUtil;
import com.google.gson.JsonObject;
import net.minecraft.commands.CommandFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final BiomeParameterRecipeSerializer.CookieBaker<T> factory;

    public BiomeParameterRecipeSerializer(BiomeParameterRecipeSerializer.CookieBaker<T> factory, BlockStateRecipeSerializer.CookieBaker<T> superFactory) {
        super(superFactory);
        this.factory = factory;
    }

    @Nonnull
    @Override
    public T fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject serializedRecipe) {
        Pair<ResourceKey<Biome>, TagKey<Biome>> biomeRecipeData = BlockStateRecipeUtil.biomeRecipeDataFromJson(serializedRecipe);
        ResourceKey<Biome> biomeKey = biomeRecipeData.getLeft();
        TagKey<Biome> biomeTag = biomeRecipeData.getRight();
        T recipe = super.fromJson(recipeId, serializedRecipe);
        return this.factory.create(recipeId, biomeKey, biomeTag, recipe.getIngredient(), recipe.getResult(), recipe.getMcfunction());
    }

    @Nullable
    @Override
    public T fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buf) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buf);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buf);
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buf);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buf);
        return this.factory.create(recipeId, biomeKey, biomeTag, ingredient, result, CommandFunction.CacheableFunction.NONE);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buf, T recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buf, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buf, recipe.getBiomeTag());
        super.toNetwork(buf, recipe);
    }

    public interface CookieBaker<T extends AbstractBiomeParameterRecipe> {
        T create(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function);
    }
}
