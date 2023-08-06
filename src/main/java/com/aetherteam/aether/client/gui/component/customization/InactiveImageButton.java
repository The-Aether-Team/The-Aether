package com.aetherteam.aether.client.gui.component.customization;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InactiveImageButton extends ImageButton {
    public InactiveImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation texture, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, texture, textureWidth, textureHeight, onPress, message);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.gui.components.AbstractWidget#renderTexture(PoseStack, ResourceLocation, int, int, int, int, int, int, int, int, int)}.<br><br>
     * Supports a third texture for the inactive state of this image button.
     */
    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int v = this.yTexStart;
        if (!this.isActive()) {
            v -= this.yDiffTex;
        } else if (this.isHoveredOrFocused()) {
            v += this.yDiffTex;
        }
        RenderSystem.enableDepthTest();
        GuiComponent.blit(poseStack, this.getX(), this.getY(), (float) this.xTexStart, (float) v, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}