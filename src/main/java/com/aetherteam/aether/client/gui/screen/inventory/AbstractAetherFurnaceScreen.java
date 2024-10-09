package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.inventory.menu.AbstractAetherFurnaceMenu;
import com.aetherteam.aether.recipe.recipes.item.AbstractAetherCookingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public abstract class AbstractAetherFurnaceScreen<T extends AbstractAetherFurnaceMenu> extends AbstractRecipeBookScreen<SingleRecipeInput, AbstractCookingRecipe, T, AbstractFurnaceRecipeBookComponent> {
    private final ResourceLocation texture;
    private final ResourceLocation litProgressSprite;
    private final ResourceLocation burnProgressSprite;

    public AbstractAetherFurnaceScreen(T menu, AbstractFurnaceRecipeBookComponent recipeBook, Inventory playerInventory, Component title, ResourceLocation texture, ResourceLocation litProgressSprite, ResourceLocation burnProgressSprite) {
        super(menu, recipeBook, playerInventory, title);
        this.texture = texture;
        this.litProgressSprite = litProgressSprite;
        this.burnProgressSprite = burnProgressSprite;
    }

    @Override
    public void init() {
        super.init();
        this.initScreen(20);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        guiGraphics.blit(this.texture, left, top, 0, 0, this.getXSize(), this.getYSize());
        if (this.getMenu().isLit()) {
            int litProgress = this.getMenu().getLitProgress() + 1;
            guiGraphics.blitSprite(this.litProgressSprite, 14, 14, 0, 14 - litProgress, left + 57, top + 36 + 13 - litProgress, 14, litProgress);
        }
        int burnProgress = this.getMenu().getBurnProgress();
        guiGraphics.blitSprite(this.burnProgressSprite, 24, 16, 0, 0, left + 79, top + 34, burnProgress + 1, 16);
    }
}
