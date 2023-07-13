package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.EternalDayPacket;
import com.aetherteam.nitrogen.network.PacketRelay;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

/**
 * Capability class to store data for the Aether's custom day/night cycle.
 * This capability only has an effect on levels where the dimension type's effects are set to the Aether's.
 */
public class AetherTimeCapability implements AetherTime {
    private final Level level;
    private long dayTime = 18000L;
    private boolean isEternalDay = true;

    public AetherTimeCapability(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("DayTime", this.level.getDayTime());
        tag.putBoolean("EternalDay", this.getEternalDay());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("DayTime")) {
            this.setDayTime(tag.getLong("DayTime"));
        }
        if (tag.contains("EternalDay")) {
            this.setEternalDay(tag.getBoolean("EternalDay"));
        }
    }

    /**
     * Used to increment the time in Aether levels.
     */
    @Override
    public long tickTime(Level level) {
        long dayTime = level.getDayTime();
        if (this.getEternalDay()) {
            if (dayTime != 18000L) {
                long tempTime = dayTime % (long) AetherDimensions.AETHER_TICKS_PER_DAY;
                if (tempTime > 54000L) {
                    tempTime -= AetherDimensions.AETHER_TICKS_PER_DAY;
                }
                long target = (long) Mth.clamp(18000L - tempTime, -10, 10);
                dayTime += target;
            }
        } else {
            dayTime++;
        }
        return dayTime;
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay() {
        PacketRelay.sendToDimension(AetherPacketHandler.INSTANCE, new EternalDayPacket(this.isEternalDay), this.level.dimension());
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay(ServerPlayer player) {
        PacketRelay.sendToPlayer(AetherPacketHandler.INSTANCE, new EternalDayPacket(this.isEternalDay), player);
    }

    @Override
    public void setDayTime(long time) {
        this.dayTime = time;
    }

    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }
}
