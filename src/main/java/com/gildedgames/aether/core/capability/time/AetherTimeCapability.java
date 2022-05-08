package com.gildedgames.aether.core.capability.time;

import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

/**
 * Capability class to handle the Aether's custom day/night cycle.
 * This class makes the day/night cycle longer depending on the time factor, and handles eternal day.
 * This capability should ONLY be attached to the Aether dimension!!!
 */
public class AetherTimeCapability implements AetherTime {
    private final Level level;
    private boolean isEternalDay = true;

    public AetherTimeCapability(Level level) {
        this.level = level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("EternalDay", this.getEternalDay());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("EternalDay")) {
            this.setEternalDay(tag.getBoolean("EternalDay"));
        }
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay() {
        AetherPacketHandler.sendToAll(new EternalDayPacket(this.isEternalDay));
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }
}
