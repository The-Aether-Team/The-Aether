package com.gildedgames.aether.core.mixin.client;

import com.gildedgames.aether.common.registry.AetherRecipes.RecipeTypes;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin
{
    @Inject(at = @At("HEAD"), method = "getCategory", cancellable = true)
    private static void getCategory(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookCategories> cir) {
        if (recipe.getType() == RecipeTypes.ENCHANTING || recipe.getType() == RecipeTypes.FREEZING) {
            cir.setReturnValue(RecipeBookCategories.UNKNOWN);
        }
    }
}
