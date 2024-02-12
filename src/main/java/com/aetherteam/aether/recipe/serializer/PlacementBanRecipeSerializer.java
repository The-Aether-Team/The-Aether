package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class PlacementBanRecipeSerializer<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory) {
        this.factory = factory;
    }

    @Nullable
    @Override
    public F fromNetwork(FriendlyByteBuf buffer) {
        ResourceKey<Biome> biomeKey = BlockStateRecipeUtil.readBiomeKey(buffer);
        TagKey<Biome> biomeTag = BlockStateRecipeUtil.readBiomeTag(buffer);
        BlockStateIngredient bypassBlock = BlockStateIngredient.fromNetwork(buffer);
        return this.factory.create(biomeKey, biomeTag, bypassBlock);
    }

    @Override
    public Codec<F> codec() {
        return null;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, F recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        recipe.getBypassBlock().toNetwork(buffer);
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> {
        F create(@Nullable ResourceKey<Biome> dimensionTypeKey, @Nullable TagKey<Biome> dimensionTypeTag, BlockStateIngredient bypassBlock);
    }
}
