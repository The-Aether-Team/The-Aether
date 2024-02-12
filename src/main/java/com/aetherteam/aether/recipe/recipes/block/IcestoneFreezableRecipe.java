package com.aetherteam.aether.recipe.recipes.block;

import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.nitrogen.recipe.BlockPropertyPair;
import com.aetherteam.nitrogen.recipe.BlockStateIngredient;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.commands.CommandFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public class IcestoneFreezableRecipe extends AbstractBlockStateRecipe {
    public IcestoneFreezableRecipe(BlockStateIngredient ingredient, BlockPropertyPair result, @Nullable CommandFunction.CacheableFunction function) {
        super(AetherRecipeTypes.ICESTONE_FREEZABLE.get(), ingredient, result, function);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.ICESTONE_FREEZABLE.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<IcestoneFreezableRecipe> {
        public Serializer() {
            super(IcestoneFreezableRecipe::new);
        }
    }
}
