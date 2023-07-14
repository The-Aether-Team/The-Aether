package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PhoenixArrowCapability implements PhoenixArrow {
    private final AbstractArrow arrow;

    private boolean isPhoenixArrow;
    private int fireTime;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setPhoenixArrow", Triple.of(Type.BOOLEAN, (object) -> this.setPhoenixArrow((boolean) object), this::isPhoenixArrow))
    );

    public PhoenixArrowCapability(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public AbstractArrow getArrow() {
        return this.arrow;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        tag.putInt("FireTime", this.getFireTime());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("PhoenixArrow")) {
            this.setPhoenixArrow(tag.getBoolean("PhoenixArrow"));
        }
        if (tag.contains("FireTime")) {
            this.setFireTime(tag.getInt("FireTime"));
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public void setPhoenixArrow(boolean isPhoenixArrow) {
        this.isPhoenixArrow = isPhoenixArrow;
    }

    @Override
    public boolean isPhoenixArrow() {
        return this.isPhoenixArrow;
    }

    @Override
    public void setFireTime(int time) {
        this.fireTime = time;
    }

    @Override
    public int getFireTime() {
        return this.fireTime;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new PhoenixArrowSyncPacket(this.getArrow().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return AetherPacketHandler.INSTANCE;
    }
}
