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
    public float scale = 1.0F;

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
        int logoWidth = (int) (350 / this.scale);
        int logoHeight = (int) (76 / this.scale);
        int logoX = this.alignedLeft ? (int) (10 + (18 / scale)) : (int) ((screenWidth / 2.0F - 175 / scale));
        int logoY = this.alignedLeft ? (int) (15 + (10 / scale)) : (int) (25 + (10 / scale));
        guiGraphics.blit(AETHER_LOGO, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }
}
