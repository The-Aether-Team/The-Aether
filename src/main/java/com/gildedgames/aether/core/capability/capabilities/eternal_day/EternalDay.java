package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.AetherTimePacket;
import com.gildedgames.aether.core.network.packet.client.CheckTimePacket;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
import com.gildedgames.aether.core.network.packet.client.ServerTimePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class EternalDay implements IEternalDay
{
    private final Level world;

    private boolean isEternalDay = true;
    private boolean shouldCheckTime = true;
    private long aetherTime = 18000L;
    private long serverWorldTime;

    public EternalDay(Level world) {
        this.world = world;
    }

    @Override
    public Level getWorld() {
        return this.world;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("EternalDay", this.getEternalDay());
        nbt.putBoolean("CheckTime", this.getCheckTime());
        nbt.putLong("Time", this.getAetherTime());
        nbt.putLong("ServerWorldTime", this.getServerWorldTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("EternalDay")) {
            this.setEternalDay(nbt.getBoolean("EternalDay"));
        }
        if (nbt.contains("CheckTime")) {
            this.setCheckTime(nbt.getBoolean("CheckTime"));
        }
        if (nbt.contains("Time")) {
            this.setAetherTime(nbt.getLong("Time"));
        }
        if (nbt.contains("ServerWorldTime")) {
            this.setServerWorldTime(nbt.getLong("ServerWorldTime"));
        }
    }

    @Override
    public void syncToClient() {
        this.setEternalDay(this.getEternalDay());
        this.setCheckTime(this.getCheckTime());
        this.setAetherTime(this.getAetherTime());
        this.setServerWorldTime(this.getServerWorldTime());
    }

    @Override
    public void serverTick(ServerLevel world) {
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            if (!AetherConfig.COMMON.disable_eternal_day.get()) {
                if (this.getCheckTime()) {
                    if (!this.getEternalDay()) {
                        long dayTime = world.getDayTime() % 72000;
                        if (dayTime != this.getAetherTime()) {
                            this.setAetherTime((long) Mth.approach(this.getAetherTime(), dayTime, 10.0F));
                        } else {
                            this.setCheckTime(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.sendEternalDay(isEternalDay);
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }

    @Override
    public void setCheckTime(boolean shouldCheckTime) {
        this.sendCheckTime(shouldCheckTime);
        this.shouldCheckTime = shouldCheckTime;
    }

    @Override
    public boolean getCheckTime() {
        return this.shouldCheckTime;
    }

    @Override
    public void setAetherTime(long time) {
        this.sendAetherTime(time);
        this.aetherTime = time;
    }

    @Override
    public long getAetherTime() {
        return this.aetherTime;
    }

    @Override
    public void setServerWorldTime(long time) {
        this.sendServerWorldTime(time);
        this.serverWorldTime = time;
    }

    @Override
    public long getServerWorldTime() {
        return this.serverWorldTime;
    }

    private void sendEternalDay(boolean eternalDay) {
        if (this.getWorld() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new EternalDayPacket(eternalDay));
        }
    }

    private void sendCheckTime(boolean checkTime) {
        if (this.getWorld() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new CheckTimePacket(checkTime));
        }
    }

    private void sendAetherTime(long time) {
        if (this.getWorld() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new AetherTimePacket(time));
        }
    }

    private void sendServerWorldTime(long time) {
        if (this.getWorld() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new ServerTimePacket(time));
        }
    }
}
