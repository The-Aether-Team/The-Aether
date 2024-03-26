package com.aetherteam.aether.recipe.builder;

import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class PlacementBanBuilder implements RecipeBuilder {
    private final Optional<BlockStateIngredient> bypassBlock;
    private final Either<ResourceKey<Biome>, TagKey<Biome>> biome;

    public PlacementBanBuilder(Optional<BlockStateIngredient> bypassBlock, Either<ResourceKey<Biome>, TagKey<Biome>> biome) {
        this.bypassBlock = bypassBlock;
        this.biome = biome;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    public Optional<BlockStateIngredient> getBypassBlock() {
        return this.bypassBlock;
    }

    public Either<ResourceKey<Biome>, TagKey<Biome>> getBiome() {
        return this.biome;
    }

    @Override
    public Item getResult() {
        return Items.AIR;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        return this;
    }
}
