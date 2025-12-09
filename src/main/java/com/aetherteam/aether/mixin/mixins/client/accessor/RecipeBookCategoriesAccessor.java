package com.aetherteam.aether.mixin.mixins.client.accessor;

import net.minecraft.client.RecipeBookCategories;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(RecipeBookCategories.class)
public interface RecipeBookCategoriesAccessor {
    @Mutable
    @Accessor("AGGREGATE_CATEGORIES")
    static void nitrogen_fabric$setAGGREGATE_CATEGORIES(Map<RecipeBookCategories, List<RecipeBookCategories>> map) {
        throw new IllegalStateException("HOW DID THIS HAPPEN");
    }
}
