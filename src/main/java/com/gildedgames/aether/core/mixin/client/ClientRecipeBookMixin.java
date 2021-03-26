package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.crafting.IRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin
{
    @Inject(at = @At("HEAD"), method = "getCategory", cancellable = true)
    private static void getCategory(IRecipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
        if (recipe.getType() == RecipeTypes.ENCHANTING || recipe.getType() == RecipeTypes.FREEZING || recipe.getType() == RecipeTypes.REPAIRING) {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
