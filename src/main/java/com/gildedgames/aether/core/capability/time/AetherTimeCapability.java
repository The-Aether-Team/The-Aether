package com.gildedgames.aether.core.capability.time;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
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
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("dayTime", this.level.getDayTime());
        tag.putBoolean("EternalDay", this.getEternalDay());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("dayTime")) {
            this.setDayTime(tag.getLong("dayTime"));
        }
        if (tag.contains("EternalDay")) {
            this.setEternalDay(tag.getBoolean("EternalDay"));
        }
    }

    @Override
    public void setDayTime(long time) {
        this.dayTime = time;
    }

    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay() {
        AetherPacketHandler.sendToDimension(new EternalDayPacket(this.isEternalDay), this.level.dimension());
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay(ServerPlayer player) {
        AetherPacketHandler.sendToPlayer(new EternalDayPacket(this.isEternalDay), player);
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }

    /**
     * Used to increment the time in Aether levels.
     */
    @Override
    public long tickTime(Level level) {
        long dayTime = level.getDayTime();
        if (this.getEternalDay() && !AetherConfig.COMMON.disable_eternal_day.get()) {
            if (dayTime != 18000L) {
                long tempTime = dayTime % 72000L;
                if (tempTime > 54000L) {
                    tempTime -= 72000L;
                }
                long target = Mth.clamp(18000L - tempTime, -10, 10);
                dayTime += target;
            }
        } else {
            dayTime++;
        }
        return dayTime;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }
}
