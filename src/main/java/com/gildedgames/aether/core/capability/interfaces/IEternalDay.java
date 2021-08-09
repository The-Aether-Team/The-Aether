package com.gildedgames.aether.core.capability.interfaces;

import com.gildedgames.aether.core.capability.AetherCapabilities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public interface IEternalDay extends INBTSerializable<CompoundNBT>
{
    World getWorld();

    static LazyOptional<IEternalDay> get(World world) {
        return world.getCapability(AetherCapabilities.ETERNAL_DAY_CAPABILITY);
    }

    void syncToClient();

    void serverTick(ServerWorld world);

    void setEternalDay(boolean isEternalDay);
    boolean getEternalDay();

    void setCheckTime(boolean shouldCheckTime);
    boolean getCheckTime();

    void setAetherTime(long time);
    long getAetherTime();

    void setServerWorldTime(long time);
    long getServerWorldTime();
}
