package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.recipe.recipes.block.IcestoneFreezableRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class IcestoneFreezableRecipeCategory extends AbstractAetherBlockStateRecipeCategory<IcestoneFreezableRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "icestone_freezable");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<IcestoneFreezableRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "icestone_freezable", IcestoneFreezableRecipe.class);

    public IcestoneFreezableRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("icestone_freezable", UID,
                guiHelper.createDrawable(TEXTURE, 0, 0, 84, 28),
                guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherBlocks.ICESTONE.get())),
                RECIPE_TYPE, fluidHelper);
    }
}
