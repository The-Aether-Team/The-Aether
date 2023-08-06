package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.inventory.SunAltarSlider;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class SunAltarScreen extends Screen {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Aether.MODID, "textures/gui/menu/sun_altar.png");

    public SunAltarScreen(Component title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();
        if (this.getMinecraft().level != null) {
            double sliderValue = (this.getMinecraft().level.getDayTime() % (long) AetherDimensions.AETHER_TICKS_PER_DAY) / (double) AetherDimensions.AETHER_TICKS_PER_DAY; // What position the slider bar should be at.
            this.addRenderableWidget(new SunAltarSlider(this.getMinecraft().level, this.width / 2 - 75, this.height / 2, 150, 20, Component.translatable("gui.aether.sun_altar.time"), sliderValue));
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int xSize = 176;
        int ySize = 79;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        GuiComponent.blit(poseStack, x, y, 0, 0, xSize, ySize);

        FormattedCharSequence sequence = this.title.getVisualOrderText();
        this.font.draw(poseStack, this.title, (this.width - this.font.width(sequence)) / 2F, y + 20, 0x404040);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
