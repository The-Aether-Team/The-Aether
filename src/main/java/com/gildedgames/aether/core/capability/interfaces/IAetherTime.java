package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
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

    void syncToClient();

    void tick(Level world);

    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();

    void setDayTime(long time);
    long getDayTime();
}
