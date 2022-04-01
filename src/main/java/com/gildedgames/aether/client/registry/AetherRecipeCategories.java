package com.gildedgames.aether.client.registry;

import com.gildedgames.aether.common.recipe.AltarRepairRecipe;
import com.gildedgames.aether.common.registry.AetherBlocks;
import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherRecipeBookTypes;
import com.gildedgames.aether.common.registry.AetherRecipes;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.RecipeBookRegistry;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class AetherRecipeCategories {
    public static final Supplier<RecipeBookCategories> ENCHANTING_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> ENCHANTING_FOOD = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_FOOD", new ItemStack(AetherItems.BLUE_BERRY.get())));
    public static final Supplier<RecipeBookCategories> ENCHANTING_BLOCKS = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_BLOCKS", new ItemStack(AetherBlocks.ZANITE_ORE.get())));
    public static final Supplier<RecipeBookCategories> ENCHANTING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_MISC", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> ENCHANTING_REPAIR = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_REPAIR", new ItemStack(AetherItems.ZANITE_PICKAXE.get())));

    public static final Supplier<RecipeBookCategories> FREEZABLES = Suppliers.memoize(() -> RecipeBookCategories.create("FREEZABLES", new ItemStack(AetherItems.ICE_PENDANT.get())));

    public static void registerRecipeCategories() {
        RecipeBookRegistry.addCategoriesToType(AetherRecipeBookTypes.ALTAR, ImmutableList.of(ENCHANTING_SEARCH.get(), ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.addAggregateCategories(ENCHANTING_SEARCH.get(), ImmutableList.of(ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.addCategoriesFinder(AetherRecipes.RecipeTypes.ENCHANTING, recipe -> {
            if (recipe instanceof AltarRepairRecipe) {
                return ENCHANTING_REPAIR.get();
            }
            if (recipe.getResultItem().isEdible()) {
                return ENCHANTING_FOOD.get();
            }
            if (recipe.getResultItem().getItem() instanceof BlockItem) {
                return ENCHANTING_BLOCKS.get();
            }
            return ENCHANTING_MISC.get();
        });
        RecipeBookRegistry.addCategoriesToType(AetherRecipeBookTypes.FREEZER, ImmutableList.of(FREEZABLES.get()));
        RecipeBookRegistry.addCategoriesFinder(AetherRecipes.RecipeTypes.FREEZING, recipe -> FREEZABLES.get());
    }

}
