package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.BlockBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class BlockBanBuilder extends PlacementBanBuilder {
    private final BlockStateIngredient ingredient;

    public BlockBanBuilder(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome) {
        super(bypassBlock, biome);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(BlockStateIngredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome) {
        return new BlockBanBuilder(ingredient, bypassBlock, biome);
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        output.accept(id, new BlockBanRecipe(this.getBiome(), this.getBypassBlock(), this.ingredient), null);
    }
}