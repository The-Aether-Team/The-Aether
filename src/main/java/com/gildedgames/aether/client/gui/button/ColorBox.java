package com.gildedgames.aether.client.gui.button;

import com.gildedgames.aether.core.util.AetherCustomizations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class ColorBox extends EditBox {
    private final String valueKey;
    private final String colorKey;
    private String savedValue = "";
    private boolean hasValidColor = false;

    public ColorBox(String valueKey, String colorKey, Font font, int x, int y, int width, int height, Component message) {
        super(font, x, y, width, height, null, message);
        this.setMaxLength(6);
        this.valueKey = valueKey;
        this.colorKey = colorKey;
    }

    @Override
    public boolean canConsumeInput() {
        return super.canConsumeInput() && this.isValueActive();
    }

    @Override
    public boolean isFocused() {
        return super.isFocused() && this.isValueActive();
    }

    @Override
    public void renderButton(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        if (this.getValue().length() == 6) {
            try {
                AetherCustomizations.INSTANCE.set(this.colorKey, this.getValue());
                int decimal = Integer.parseInt(this.getValue(), 16);
                this.setTextColor(decimal);
                this.hasValidColor = true;
            } catch (NumberFormatException ignored) {
                this.setTextColor(14737632);
                this.hasValidColor = false;
            }
        } else if (this.getValue().length() == 0) {
            AetherCustomizations.INSTANCE.set(this.colorKey, null);
            this.setTextColor(14737632);
            this.hasValidColor = true;
        } else {
            this.setTextColor(14737632);
            this.hasValidColor = false;
        }
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
    }

    public boolean hasTextChanged() {
        return !this.savedValue.equals(this.getValue());
    }

    public String getSavedValue() {
        return this.savedValue;
    }

    public void setSavedValue(String lastSavedValue) {
        this.savedValue = lastSavedValue;
    }

    public boolean hasValidColor() {
        return this.hasValidColor;
    }

    public boolean isValueActive() {
        Object value = AetherCustomizations.INSTANCE.get(this.valueKey);
        if (value instanceof Boolean bool) {
            return bool;
        } else {
            return false;
        }
    }
}
