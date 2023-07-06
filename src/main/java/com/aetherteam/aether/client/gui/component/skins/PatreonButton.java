package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;

public class PatreonButton extends Button {
    public PatreonButton(Builder builder) {
        super(builder);
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MoaSkinsScreen.MOA_SKINS_GUI);
        RenderSystem.clearColor(1.0F, 1.0F, 1.0F, this.alpha);
        int u = 0;
        int v = 215;
        if (this.isHovered) {
            u = 54;
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(poseStack, this.getX(), this.getY(), u, v, this.getWidth(), this.getHeight());
        Gui.drawCenteredString(poseStack, minecraft.font, this.getMessage(), this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2) - 4, 16777215);
    }
}
