package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.BlockStateIngredient;
import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.aether.recipe.BlockStateRecipeUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class PlacementBanRecipeSerializer<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory) {
        this.factory = factory;
    }

    @Override
    public F fromJson(ResourceLocation id, JsonObject json) {
        Pair<ResourceKey<Biome>, TagKey<Biome>> biomeRecipeData = BlockStateRecipeUtil.biomeRecipeDataFromJson(json);
        ResourceKey<Biome> biomeKey = biomeRecipeData.getLeft();
        TagKey<Biome> biomeTag = biomeRecipeData.getRight();

        BlockStateIngredient bypassBlock = BlockStateIngredient.EMPTY;
        if (json.has("bypass")) {
            boolean isBypassArray = GsonHelper.isArrayNode(json, "bypass");
            JsonElement bypassElement = isBypassArray ? GsonHelper.getAsJsonArray(json, "bypass") : GsonHelper.getAsJsonObject(json, "bypass");
            bypassBlock = BlockStateIngredient.fromJson(bypassElement);
        }

        return this.factory.create(id, biomeKey, biomeTag, bypassBlock);
    }

    @Nullable
    @Override
    public F fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
        BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buffer);
        return this.factory.create(id, biomeKey, biomeTag, bypassBlock);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, F recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        recipe.getBypassBlock().toNetwork(buffer);
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> {
        F create(ResourceLocation id, @Nullable ResourceKey<Biome> dimensionTypeKey, @Nullable TagKey<Biome> dimensionTypeTag, BlockStateIngredient bypassBlock);
    }
}
