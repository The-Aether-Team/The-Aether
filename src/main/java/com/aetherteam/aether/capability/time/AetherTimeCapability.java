package com.aetherteam.aether.capability.time;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.simple.SimpleChannel;
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
    private final Level level;
    private long dayTime = 18000L;
    private boolean isEternalDay = true;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setEternalDay", Triple.of(Type.BOOLEAN, (object) -> this.setEternalDay((boolean) object), this::getEternalDay))
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

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new AetherTimeSyncPacket(key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return AetherPacketHandler.INSTANCE;
    }
}
