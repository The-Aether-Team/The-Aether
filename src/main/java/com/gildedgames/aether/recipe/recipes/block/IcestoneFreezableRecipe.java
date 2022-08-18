package com.gildedgames.aether.recipe.recipes.block;

import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;

public class IcestoneFreezableRecipe extends AbstractBlockStateRecipe {
    public IcestoneFreezableRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result) {
        super(AetherRecipeTypes.ICESTONE_FREEZABLE.get(), id, ingredient, result);
    }

    @Nonnull
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
