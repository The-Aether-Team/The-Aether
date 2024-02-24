package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.mojang.datafixers.util.Function4;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;
import java.util.function.Predicate;

public abstract class PlacementBanRecipeSerializer<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, F> factory) {
        this.factory = factory;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, F recipe) {
        BlockStateRecipeUtil.writeBiomeKey(buffer, recipe.getBiomeKey());
        BlockStateRecipeUtil.writeBiomeTag(buffer, recipe.getBiomeTag());
        recipe.getBypassBlock().toNetwork(buffer);
    }

    public CookieBaker<T, S, F> getFactory() {
        return this.factory;
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> extends Function4<Optional<ResourceKey<Biome>>, Optional<TagKey<Biome>>, BlockStateIngredient, S, F> {
        @Override
        F apply(Optional<ResourceKey<Biome>> dimensionTypeKey, Optional<TagKey<Biome>> dimensionTypeTag, BlockStateIngredient bypassBlock, S ingredient);
    }
}
