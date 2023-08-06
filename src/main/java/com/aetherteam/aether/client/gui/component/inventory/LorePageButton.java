package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.Aether;
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
    private boolean isActive;

    public LorePageButton(Button.Builder builder) {
        super(builder.createNarration(DEFAULT_NARRATION));
        this.setIsActive(false);
    }

    public void onPress() {
        if (this.isActive()) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int textureY = this.getTextureY();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BUTTON_TEXTURES);
        GuiComponent.blit(poseStack, this.getX(), this.getY(), 0, textureY, this.width / 2, this.height);
        GuiComponent.blit(poseStack, this.getX() + this.width / 2, this.getY(), 200 - this.width / 2, textureY, this.width / 2, this.height);
        int color = this.getFGColor();
        GuiComponent.drawCenteredString(poseStack, fontRenderer, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, color | 255 << 24);
    }

    /**
     * [CODE COPY] - {@link AbstractButton#getTextureY()}
     */
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
