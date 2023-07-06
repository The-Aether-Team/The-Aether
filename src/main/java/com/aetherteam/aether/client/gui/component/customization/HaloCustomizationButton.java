package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.perk.CustomizationsOptions;
import net.minecraft.resources.ResourceLocation;

public class HaloCustomizationButton extends CustomizationButton {
    public HaloCustomizationButton(AetherCustomizationsScreen screen, ButtonType buttonType, ColorBox colorBox, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Builder builder) {
        super(screen, buttonType, colorBox, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, builder);
    }

    @Override
    public boolean isActive() {
        if (this.buttonType == ButtonType.SAVE) {
            return super.isActive() && ((this.colorBox.hasValidColor() && this.colorBox.hasTextChanged()) || (this.screen.haloEnabled != CustomizationsOptions.INSTANCE.isHaloEnabled()));
        } else {
            return super.isActive() && (this.colorBox.hasTextChanged() || (this.screen.haloEnabled != CustomizationsOptions.INSTANCE.isHaloEnabled()));
        }
    }
}
