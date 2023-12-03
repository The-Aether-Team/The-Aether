package com.aetherteam.aether.client.gui.component.skins;

import com.aetherteam.aether.client.gui.component.Builder;
import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;

public class PatreonButton extends Button {
    private final boolean small;

    public PatreonButton(Builder builder) {
        this(builder, false);
    }

    public PatreonButton(Builder builder, boolean small) {
        super(builder.x, builder.y, builder.width, builder.height, builder.message, builder.onPress, builder.tooltip);
        this.small = small;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MoaSkinsScreen.MOA_SKINS_GUI);
        int u = 0;
        int v = !this.small ? 215 : 233;
        if (this.isHovered) {
            u = 54;
        }
        this.blit(poseStack, this.x, this.y, u, v, this.getWidth(), this.getHeight());
        GuiComponent.drawCenteredString(poseStack, minecraft.font, this.getMessage(), this.x + (this.getWidth() / 2), this.y + (this.getHeight() / 2) - 4, 16777215);
    }
}
