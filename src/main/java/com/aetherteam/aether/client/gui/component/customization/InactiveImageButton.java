package com.aetherteam.aether.client.gui.component.customization;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class InactiveImageButton extends ImageButton {
    public InactiveImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
        super(x, y, width, height, sprites, onPress, message);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.gui.components.AbstractWidget#renderTexture(GuiGraphics, ResourceLocation, int, int, int, int, int, int, int, int, int)}.<br><br>
     * Supports a third texture for the inactive state of this image button.
     */
    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //todo
        ResourceLocation resourcelocation = this.sprites.get(this.isActive(), this.isHoveredOrFocused());
        guiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), this.width, this.height);
    }
}