package com.aetherteam.aether.client.gui.component;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.SunAltarUpdatePacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunAltarSlider extends AbstractSliderButton {
    private Level level;

    public SunAltarSlider(Level level, int pX, int pY, int pWidth, int pHeight, Component pMessage, double pValue) {
        super(pX, pY, pWidth, pHeight, pMessage, pValue);
        this.level = level;
    }

    @Override
    protected void updateMessage() {

    }

    @Override
    protected void applyValue() {
        long time = (long) (this.value * AetherDimensions.AETHER_TICKS_PER_DAY);
        level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> {
            PacketRelay.sendToServer(AetherPacketHandler.INSTANCE, new SunAltarUpdatePacket(time));
        });
    }

}
