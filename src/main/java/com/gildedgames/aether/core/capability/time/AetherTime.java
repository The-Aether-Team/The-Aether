package com.gildedgames.aether.core.capability.time;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Capability interface to handle the Aether's day/night cycle.
 */
public interface AetherTime extends INBTSerializable<CompoundTag> {
    Level getLevel();

    static LazyOptional<AetherTime> get(Level world) {
        return world.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY);
    }

    /**
     * Sends the eternal day value to the client.
     */
    void updateEternalDay();
    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();
    long correctTimeOfDay(Level level);
}
