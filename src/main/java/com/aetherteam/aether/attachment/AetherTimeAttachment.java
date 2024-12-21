package com.aetherteam.aether.attachment;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.aether.world.AetherLevelData;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class to store data for the Aether's custom day/night cycle.
 * This capability only has an effect on levels where the dimension type's effects are set to the Aether's.
 *
 * @see com.aetherteam.aether.event.hooks.CapabilityHooks.AetherTimeHooks
 */
public class AetherTimeAttachment implements INBTSynchable {
    private long dayTime = -1;
    private boolean isEternalDay = true;
    private boolean shouldWait = false;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setEternalDay", Triple.of(Type.BOOLEAN, (object) -> this.setEternalDay((boolean) object), this::isEternalDay)),
            Map.entry("setShouldWait", Triple.of(Type.BOOLEAN, (object) -> this.setShouldWait((boolean) object), this::getShouldWait))
    );

    public static final Codec<AetherTimeAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("day_time").forGetter(AetherTimeAttachment::getDayTime),
            Codec.BOOL.fieldOf("eternal_day").forGetter(AetherTimeAttachment::isEternalDay),
            Codec.BOOL.fieldOf("should_wait").forGetter(AetherTimeAttachment::getShouldWait)
    ).apply(instance, AetherTimeAttachment::new));

    protected AetherTimeAttachment() {

    }

    private AetherTimeAttachment(long time, boolean eternalDay, boolean shouldWait) {
        this.setDayTime(time);
        this.setEternalDay(eternalDay);
        this.setShouldWait(shouldWait);
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    /**
     * Used to increment the time in Aether levels.
     */
    public long tickTime(Level level) {
        long dayTime = level.getDayTime();
        if (this.getDayTime() == -1) {
            dayTime = getTicksPerDay() / 4;
        }
        if (!this.isTimeSynced()) {
            if (this.isEternalDay() || this.getShouldWait()) {
                if (dayTime != getTicksPerDay() / 4) {
                    long tempTime = dayTime % (long) getTicksPerDay();
                    if (tempTime > getTicksPerDay() * 0.75) {
                        tempTime -= getTicksPerDay();
                    }
                    long target = Mth.clamp((getTicksPerDay() / 4) - tempTime, -10, 10);
                    dayTime += target;
                }
                if (!level.isClientSide() && level.getLevelData() instanceof AetherLevelData aetherLevelData) {
                    if (AetherConfig.SERVER.sync_aether_time.get()) {
                        if (aetherLevelData.getOverworldDayTime() == aetherLevelData.getDayTime()) {
                            this.setSynched(-1, Direction.DIMENSION, "setShouldWait", false, level);
                        }
                    } else if (this.getShouldWait()) {
                        this.setSynched(-1, Direction.DIMENSION, "setShouldWait", false, level);
                    }
                }
            } else {
                dayTime++;
            }
        }
        this.setDayTime(dayTime);
        return dayTime;
    }

    /**
     * Sends the eternal day value to the client dimension.
     */
    public void updateEternalDay(Level level) {
        this.setSynched(-1, Direction.DIMENSION, "setEternalDay", this.isEternalDay, level);
    }

    /**
     * Sends the eternal day value to the client player.
     */
    public void updateEternalDay(ServerPlayer player) {
        this.setSynched(player.getId(), Direction.PLAYER, "setEternalDay", this.isEternalDay, player);
    }

    public void setDayTime(long time) {
        this.dayTime = time;
    }

    /**
     * @return A {@link Long} for the time in the Aether.
     */
    public long getDayTime() {
        return this.dayTime;
    }

    public void setEternalDay(boolean isEternalDay) {
        this.isEternalDay = isEternalDay;
    }

    /**
     * @return Whether eternal day is active, as a {@link Boolean}.
     */
    public boolean isEternalDay() {
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
        return AetherConfig.SERVER.sync_aether_time.get() && !this.isEternalDay() && !this.getShouldWait();
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new AetherTimeSyncPacket(key, type, value);
    }

    public static int getTicksPerDayMultiplier() {
        return AetherConfig.SERVER.normal_length_aether_time.get() || AetherConfig.SERVER.sync_aether_time.get() ? 1 : 3;
    }

    public static int getTicksPerDay() {
        return Level.TICKS_PER_DAY * getTicksPerDayMultiplier(); // Time in ticks of how long a day/night cycle lasts.
    }
}
