package com.aetherteam.aether.capability.arrow;

import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class to store whether a shot {@link AbstractArrow} is marked as having been shot from a Phoenix Bow or not. This capability works for all arrow types.
 * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#phoenixArrowHit(HitResult, Projectile)
 * @see com.aetherteam.aether.mixin.mixins.common.AbstractArrowMixin
 * @see com.aetherteam.aether.mixin.mixins.client.TippableArrowRendererMixin
 */
public class PhoenixArrowCapability implements PhoenixArrow {
    private final AbstractArrow arrow;

    private boolean isPhoenixArrow;
    private int fireTime;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
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

    /**
     * Saves data on world close.
     */
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("PhoenixArrow", this.isPhoenixArrow());
        tag.putInt("FireTime", this.getFireTime());
        return tag;
    }

    /**
     * Restores data from world on open.
     */
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

    /**
     * @return Whether an arrow is a Phoenix Arrow, as a {@link Boolean}.
     */
    @Override
    public boolean isPhoenixArrow() {
        return this.isPhoenixArrow;
    }

    @Override
    public void setFireTime(int time) {
        this.fireTime = time;
    }

    /**
     * @return How many ticks an entity shot by a Phoenix Arrow should stay on fire, as an {@link Integer}.
     */
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
