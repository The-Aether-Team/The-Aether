package com.aetherteam.aether.client.gui.component;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LorePageButton extends Button
{
    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/lore_widgets.png");

    private boolean isActive;

    public LorePageButton(int x, int y, int width, int height, Component title, OnPress pressedAction) {
        super(x, y, width, height, title, pressedAction, DEFAULT_NARRATION);
        this.isActive = false;
    }

    public void onPress() {
        if (this.isActive) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderWidget(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BUTTON_TEXTURES);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        int textureY = this.getTextureY();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(matrixStack, this.getX(), this.getY(), 0, textureY, this.width / 2, this.height);
        blit(matrixStack, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, textureY, this.width / 2, this.height);
        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    // [CODE COPY] - AbstractButton.getTextureY()
    private int getTextureY() {
        int i = 1;
        if (!this.isActive) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }
}
