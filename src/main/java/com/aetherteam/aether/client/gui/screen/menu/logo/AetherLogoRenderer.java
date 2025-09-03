package com.aetherteam.aether.client.gui.screen.menu.logo;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;

public class AetherLogoRenderer extends LogoRenderer {
    private static final ResourceLocation AETHER_LOGO = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/title/aether.png");
    private final boolean keepLogoThroughFade;
    private final boolean alignedLeft;

    public AetherLogoRenderer(boolean keepLogoThroughFade, boolean alignedLeft) {
        super(keepLogoThroughFade);
        this.keepLogoThroughFade = keepLogoThroughFade;
        this.alignedLeft = alignedLeft;
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency) {
        this.renderLogo(guiGraphics, screenWidth, transparency, 30);
    }

    public void renderLogo(GuiGraphics guiGraphics, int screenWidth, float transparency, int height) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : transparency);
        RenderSystem.enableBlend();
        int logoX = this.alignedLeft ? 28 : (int) ((screenWidth / 2.0F - (190.0F / 2.0F)));
        int logoY = this.alignedLeft ? 25 : 36;
        guiGraphics.blit(AETHER_LOGO, logoX, logoY, 0, 0, 190, 38, 190, 38);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
