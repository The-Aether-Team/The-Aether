package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import net.minecraft.resources.ResourceLocation;

public class CustomizationButton extends InactiveImageButton {
    protected final AetherCustomizationsScreen screen;
    protected final ColorBox colorBox;
    protected final ButtonType buttonType;

    public CustomizationButton(AetherCustomizationsScreen screen, ButtonType buttonType, ColorBox colorBox, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Builder builder) {
        super(xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, builder);
        this.screen = screen;
        this.colorBox = colorBox;
        this.buttonType = buttonType;
    }

    public enum ButtonType {
        SAVE,
        UNDO
    }
}
