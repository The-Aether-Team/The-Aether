package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.aether.world.AetherLevelData;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class to store data for the Aether's custom day/night cycle.
 * This capability only has an effect on levels where the dimension type's effects are set to the Aether's.
 * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherTimeHooks
 */
public class AetherTimeCapability implements AetherTime {
    private static int ticksPerDayMultiplier = -1;
    private final Level level;
    private long dayTime = -1;
    private boolean isEternalDay = true;
    private boolean shouldWait = false;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setEternalDay", Triple.of(Type.BOOLEAN, (object) -> this.setEternalDay((boolean) object), this::getEternalDay)),
            Map.entry("setShouldWait", Triple.of(Type.BOOLEAN, (object) -> this.setShouldWait((boolean) object), this::getShouldWait))
    );

    public AetherTimeCapability(Level level) {
        this.level = level;
    }

    @Override
    public Level getLevel() {
        return this.level;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Saves data on world close.
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("DayTime", this.level.getDayTime());
        tag.putBoolean("EternalDay", this.getEternalDay());
        tag.putBoolean("ShouldWait", this.getShouldWait());
        return tag;
    }

    /**
     * Restores data from world on open.
     */
    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("DayTime")) {
            this.setDayTime(tag.getLong("DayTime"));
        }
        if (tag.contains("EternalDay")) {
            this.setEternalDay(tag.getBoolean("EternalDay"));
        }
        if (tag.contains("ShouldWait")) {
            this.setShouldWait(tag.getBoolean("ShouldWait"));
        }
    }

    /**
     * Used to increment the time in Aether levels.
     */
    @Override
    public long tickTime(Level level) {
        long dayTime = level.getDayTime();
        if (this.getDayTime() == -1 && !AetherConfig.SERVER.disable_eternal_day.get()) {
            dayTime = getTicksPerDay() / 4;
        }
        dayTime = this.configsChanged(dayTime);
        if (!this.isTimeSynced()) {
            if (this.getEternalDay() || this.getShouldWait()) {
                if (dayTime != getTicksPerDay() / 4) {
                    long tempTime = dayTime % (long) getTicksPerDay();
                    if (tempTime > getTicksPerDay() * 0.75) {
                        tempTime -= getTicksPerDay();
                    }
                    long target = (long) Mth.clamp(((float) getTicksPerDay() / 4) - tempTime, -10, 10);
                    dayTime += target;
                }
                if (!level.isClientSide() && level.getLevelData() instanceof AetherLevelData aetherLevelData) {
                    if (AetherConfig.SERVER.sync_aether_time.get()) {
                        if (aetherLevelData.getOverworldDayTime() == aetherLevelData.getDayTime()) {
                            this.setSynched(Direction.DIMENSION, "setShouldWait", false, level);
                        }
                    } else if (this.getShouldWait()) {
                        this.setSynched(Direction.DIMENSION, "setShouldWait", false, level);
                    }
                }
            } else {
                dayTime++;
            }
        }
        this.setDayTime(dayTime);
        return dayTime;
    }

    private long configsChanged(long time) {
        int multiplier = configMultiplier();
        if (ticksPerDayMultiplier != multiplier) {
            ticksPerDayMultiplier = multiplier;
        }
        return time;
    }

    /**
     * Sends the eternal day value to the client dimension.
     */
    @Override
    public void updateEternalDay() {
        this.setSynched(Direction.DIMENSION, "setEternalDay", this.isEternalDay, this.level.dimension());
    }

    /**
     * Sends the eternal day value to the client player.
     */
    @Override
    public void updateEternalDay(ServerPlayer player) {
        this.setSynched(Direction.PLAYER, "setEternalDay", this.isEternalDay, player);
    }

    @Override
    public void setDayTime(long time) {
        this.dayTime = time;
    }

    /**
     * @return A {@link Long} for the time in the Aether.
     */
    @Override
    public long getDayTime() {
        return this.dayTime;
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    /**
     * @return Whether eternal day is active, as a {@link Boolean}.
     */
    @Override
    public boolean getEternalDay() {
        return this.isEternalDay;
    }

    public void setShouldWait(boolean shouldWait) {
        this.shouldWait = shouldWait;
    }

    /**
     * @return Whether there should be a wait to disable eternal day, as a {@link Boolean}.
     */
    public boolean getShouldWait() {
        return this.shouldWait;
    }

    public boolean isTimeSynced() {
        return AetherConfig.SERVER.sync_aether_time.get() && !this.getEternalDay() && !this.getShouldWait();
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new AetherTimeSyncPacket(key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return AetherPacketHandler.INSTANCE;
    }

    public static int getTicksPerDayMultiplier() {
        if (ticksPerDayMultiplier < 0) {
            ticksPerDayMultiplier = AetherConfig.SERVER.normal_length_aether_time.get() || AetherConfig.SERVER.sync_aether_time.get() ? 1 : 3;
        }
        return ticksPerDayMultiplier;
    }

    public static int getTicksPerDay() {
        return Level.TICKS_PER_DAY * getTicksPerDayMultiplier(); // Time in ticks of how long a day/night cycle lasts.
    }

    public static int configMultiplier() {
        return AetherConfig.SERVER.normal_length_aether_time.get() || AetherConfig.SERVER.sync_aether_time.get() ? 1 : 3;
    }
}
