package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import com.google.gson.JsonObject;
import net.minecraft.commands.CommandFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

public class BiomeParameterRecipeSerializer<T extends AbstractBiomeParameterRecipe> extends BlockStateRecipeSerializer<T> {
    private final BiomeParameterRecipeSerializer.CookieBaker<T> factory;

    public BiomeParameterRecipeSerializer(BiomeParameterRecipeSerializer.CookieBaker<T> factory, BlockStateRecipeSerializer.CookieBaker<T> superFactory) {
        super(superFactory);
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject json) {
        Pair<ResourceKey<Biome>, TagKey<Biome>> biomeRecipeData = BlockStateRecipeUtil.biomeRecipeDataFromJson(json);
        ResourceKey<Biome> biomeKey = biomeRecipeData.getLeft();
        TagKey<Biome> biomeTag = biomeRecipeData.getRight();
        T recipe = super.fromJson(id, json);
        return this.factory.create(id, biomeKey, biomeTag, recipe.getIngredient(), recipe.getResult(), recipe.getFunction());
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
        BlockStateIngredient ingredient = BlockStateIngredient.fromNetwork(buffer);
        BlockPropertyPair result = BlockStateRecipeUtil.readPair(buffer);
        CommandFunction.CacheableFunction function = BlockStateRecipeUtil.readFunction(buffer);
        return this.factory.create(id, biomeKey, biomeTag, ingredient, result, function);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        super.toNetwork(buffer, recipe);
    }

    public interface CookieBaker<T extends AbstractBiomeParameterRecipe> {
        T create(ResourceLocation id, @Nullable ResourceKey<Biome> biomeKey, @Nullable TagKey<Biome> biomeTag, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function);
    }
}
