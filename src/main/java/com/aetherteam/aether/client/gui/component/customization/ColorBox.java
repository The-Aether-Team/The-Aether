package com.aetherteam.aether.client.gui.component.customization;

import com.aetherteam.aether.client.gui.screen.perks.AetherCustomizationsScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public abstract class ColorBox extends EditBox {
    protected final AetherCustomizationsScreen screen;
    protected boolean hasValidColor = false;

    public ColorBox(AetherCustomizationsScreen screen, Font font, int x, int y, int width, int height, Component message) {
        super(font, x, y, width, height, null, message);
        this.setMaxLength(6);
        this.screen = screen;
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.getValue().length() == 6) {
            try { // Attempt to parse the input as a valid hex code and convert it to decimal.
                int decimal = Integer.parseInt(this.getValue(), 16);
                this.setTextColor(decimal);
                this.hasValidColor = true;
            } catch (NumberFormatException ignored) { // Sets there to be no color if the color conversion failed.
                this.setTextColor(14737632);
                this.hasValidColor = false;
            }
        } else if (this.getValue().length() == 0) { // Sets there to be no color if there is no input.
            this.setTextColor(14737632);
            this.hasValidColor = true;
        } else { // If the input is longer than 6 characters, it's not a valid hex code.
            this.setTextColor(14737632);
            this.hasValidColor = false;
        }
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public boolean hasValidColor() {
        return this.hasValidColor;
    }

    public abstract boolean hasTextChanged();
}
