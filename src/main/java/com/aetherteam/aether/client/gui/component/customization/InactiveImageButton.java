package com.aetherteam.aether.client.gui.component.customization;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class InactiveImageButton extends Button {
    public final ResourceLocation resourceLocation;
    public final int xTexStart;
    public final int yTexStart;
    public final int yDiffTex;
    public final int textureWidth;
    public final int textureHeight;

    public InactiveImageButton(int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Builder builder) {
        super(builder);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiffTex = yDiffTex;
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
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