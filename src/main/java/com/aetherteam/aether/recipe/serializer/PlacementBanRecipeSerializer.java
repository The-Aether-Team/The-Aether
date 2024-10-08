package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.BlockStateRecipeUtil;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;
import java.util.function.Predicate;

public abstract class PlacementBanRecipeSerializer<T, S extends Predicate<T>, R extends RecipeInput, F extends AbstractPlacementBanRecipe<T, S, R>> implements RecipeSerializer<F> {
    private final PlacementBanRecipeSerializer.CookieBaker<T, S, R, F> factory;

    public PlacementBanRecipeSerializer(PlacementBanRecipeSerializer.CookieBaker<T, S, R, F> factory) {
        this.factory = factory;
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, F recipe) {
        BlockStateRecipeUtil.STREAM_CODEC.encode(buffer, recipe.getBiome());
        buffer.writeOptional(recipe.getBypassBlock(), (buf, blockStateIngredient) -> BlockStateIngredient.CONTENTS_STREAM_CODEC.encode((RegistryFriendlyByteBuf) buf, blockStateIngredient));
    }

    public CookieBaker<T, S, R, F> getFactory() {
        return this.factory;
    }

    public interface CookieBaker<T, S extends Predicate<T>, R extends RecipeInput, F extends AbstractPlacementBanRecipe<T, S, R>> extends Function3<Either<ResourceKey<Biome>, TagKey<Biome>>, Optional<BlockStateIngredient>, S, F> {
        @Override
        F apply(Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, S ingredient);
    }
}
