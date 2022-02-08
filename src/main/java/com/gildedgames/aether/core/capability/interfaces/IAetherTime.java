package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import com.gildedgames.aether.server.player.AetherSleepStatus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Capability interface to handle the Aether's day/night cycle.
 */
public interface IAetherTime extends INBTSerializable<CompoundTag>
{
    Level getLevel();

    static LazyOptional<IAetherTime> get(Level world) {
        return world.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY);
    }

    /**
     * Sends eternal day and Aether time values to the client.
     */
    void syncToClient();

    void serverTick(ServerLevel world);

    /**
     * Sends the eternal day value to the client.
     */
    void updateEternalDay();
    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();

    /**
     * Sends the Aether time value to the client.
     */
    void updateDayTime();
    void setDayTime(long time);
    long getDayTime();
}
