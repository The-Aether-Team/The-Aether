package com.aetherteam.aether.integration.jei.categories.fuel;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.nitrogen.integration.jei.categories.fuel.AbstractFuelCategory;
import com.aetherteam.nitrogen.integration.jei.categories.fuel.FuelRecipe;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class AetherFuelCategory extends AbstractFuelCategory {
    public static final ResourceLocation ICON_TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/sprites/menu/lit_progress_transparent.png");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/altar.png");
    public static final RecipeType<FuelRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "fuel", FuelRecipe.class);

    public AetherFuelCategory(IGuiHelper helper) {
        super(helper, List.of(AetherBlocks.ALTAR.get().getName().getString(), AetherBlocks.FREEZER.get().getName().getString(), AetherBlocks.INCUBATOR.get().getName().getString()));
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui." + Aether.MODID + ".jei.fuel");
    }

    @Override
    public RecipeType<FuelRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public ResourceLocation getBackgroundTexture() {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getIconTexture() {
        return ICON_TEXTURE;
    }
}
