package com.gildedgames.aether.client.gui.screen.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class AetherFurnaceScreen<T extends AbstractFurnaceMenu> extends AbstractContainerScreen<T>
{
    private final ResourceLocation guiTexture;

    public AetherFurnaceScreen(T screenContainer, Inventory inv, Component titleIn, ResourceLocation guiTextureIn) {
        super(screenContainer, inv, titleIn);
        this.guiTexture = guiTextureIn;
    }

    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(this.guiTexture);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(matrixStack, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getBurnProgress();
        this.blit(matrixStack, i + 79, j + 34, 176, 14, l + 1, 16);
    }
}
