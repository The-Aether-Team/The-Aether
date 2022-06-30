package com.gildedgames.aether.client.gui.component;

import com.gildedgames.aether.capability.AetherCapabilities;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.SunAltarUpdatePacket;
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
        long time = (long) (this.value * 72000);
        level.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY).ifPresent(aetherTime -> {
            AetherPacketHandler.sendToServer(new SunAltarUpdatePacket(time));
        });
    }

}
