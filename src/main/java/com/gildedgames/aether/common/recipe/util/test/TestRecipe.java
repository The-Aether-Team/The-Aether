package com.gildedgames.aether.common.recipe.util.test;

import com.gildedgames.aether.common.recipe.util.AbstractBlockStateRecipe;
import com.gildedgames.aether.common.recipe.util.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.util.BlockStateRecipeSerializer;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class TestRecipe extends AbstractBlockStateRecipe {
    public TestRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockState result) {
        super(RecipeTypes.TEST, id, ingredient, result);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.TEST.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<TestRecipe> {
        public Serializer() {
            super(TestRecipe::new);
        }
    }
}
