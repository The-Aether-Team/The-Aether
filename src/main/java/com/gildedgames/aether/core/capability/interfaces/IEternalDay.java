package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IEternalDay extends INBTSerializable<CompoundTag>
{
    Level getLevel();

    static LazyOptional<IEternalDay> get(Level world) {
        return world.getCapability(AetherCapabilities.ETERNAL_DAY_CAPABILITY);
    }

    void syncToClient();

    void serverTick(ServerLevel world);

    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();

    void setCheckTime(boolean shouldCheckTime);
    boolean getCheckTime();

    void setAetherTime(long time);
    long getAetherTime();

    void setServerLevelTime(long time);
    long getServerLevelTime();
}
