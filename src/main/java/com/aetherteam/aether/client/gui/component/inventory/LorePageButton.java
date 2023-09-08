package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class LorePageButton extends Button {
    private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation(Aether.MODID, "textures/gui/menu/lore_widgets.png");

    public LorePageButton(Builder builder) {
        super(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.tooltip);
        this.active = false;
    }

    public void onPress() {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int textureY = this.getTextureY();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BUTTON_TEXTURES);
        this.blit(poseStack, this.x, this.y, 0, textureY, this.width / 2, this.height);
        this.blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, textureY, this.width / 2, this.height);
        int color = this.getFGColor();
        GuiComponent.drawCenteredString(poseStack, fontRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, color | 255 << 24);
    }

    /**
     * [CODE COPY] - AbstractButton#getTextureY()
     */
    private int getTextureY() {
        int i = 1;
        if (!this.isActive()) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }
        return 46 + i * 20;
    }
}
