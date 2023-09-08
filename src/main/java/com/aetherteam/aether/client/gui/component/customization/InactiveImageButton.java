package com.aetherteam.aether.client.gui.component.customization;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InactiveImageButton extends ImageButton {
    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;

    public InactiveImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation texture, int textureWidth, int textureHeight, OnPress onPress, OnTooltip tooltip, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, texture, textureWidth, textureHeight, onPress, tooltip, message);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiffTex = yDiffTex;
        this.resourceLocation = texture;
    }

    /**
     * [CODE COPY] - net.minecraft.client.gui.components.AbstractWidget#renderTexture(PoseStack, ResourceLocation, int, int, int, int, int, int, int, int, int).<br><br>
     * Supports a third texture for the inactive state of this image button.
     */
    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int v = this.yTexStart;
        if (!this.isActive()) {
            v -= this.yDiffTex;
        } else if (this.isHoveredOrFocused()) {
            v += this.yDiffTex;
        }
        RenderSystem.enableDepthTest();
        GuiComponent.blit(poseStack, this.x, this.y, (float) this.xTexStart, (float) v, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}