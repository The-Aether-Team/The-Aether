package com.gildedgames.aether.client.gui.button;

import com.gildedgames.aether.Aether;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.network.chat.Component;

import net.minecraft.client.gui.components.Button.OnPress;

public class LorePageButton extends Button
{
    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/container/lore_widgets.png");

    private boolean isActive;

    public LorePageButton(int x, int y, int width, int height, Component title, OnPress pressedAction) {
        super(x, y, width, height, title, pressedAction);
        this.isActive = false;
    }

    public void onPress() {
        if (this.isActive) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;
        minecraft.getTextureManager().bindForSetup(BUTTON_TEXTURES);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        boolean flag = false;
        if (this.isActive) flag = this.isHovered;
        int i = this.getYImage(flag);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, minecraft, mouseX, mouseY);
        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }
}
