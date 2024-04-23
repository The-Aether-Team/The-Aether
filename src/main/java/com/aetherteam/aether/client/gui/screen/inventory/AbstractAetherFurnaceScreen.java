package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.inventory.menu.AbstractAetherFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractAetherFurnaceScreen<T extends AbstractAetherFurnaceMenu> extends AbstractRecipeBookScreen<T, AbstractFurnaceRecipeBookComponent> {
    private final ResourceLocation texture;

    public AbstractAetherFurnaceScreen(T menu, AbstractFurnaceRecipeBookComponent recipeBook, Inventory playerInventory, Component title, ResourceLocation texture) {
        super(menu, recipeBook, playerInventory, title);
        this.texture = texture;
    }

    @Override
    public void init() {
        super.init();
        this.initScreen(20);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        int left = this.leftPos;
        int top = this.topPos;
        guiGraphics.blit(this.texture, left, top, 0, 0, this.imageWidth, this.imageHeight);
        if (this.getMenu().isLit()) {
            int litProgress = this.getMenu().getLitProgress();
            guiGraphics.blit(this.texture, left + 56, top + 36 + 12 - litProgress, 176, 12 - litProgress, 14, litProgress + 1);
        }
        int burnProgress = this.getMenu().getBurnProgress();
        guiGraphics.blit(this.texture, left + 79, top + 34, 176, 14, burnProgress + 1, 16);
    }
}