package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EternalDay implements IEternalDay
{
    private final World world;

    private boolean isEternalDay;
    private long time;

    public EternalDay(World world) {
        this.world = world;
        this.isEternalDay = true;
        this.time = 6000L;
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putBoolean("EternalDay", this.getEternalDay());
        nbt.putLong("Time", this.getAetherTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("EternalDay")) {
            this.setEternalDay(nbt.getBoolean("EternalDay"));
        }
        if (nbt.contains("Time")) {
            this.setAetherTime(nbt.getLong("Time"));
        }
    }

    //TODO: It seems like a better idea to not let packets handle this stuff for performance reasons and instead try to just find a way to keep the aether's time synced between client and server through
    //however vanilla does it, but don't let it be overridden by other serverWorld times.

    @Override
    public void serverTick(ServerWorld world) {
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            world.setDayTime(6000L);
            Aether.LOGGER.info(world.getDayTime());
        }

        //Aether.LOGGER.info("Server: " + this.getEternalDay());
        //Aether.LOGGER.info("Server: " + this.getAetherTime());
//            if (!this.getEternalDay() || AetherConfig.COMMON.disable_eternal_day.get()) {
//                if (serverWorld.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
//                    if (serverWorld.getGameTime() % 3 == 0) {
//                        this.setAetherTime(this.getAetherTime() + 1L);
//                    }
//                }
//            }

    }

    @Override
    public void clientTick(ClientWorld world) {
        //Aether.LOGGER.info("Client: " + this.getEternalDay());
        //Aether.LOGGER.info("Client: " + this.getAetherTime());
//        if (!this.getEternalDay() || AetherConfig.COMMON.disable_eternal_day.get()) {
//            if (world.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
//                if (world.getGameTime() % 3 == 0) {
//                    this.setAetherTime(this.getAetherTime() + 1L);
//                }
//            }
//        }
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            world.setDayTime(this.getAetherTime());
            //Aether.LOGGER.info(world.getDayTime());
        }
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return isEternalDay;
    }

    @Override
    public void setAetherTime(long time) {
        this.time = time;
    }

    @Override
    public long getAetherTime() {
        return this.time;
    }
}
