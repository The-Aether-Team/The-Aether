package com.aetherteam.aether.integration.jei.categories.block;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.recipe.recipes.block.AmbrosiumRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AmbrosiumRecipeCategory extends AetherBlockStateRecipeCategory<AmbrosiumRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Aether.MODID, "ambrosium_enchanting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/jei_render.png");
    public static final RecipeType<AmbrosiumRecipe> RECIPE_TYPE = RecipeType.create(Aether.MODID, "ambrosium_enchanting", AmbrosiumRecipe.class);

    public AmbrosiumRecipeCategory(IGuiHelper guiHelper, IPlatformFluidHelper<?> fluidHelper) {
        super("ambrosium_enchanting", UID,
                guiHelper.createDrawable(TEXTURE, 0, 0, 84, 28),
                guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherItems.AMBROSIUM_SHARD.get())),
                RECIPE_TYPE, fluidHelper);
    }
}
