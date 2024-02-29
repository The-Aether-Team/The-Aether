package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;

public class CustomizationButton extends ImageButton {
    protected final AetherCustomizationsScreen screen;
    protected final ColorBox colorBox;
    protected final ButtonType buttonType;

    public CustomizationButton(AetherCustomizationsScreen screen, ButtonType buttonType, ColorBox colorBox, int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress, Component message) {
        super(x, y, width, height, sprites, onPress, message);
        this.screen = screen;
        this.colorBox = colorBox;
        this.buttonType = buttonType;
    }

    public enum ButtonType {
        SAVE,
        UNDO
    }
}
