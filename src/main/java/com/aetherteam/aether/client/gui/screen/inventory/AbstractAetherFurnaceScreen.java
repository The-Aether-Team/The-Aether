package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.inventory.menu.AbstractAetherFurnaceMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
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
    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int left = this.getGuiLeft();
        int top = this.getGuiTop();
        this.blit(poseStack, left, top, 0, 0, this.getXSize(), this.getYSize());
        if (this.getMenu().isLit()) {
            int litProgress = this.getMenu().getLitProgress();
            this.blit(poseStack, left + 56, top + 36 + 12 - litProgress, 176, 12 - litProgress, 14, litProgress + 1);
        }
        int burnProgress = this.getMenu().getBurnProgress();
        this.blit(poseStack, left + 79, top + 34, 176, 14, burnProgress + 1, 16);
    }
}