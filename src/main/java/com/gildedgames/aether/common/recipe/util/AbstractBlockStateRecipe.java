package com.gildedgames.aether.common.recipe.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractBlockStateRecipe implements BlockStateRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final BlockStateIngredient ingredient;
    protected final BlockState result;

    public AbstractBlockStateRecipe(RecipeType<?> type, ResourceLocation id, BlockStateIngredient ingredient, BlockState result) {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
    }

    public boolean matches(BlockState state) {
        return this.ingredient.test(state);
    }

    @Override
    public BlockStateIngredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public BlockState getResultState() {
        return this.result;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
