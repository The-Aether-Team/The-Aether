package com.gildedgames.aether.recipe.recipes;

import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
import com.gildedgames.aether.recipe.BlockPropertyPair;
import com.gildedgames.aether.recipe.BlockStateIngredient;
import com.gildedgames.aether.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;

public class AccessoryFreezableRecipe extends AbstractBlockStateRecipe {
    public AccessoryFreezableRecipe(ResourceLocation id, BlockStateIngredient ingredient, BlockPropertyPair result) {
        super(AetherRecipeTypes.ACCESSORY_FREEZABLE.get(), id, ingredient, result);
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.ACCESSORY_FREEZABLE.get();
    }

    public static class Serializer extends BlockStateRecipeSerializer<AccessoryFreezableRecipe> {
        public Serializer() {
            super(AccessoryFreezableRecipe::new);
        }
    }
}
