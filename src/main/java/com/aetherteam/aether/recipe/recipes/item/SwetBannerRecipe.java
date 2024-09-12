package com.aetherteam.aether.recipe.recipes.item;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.AetherRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SwetBannerRecipe extends CustomRecipe {
    public SwetBannerRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(CraftingContainer inv, Level level) {
        boolean flag = false;
        boolean flag1 = false;

        for(int i = 0; i < inv.getContainerSize(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(ItemTags.BANNERS) && !flag) {
                    flag = true;
                } else if (itemstack.is(AetherItems.SWET_CAPE.get()) && !flag1) {
                    flag1 = true;
                }
            }
        }

        return flag && flag1;
    }

    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return AetherItems.createSwetBannerItemStack();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AetherRecipeSerializers.SWET_BANNER.get();
    }
}
