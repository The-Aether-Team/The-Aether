package com.aetherteam.aether.client;

import com.aetherteam.aether.inventory.AetherRecipeBookTypes;
import com.aetherteam.aether.recipe.AetherBookCategory;
import com.aetherteam.aether.recipe.AetherRecipeTypes;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import com.aetherteam.aether.recipe.recipes.item.AltarRepairRecipe;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import io.github.fabricators_of_create.porting_lib.recipe_book_categories.RecipeBookRegistry;
import me.shedaniel.mm.api.ClassTinkerers;
import net.minecraft.client.RecipeBookCategories;

import java.util.function.Supplier;

public class AetherRecipeCategories {
    public static final Supplier<RecipeBookCategories> ENCHANTING_SEARCH = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "ENCHANTING_SEARCH"));
    public static final Supplier<RecipeBookCategories> ENCHANTING_FOOD = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "ENCHANTING_FOOD"));
    public static final Supplier<RecipeBookCategories> ENCHANTING_BLOCKS = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "ENCHANTING_BLOCKS"));
    public static final Supplier<RecipeBookCategories> ENCHANTING_MISC = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "ENCHANTING_MISC"));
    public static final Supplier<RecipeBookCategories> ENCHANTING_REPAIR = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "ENCHANTING_REPAIR"));

    public static final Supplier<RecipeBookCategories> FREEZABLE_SEARCH = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "FREEZABLE_SEARCH"));
    public static final Supplier<RecipeBookCategories> FREEZABLE_BLOCKS = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "FREEZABLE_BLOCKS"));
    public static final Supplier<RecipeBookCategories> FREEZABLE_MISC = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "FREEZABLE_MISC"));

    public static final Supplier<RecipeBookCategories> INCUBATION_SEARCH = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "INCUBATION_SEARCH"));
    public static final Supplier<RecipeBookCategories> INCUBATION_MISC = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookCategories.class, "INCUBATION_MISC"));

    /**
     * Registers the mod's categories to be used in-game, along with functions to sort items.
     * To add sub-categories to be used by the search, use addAggregateCategories with the
     * search category as the first parameter.
     */
    public static void registerRecipeCategories() {
        // Enchanting
        RecipeBookRegistry.registerBookCategories(AetherRecipeBookTypes.ALTAR, ImmutableList.of(ENCHANTING_SEARCH.get(), ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.registerAggregateCategory(ENCHANTING_SEARCH.get(), ImmutableList.of(ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.registerRecipeCategoryFinder(AetherRecipeTypes.ENCHANTING.get(), recipe -> {
            if (recipe instanceof AltarRepairRecipe || (recipe instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe && abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_REPAIR)) {
                return ENCHANTING_REPAIR.get();
            } else if (recipe instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe) {
                if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_FOOD) {
                    return ENCHANTING_FOOD.get();
                } else if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.ENCHANTING_BLOCKS) {
                    return ENCHANTING_BLOCKS.get();
                }
            }
            return ENCHANTING_MISC.get();
        });
        // Freezing
        RecipeBookRegistry.registerBookCategories(AetherRecipeBookTypes.FREEZER, ImmutableList.of(FREEZABLE_SEARCH.get(), FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
        RecipeBookRegistry.registerAggregateCategory(FREEZABLE_SEARCH.get(), ImmutableList.of(FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
        RecipeBookRegistry.registerRecipeCategoryFinder(AetherRecipeTypes.FREEZING.get(), recipe -> {
            if (recipe instanceof AbstractAetherCookingRecipe abstractAetherCookingRecipe) {
                if (abstractAetherCookingRecipe.aetherCategory() == AetherBookCategory.FREEZABLE_BLOCKS) {
                    return FREEZABLE_BLOCKS.get();
                }
            }
            return FREEZABLE_MISC.get();
        });
        // Incubation
        RecipeBookRegistry.registerBookCategories(AetherRecipeBookTypes.INCUBATOR, ImmutableList.of(INCUBATION_SEARCH.get(), INCUBATION_MISC.get()));
        RecipeBookRegistry.registerAggregateCategory(INCUBATION_SEARCH.get(), ImmutableList.of(INCUBATION_MISC.get()));
        RecipeBookRegistry.registerRecipeCategoryFinder(AetherRecipeTypes.INCUBATION.get(), recipe -> INCUBATION_MISC.get());
    }
}
