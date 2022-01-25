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
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("EternalDay", this.getEternalDay());
        nbt.putBoolean("CheckTime", this.getCheckTime());
        nbt.putLong("Time", this.getAetherTime());
        nbt.putLong("ServerLevelTime", this.getServerLevelTime());
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
        if (nbt.contains("ServerLevelTime")) {
            this.setServerLevelTime(nbt.getLong("ServerLevelTime"));
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
