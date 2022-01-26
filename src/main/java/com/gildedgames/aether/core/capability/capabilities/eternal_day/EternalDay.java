package com.gildedgames.aether.core.capability.capabilities.eternal_day;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.EternalDaySerializable;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.AetherTimePacket;
import com.gildedgames.aether.core.network.packet.client.CheckTimePacket;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
import com.gildedgames.aether.core.network.packet.client.ServerTimePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class EternalDay implements EternalDaySerializable
{
    private final Level level;

    private boolean isEternalDay = true;
    private boolean shouldCheckTime = true;
    private long aetherTime = 18000L;
    private long serverLevelTime;

    public EternalDay(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putBoolean("EternalDay", this.getEternalDay());
        compound.putBoolean("CheckTime", this.getCheckTime());
        compound.putLong("Time", this.getAetherTime());
        compound.putLong("ServerLevelTime", this.getServerLevelTime());
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        if (compound.contains("EternalDay")) {
            this.setEternalDay(compound.getBoolean("EternalDay"));
        }
        if (compound.contains("CheckTime")) {
            this.setCheckTime(compound.getBoolean("CheckTime"));
        }
        if (compound.contains("Time")) {
            this.setAetherTime(compound.getLong("Time"));
        }
        if (compound.contains("ServerLevelTime")) {
            this.setServerLevelTime(compound.getLong("ServerLevelTime"));
        }
    }

    @Override
    public void syncToClient() {
        this.setEternalDay(this.getEternalDay());
        this.setCheckTime(this.getCheckTime());
        this.setAetherTime(this.getAetherTime());
        this.setServerLevelTime(this.getServerLevelTime());
    }

    @Override
    public void serverTick(ServerLevel level) {
        if (level.dimension() == AetherDimensions.AETHER_WORLD) {
            if (!AetherConfig.COMMON.disable_eternal_day.get()) {
                if (this.getCheckTime()) {
                    if (!this.getEternalDay()) {
                        long dayTime = level.getDayTime() % 72000;
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
    public void setServerLevelTime(long time) {
        this.sendServerLevelTime(time);
        this.serverLevelTime = time;
    }

    @Override
    public long getServerLevelTime() {
        return this.serverLevelTime;
    }

    private void sendEternalDay(boolean eternalDay) {
        if (this.getLevel() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new EternalDayPacket(eternalDay));
        }
    }

    private void sendCheckTime(boolean checkTime) {
        if (this.getLevel() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new CheckTimePacket(checkTime));
        }
    }

    private void sendAetherTime(long time) {
        if (this.getLevel() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new AetherTimePacket(time));
        }
    }

    private void sendServerLevelTime(long time) {
        if (this.getLevel() instanceof ServerLevel) {
            AetherPacketHandler.sendToAll(new ServerTimePacket(time));
        }
    }
}
