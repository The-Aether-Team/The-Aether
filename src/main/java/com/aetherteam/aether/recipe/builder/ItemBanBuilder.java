package com.aetherteam.aether.recipe.builder;

import com.aetherteam.aether.recipe.recipes.ban.ItemBanRecipe;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class ItemBanBuilder extends PlacementBanBuilder {
    private final Ingredient ingredient;

    public ItemBanBuilder(Ingredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome) {
        super(bypassBlock, biome);
        this.ingredient = ingredient;
    }

    public static PlacementBanBuilder recipe(Ingredient ingredient, Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome) {
        return new ItemBanBuilder(ingredient, bypassBlock, biome);
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        output.accept(id, new ItemBanRecipe(this.getBiome(), this.getBypassBlock(), this.ingredient), null);
    }
}
