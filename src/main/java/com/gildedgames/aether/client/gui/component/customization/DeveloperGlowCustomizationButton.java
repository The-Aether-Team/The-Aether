package com.gildedgames.aether.client.gui.component.customization;

import com.gildedgames.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.gildedgames.aether.perk.CustomizationsOptions;
import net.minecraft.resources.ResourceLocation;

public class DeveloperGlowCustomizationButton extends CustomizationButton {
    public DeveloperGlowCustomizationButton(AetherCustomizationsScreen screen, ButtonType buttonType, ColorBox colorBox, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, Builder builder) {
        super(screen, buttonType, colorBox, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, builder);
    }

    @Override
    public boolean isActive() {
        if (this.buttonType == ButtonType.SAVE) {
            return super.isActive() && ((this.colorBox.hasValidColor() && this.colorBox.hasTextChanged()) || (this.screen.developerGlowEnabled != CustomizationsOptions.INSTANCE.isDeveloperGlowEnabled()));
        } else {
            return super.isActive() && (this.colorBox.hasTextChanged() || (this.screen.developerGlowEnabled != CustomizationsOptions.INSTANCE.isDeveloperGlowEnabled()));
        }
    }
}
