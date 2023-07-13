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

public class IcestoneFreezableRecipe extends AbstractBlockStateRecipe {
    public IcestoneFreezableRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result, CommandFunction.CacheableFunction function) {
        super(AetherRecipeTypes.ICESTONE_FREEZABLE.get(), id, ingredient, result, function);
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
