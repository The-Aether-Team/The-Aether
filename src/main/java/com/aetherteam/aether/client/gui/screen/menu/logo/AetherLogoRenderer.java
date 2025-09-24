package com.aetherteam.aether.client.gui.screen.menu.logo;

import com.aetherteam.aether.Aether;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

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
        float fade = this.keepLogoThroughFade ? 1.0F : transparency;
        int color = ARGB.white(fade);
        int logoX = this.alignedLeft ? 28 : (int) ((screenWidth / 2.0F - (190.0F / 2.0F)));
        int logoY = this.alignedLeft ? 25 : 36;
        guiGraphics.blit(RenderType::guiTextured, AETHER_LOGO, logoX, logoY, 0, 0, 190, 38, 190, 38, color);
    }
}
