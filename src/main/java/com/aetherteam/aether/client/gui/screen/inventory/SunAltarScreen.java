package com.aetherteam.aether.client.gui.screen.inventory;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.gui.component.inventory.SunAltarSlider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

public class SunAltarScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/gui/menu/sun_altar.png");
    private final int timeScale;

    public SunAltarScreen(Component title, int timeScale) {
        super(title);
        this.timeScale = timeScale;
    }

    @Override
    public void init() {
        super.init();
        if (this.getMinecraft().level != null) {
            double sliderValue = (this.getMinecraft().level.getDayTime() % (long) this.timeScale) / (double) this.timeScale; // What position the slider bar should be at.
            this.addRenderableWidget(new SunAltarSlider(this.getMinecraft().level, this.width / 2 - 75, this.height / 2, 150, 20, Component.translatable("gui.aether.sun_altar.time"), sliderValue, this.timeScale));
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderTransparentBackground(guiGraphics);
        int xSize = 176;
        int ySize = 79;
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, xSize, ySize);

        FormattedCharSequence sequence = this.title.getVisualOrderText();
        guiGraphics.drawString(this.font, this.title, (int) ((this.width - this.font.width(sequence)) / 2.0F), y + 20, 0x404040, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
