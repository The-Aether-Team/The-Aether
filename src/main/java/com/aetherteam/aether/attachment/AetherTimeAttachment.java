package com.aetherteam.aether.attachment;

import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.network.packet.AetherTimeSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
    private long dayTime = 18000L;
    private boolean isEternalDay = true;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setEternalDay", Triple.of(Type.BOOLEAN, (object) -> this.setEternalDay((boolean) object), this::isEternalDay))
    );

    public static final Codec<AetherTimeAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.fieldOf("day_time").forGetter(AetherTimeAttachment::getDayTime),
            Codec.BOOL.fieldOf("eternal_day").forGetter(AetherTimeAttachment::isEternalDay)
    ).apply(instance, AetherTimeAttachment::new));

    protected AetherTimeAttachment() {

    }

    private AetherTimeAttachment(long time, boolean eternalDay) {
        this.setDayTime(time);
        this.setEternalDay(eternalDay);
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
        if (this.isEternalDay()) {
            if (dayTime != 18000L) {
                long tempTime = dayTime % (long) AetherDimensions.AETHER_TICKS_PER_DAY;
                if (tempTime > 54000L) {
                    tempTime -= AetherDimensions.AETHER_TICKS_PER_DAY;
                }
                long target = Mth.clamp(18000L - tempTime, -10, 10);
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
    public void updateEternalDay(Level level) {
        this.setSynched(-1, Direction.DIMENSION, "setEternalDay", this.isEternalDay, level.dimension());
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

    @Override
    public CustomPacketPayload getSyncPacket(int entityID, String key, Type type, Object value) {
        return new AetherTimeSyncPacket(key, type, value);
    }
}
