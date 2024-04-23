package com.aetherteam.aether.client.gui.component.inventory;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.SunAltarUpdatePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class SunAltarSlider extends AbstractSliderButton {
    private final Level level;

    public SunAltarSlider(Level level, int x, int y, int width, int height, Component title, double value) {
        super(x, y, width, height, title, value);
        this.level = level;
    }

    @Override
    protected void applyValue() {
        long time = (long) (this.value * AetherDimensions.AETHER_TICKS_PER_DAY);
        AetherCapabilities.AETHER_TIME_CAPABILITY.maybeGet(this.level).ifPresent(aetherTime -> PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new SunAltarUpdatePacket(time)));
    }

    @Override
    protected void updateMessage() { }
}
