package com.gildedgames.aether.client.gui.screen;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.gui.component.SunAltarSlider;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class SunAltarScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/sun_altar.png");

    public SunAltarScreen(Component title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();
        double value = this.minecraft.level.getDayTime() / 72000D;
        this.addRenderableWidget(new SunAltarSlider(this.minecraft.level, this.width / 2 - 75, this.height / 2, 150, 20, Component.translatable("gui." + Aether.MODID + ".sun_altar.time"), value));
    }

    @Override
    public void render(@Nonnull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int xSize = 176;
        int ySize = 79;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        this.blit(pPoseStack, x, y, 0, 0, xSize, ySize);

        FormattedCharSequence sequence = this.title.getVisualOrderText();
        this.font.draw(pPoseStack, this.title, (this.width - this.font.width(sequence)) / 2F, y + 20, 0x404040);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
