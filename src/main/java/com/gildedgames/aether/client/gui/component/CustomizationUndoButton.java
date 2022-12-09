package com.gildedgames.aether.client.gui.component;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CustomizationUndoButton extends InactiveImageButton {
    private final ColorBox colorBox;

    public CustomizationUndoButton(ColorBox colorBox, int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress, message);
        this.colorBox = colorBox;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && this.colorBox.hasTextChanged();
    }
}