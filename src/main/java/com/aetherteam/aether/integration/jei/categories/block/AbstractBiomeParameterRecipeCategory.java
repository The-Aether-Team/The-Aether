package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.recipe.recipes.block.AbstractBiomeParameterRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public abstract class AbstractBiomeParameterRecipeCategory<T extends AbstractBiomeParameterRecipe> extends AbstractBlockStateRecipeCategory<T> {
    public AbstractBiomeParameterRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<T> recipeType, IPlatformFluidHelper<?> fluidHelper) {
        super(id, uid, background, icon, recipeType, fluidHelper);
    }

    @Override
    protected void populateAdditionalInformation(T recipe, List<Component> tooltip) {
        if (recipe.getBiomeKey() != null || recipe.getBiomeTag() != null) {
            tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip").withStyle(ChatFormatting.GRAY));
            if (recipe.getBiomeKey() != null) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biome").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal(recipe.getBiomeKey().location().toString()).withStyle(ChatFormatting.DARK_GRAY));
            } else if (recipe.getBiomeTag() != null) {
                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.tag").withStyle(ChatFormatting.DARK_GRAY));
                tooltip.add(Component.literal("#" + recipe.getBiomeTag().location()).withStyle(ChatFormatting.DARK_GRAY));

                tooltip.add(Component.translatable("gui.aether.jei.biome.tooltip.biomes").withStyle(ChatFormatting.DARK_GRAY));
                Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.BIOME).getTagOrEmpty(recipe.getBiomeTag()).forEach((biomeHolder) -> tooltip.add(Component.literal(biomeHolder.unwrapKey().get().location().toString()).withStyle(ChatFormatting.DARK_GRAY)));
            }
        }
    }
}
