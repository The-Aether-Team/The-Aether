package com.gildedgames.aether.client;

import com.gildedgames.aether.block.AetherBlocks;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.inventory.AetherRecipeBookTypes;
import com.gildedgames.aether.recipe.AetherRecipeSerializers;
import com.gildedgames.aether.recipe.AetherRecipeTypes;
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
    public static final Supplier<RecipeBookCategories> ENCHANTING_FOOD = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_FOOD", new ItemStack(AetherItems.ENCHANTED_BERRY.get())));
    public static final Supplier<RecipeBookCategories> ENCHANTING_BLOCKS = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_BLOCKS", new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get())));
    public static final Supplier<RecipeBookCategories> ENCHANTING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_MISC", new ItemStack(AetherItems.SKYROOT_REMEDY_BUCKET.get())));
    public static final Supplier<RecipeBookCategories> ENCHANTING_REPAIR = Suppliers.memoize(() -> RecipeBookCategories.create("ENCHANTING_REPAIR", new ItemStack(AetherItems.ZANITE_PICKAXE.get())));

    public static final Supplier<RecipeBookCategories> FREEZABLE_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("FREEZABLE_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> FREEZABLE_BLOCKS = Suppliers.memoize(() -> RecipeBookCategories.create("FREEZABLE_BLOCKS", new ItemStack(AetherBlocks.BLUE_AERCLOUD.get())));
    public static final Supplier<RecipeBookCategories> FREEZABLE_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("FREEZABLE_MISC", new ItemStack(AetherItems.ICE_RING.get())));


    /**
     * Registers the mod's categories to be used in-game, along with functions to sort items.
     * To add sub-categories to be used by the search, use addAggregateCategories with the
     * search category as the first parameter.
     */
    public static void registerRecipeCategories() {
        RecipeBookRegistry.addCategoriesToType(AetherRecipeBookTypes.ALTAR, ImmutableList.of(ENCHANTING_SEARCH.get(), ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.addAggregateCategories(ENCHANTING_SEARCH.get(), ImmutableList.of(ENCHANTING_FOOD.get(), ENCHANTING_BLOCKS.get(), ENCHANTING_MISC.get(), ENCHANTING_REPAIR.get()));
        RecipeBookRegistry.addCategoriesFinder(AetherRecipeTypes.ENCHANTING.get(), recipe -> {
            if (recipe.getIngredients().get(0).getItems()[0].is(recipe.getResultItem().getItem())) {
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
        RecipeBookRegistry.addCategoriesToType(AetherRecipeBookTypes.FREEZER, ImmutableList.of(FREEZABLE_SEARCH.get(), FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
        RecipeBookRegistry.addAggregateCategories(FREEZABLE_SEARCH.get(), ImmutableList.of(FREEZABLE_BLOCKS.get(), FREEZABLE_MISC.get()));
        RecipeBookRegistry.addCategoriesFinder(AetherRecipeTypes.FREEZING.get(), recipe -> recipe.getResultItem().getItem() instanceof BlockItem ? FREEZABLE_BLOCKS.get() : FREEZABLE_MISC.get());
    }

}
