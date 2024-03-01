package com.aetherteam.aether.recipe.serializer;

import com.aetherteam.aether.recipe.recipes.ban.AbstractPlacementBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Function3;
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
        buffer.writeEither(recipe.getBiome(), (buf, left) -> buf.writeResourceLocation(left.location()), (buf, right) -> buf.writeResourceLocation(right.location()));
        buffer.writeOptional(recipe.getBypassBlock(), (buf, blockStateIngredient) -> blockStateIngredient.toNetwork(buf));
    }

    public CookieBaker<T, S, F> getFactory() {
        return this.factory;
    }

    public interface CookieBaker<T, S extends Predicate<T>, F extends AbstractPlacementBanRecipe<T, S>> extends Function3<Either<ResourceKey<Biome>, TagKey<Biome>>, Optional<BlockStateIngredient>, S, F> {
        @Override
        F apply(Either<ResourceKey<Biome>, TagKey<Biome>> biome, Optional<BlockStateIngredient> bypassBlock, S ingredient);
    }
}
