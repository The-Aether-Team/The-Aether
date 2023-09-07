package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CustomizationButton extends InactiveImageButton {
    protected final AetherCustomizationsScreen screen;
    protected final ColorBox colorBox;
    protected final ButtonType buttonType;

    public CustomizationButton(AetherCustomizationsScreen screen, ButtonType buttonType, ColorBox colorBox, int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation texture, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, texture, textureWidth, textureHeight, onPress, message);
        this.screen = screen;
        this.colorBox = colorBox;
        this.buttonType = buttonType;
    }

    public enum ButtonType {
        SAVE,
        UNDO
    }
}
