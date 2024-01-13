package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.capability.AetherCapabilities;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * Capability interface to handle the Aether's day/night cycle.
 */
public interface AetherTime extends INBTSynchable<CompoundTag>, Component {
    Level getLevel();

    static Optional<AetherTime> get(Level world) {
        return AetherCapabilities.AETHER_TIME_CAPABILITY.maybeGet(world);
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
