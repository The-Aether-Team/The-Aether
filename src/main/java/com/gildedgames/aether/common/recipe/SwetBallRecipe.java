package com.gildedgames.aether.common.recipe;

import com.gildedgames.aether.common.recipe.ingredient.BlockStateIngredient;
import com.gildedgames.aether.common.recipe.serializer.BlockStateRecipeSerializer;
import com.gildedgames.aether.common.registry.AetherRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import java.util.Map;

public class SwetBallRecipe extends AbstractBlockStateRecipe {
    public SwetBallRecipe(ResourceLocation id, BlockStateIngredient ingredient, Block resultBlock, Map<Property<?>, Comparable<?>> resultProperties) {
        super(AetherRecipes.RecipeTypes.SWET_BALL, id, ingredient, resultBlock, resultProperties);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipes.SWET_BALL.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<SwetBallRecipe> {
        public Serializer() {
            super(SwetBallRecipe::new);
        }
    }
}
