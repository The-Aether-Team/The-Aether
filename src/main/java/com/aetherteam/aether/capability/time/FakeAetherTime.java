package com.aetherteam.aether.capability.time;

import com.aetherteam.nitrogen.network.BasePacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

// TODO: Stop depending on cardinal components
public class FakeAetherTime implements AetherTime {
    @Override
    public Level getLevel() {
        return null;
    }

    @Override
    public long tickTime(Level level) {
        return 0;
    }

    @Override
    public void updateEternalDay() {

    }

    @Override
    public void updateEternalDay(ServerPlayer player) {

    }

    @Override
    public void setDayTime(long time) {

    }

    @Override
    public long getDayTime() {
        return 0;
    }

    @Override
    public void setEternalDay(boolean isEternalDay) {

    }

    @Override
    public boolean getEternalDay() {
        return false;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return null;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return null;
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return null;
    }

    /**
     * Saves data on world close.
     */
    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    /**
     * Restores data from world on open.
     */
    @Override
    public void deserializeNBT(CompoundTag tag) {
    }
}
