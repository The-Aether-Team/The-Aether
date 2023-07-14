package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Capability interface to handle the Aether's day/night cycle.
 */
public interface AetherTime extends INBTSynchable<CompoundTag> {
    Level getLevel();

    static LazyOptional<AetherTime> get(Level world) {
        return world.getCapability(AetherCapabilities.AETHER_TIME_CAPABILITY);
    }

    long tickTime(Level level);

    /**
     * Sends the eternal day value to the client.
     */
    void updateEternalDay();
    void updateEternalDay(ServerPlayer player);

    void setDayTime(long time);
    long getDayTime();

    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();
}
