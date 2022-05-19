package com.gildedgames.aether.client.gui.button;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class MenuSwitchButton extends Button {
    public MenuSwitchButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, OnPress pOnPress, OnTooltip pOnTooltip) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, pOnTooltip);
    }

    @Override
    public void setAlpha(float pAlpha) { }
}
