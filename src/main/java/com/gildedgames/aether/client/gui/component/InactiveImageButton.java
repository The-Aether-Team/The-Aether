package com.gildedgames.aether.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class InactiveImageButton extends ImageButton {
    public final ResourceLocation resourceLocation;
    public final int xTexStart;
    public final int yTexStart;
    public final int yDiffTex;
    public final int textureWidth;
    public final int textureHeight;

    public InactiveImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Button.OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress, message);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiffTex = yDiffTex;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void renderButton(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        int i = this.yTexStart;
        if (!this.isActive()) {
            i -= this.yDiffTex;
        } else if (this.isHoveredOrFocused()) {
            i += this.yDiffTex;
        }
        RenderSystem.enableDepthTest();
        blit(poseStack, this.getX(), this.getY(), (float)this.xTexStart, (float)i, this.width, this.height, this.textureWidth, this.textureHeight);
    }
}