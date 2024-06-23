package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.network.packet.serverbound.SunAltarUpdatePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class SunAltarSlider extends AbstractSliderButton {
    private final Level level;
    private final int timeScale;

    public SunAltarSlider(Level level, int x, int y, int width, int height, Component title, double value, int timeScale) {
        super(x, y, width, height, title, value);
        this.level = level;
        this.timeScale = timeScale;
    }

    @Override
    protected void applyValue() {
        long time = (long) (this.value * this.timeScale);
        PacketRelay.sendToServer(new SunAltarUpdatePacket(time, this.timeScale));
    }

    @Override
    protected void updateMessage() {
    }
}
