package com.gildedgames.aether.core.capability.capabilities.aether_time;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

/**
 * Capability class to handle the Aether's custom day/night cycle.
 * This class makes the day/night cycle longer depending on the time factor, and handles eternal day.
 * This capability should ONLY be attached to the Aether dimension!!!
 */
public class AetherTime implements IAetherTime {
    private final Level level;
    private boolean isEternalDay = !AetherConfig.COMMON.disable_eternal_day.get();
    private long dayTime = 18000L;

    public AetherTime(Level level) {
        this.level = level;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("EternalDay", this.getEternalDay());
        nbt.putLong("DayTime", this.getDayTime());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("EternalDay")) {
            this.setEternalDay(nbt.getBoolean("EternalDay"));
        }
        if (nbt.contains("DayTime")) {
            this.setDayTime(nbt.getLong("DayTime"));
        }
    }

    @Override
    public void syncToClient() {}

    /**
     * Tick time in the Aether
     */
    @Override
    public void tick(Level level) {
        if (this.isEternalDay) {
            if (this.dayTime != 18000L) {
                long target = Mth.clamp(18000L - this.dayTime, -10, 10);
                this.dayTime += target;
            }
        } else if (level.getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
            this.dayTime += 1L;
            if (!level.isClientSide) {
                ((ServerLevel) level).setDayTime(this.getDayTime());
            }
        }
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
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
    public Level getLevel() {
        return this.level;
    }
}
