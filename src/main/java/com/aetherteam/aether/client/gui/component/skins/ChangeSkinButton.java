package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;

public class ChangeSkinButton extends Button {
    private final ButtonType buttonType;
    private boolean isActive;

    public ChangeSkinButton(ButtonType buttonType, Builder builder) {
        super(builder);
        this.buttonType = buttonType;
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
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MoaSkinsScreen.MOA_SKINS_GUI);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        int u = 0;
        if (this.buttonType == ButtonType.REMOVE) {
            u += 3;
        }
        if (this.isActive) {
            u += 1;
            if (this.isHovered) {
                u += 1;
            }
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, this.getX(), this.getY(), u * 7, 184, 7, 7);
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public enum ButtonType {
        APPLY,
        REMOVE
    }
}
