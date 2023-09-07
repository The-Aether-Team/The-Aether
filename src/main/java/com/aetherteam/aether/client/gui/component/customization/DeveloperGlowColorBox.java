package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import com.aetherteam.aether.perk.CustomizationsOptions;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class DeveloperGlowColorBox extends ColorBox {
    public DeveloperGlowColorBox(AetherCustomizationsScreen screen, Font font, int x, int y, int width, int height, Component message) {
        super(screen, font, x, y, width, height, message);
    }

    @Override
    public boolean canConsumeInput() {
        return super.canConsumeInput() && this.screen.developerGlowEnabled;
    }

    @Override
    public boolean isFocused() {
        return super.isFocused() && this.screen.developerGlowEnabled;
    }

    @Override
    public boolean hasTextChanged() {
        return !this.getValue().equals(CustomizationsOptions.INSTANCE.getDeveloperGlowHex());
    }
}
