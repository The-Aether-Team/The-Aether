package com.gildedgames.aether.core.capability.capabilities.aether_time;

import com.gildedgames.aether.core.AetherConfig;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.client.AetherTimePacket;
import com.gildedgames.aether.core.network.packet.client.EternalDayPacket;
import com.gildedgames.aether.server.player.AetherSleepStatus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.util.stream.Collectors;

/**
 * Capability class to handle the Aether's custom day/night cycle.
 * This class makes the day/night cycle longer depending on the time factor, and handles eternal day.
 * This capability should ONLY be attached to the Aether dimension!!!
 */
public class AetherTime implements IAetherTime {
    private final Level level;
    private boolean isEternalDay = true;
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

    /**
     * Sends eternal day and Aether time values to the client.
     */
    @Override
    public void syncToClient() {
        this.updateDayTime();
        this.updateEternalDay();
    }

    /**
     * Ticks time in the Aether. This method is called on the server.
     */
    @Override
    public void serverTick(ServerLevel level) {
        if (this.isEternalDay && !AetherConfig.COMMON.disable_eternal_day.get()) {
            if (this.dayTime != 18000L) {
                long target = Mth.clamp(18000L - this.dayTime, -10, 10);
                this.dayTime += target;
            }
        } else {
            this.handleSleep(level);
            if (level.getLevelData().getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                this.dayTime += 1L;
            }
        }
        this.updateDayTime();
    }

    private void handleSleep(ServerLevel level) {
        int i = level.getGameRules().getInt(GameRules.RULE_PLAYERS_SLEEPING_PERCENTAGE);
        if (level.sleepStatus instanceof AetherSleepStatus sleepStatus && sleepStatus.areEnoughSleeping(i) && sleepStatus.areEnoughDeepSleepingInAether(i, level.players())) {
            if (level.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                long j = this.getDayTime() + 72000L;
                this.setDayTime(j - j % 72000L);
            }
            this.wakeUpAllPlayers(level);
        }
    }

    private void wakeUpAllPlayers(ServerLevel level) {
        level.sleepStatus.removeAllSleepers();
        level.players().stream().filter(LivingEntity::isSleeping).collect(Collectors.toList()).forEach((p_184116_) -> {
            p_184116_.stopSleepInBed(false, false);
        });
    }

    /**
     * Sends the eternal day value to the client.
     */
    @Override
    public void updateEternalDay() {
        AetherPacketHandler.sendToAll(new EternalDayPacket(this.isEternalDay));
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
     * Sends the Aether time value to the client.
     */
    @Override
    public void updateDayTime() {
        AetherPacketHandler.sendToAll(new AetherTimePacket(this.dayTime));
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
