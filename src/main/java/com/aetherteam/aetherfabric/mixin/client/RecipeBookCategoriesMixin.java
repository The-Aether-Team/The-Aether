package com.aetherteam.aetherfabric.mixin.client;

import com.aetherteam.aetherfabric.events.RecipeBookCategoriesHelper;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(RecipeBookCategories.class)
public abstract class RecipeBookCategoriesMixin {
    @WrapMethod(method = "getCategories")
    private static List<RecipeBookCategories> aetherFabric$getAlternatives(RecipeBookType recipeBookType, Operation<List<RecipeBookCategories>> original) {
        var categories = RecipeBookCategoriesHelper.INSTANCE.typeCategories.get(recipeBookType);

        return categories != null ? categories : original.call(recipeBookType);
    }
}
