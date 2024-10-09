package com.aetherteam.aether.attachment;

import com.aetherteam.aether.network.packet.PhoenixArrowSyncPacket;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Capability class to store whether a shot {@link AbstractArrow} is marked as having been shot from a Phoenix Bow or not. This capability works for all arrow types.
 *
 * @see com.aetherteam.aether.event.hooks.AbilityHooks.WeaponHooks#phoenixArrowHit(HitResult, Projectile)
 * @see com.aetherteam.aether.mixin.mixins.common.AbstractArrowMixin
 * @see com.aetherteam.aether.mixin.mixins.client.TippableArrowRendererMixin
 */
public class PhoenixArrowAttachment implements INBTSynchable {
    private boolean isPhoenixArrow;
    private int fireTime;

    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setPhoenixArrow", Triple.of(Type.BOOLEAN, (object) -> this.setPhoenixArrow((boolean) object), this::isPhoenixArrow))
    );

    public static final Codec<PhoenixArrowAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("is_phoenix_arrow").forGetter(PhoenixArrowAttachment::isPhoenixArrow),
            Codec.INT.fieldOf("fire_time").forGetter(PhoenixArrowAttachment::getFireTime)
    ).apply(instance, PhoenixArrowAttachment::new));

    public PhoenixArrowAttachment() {
        this(false, 0);
    }

    private PhoenixArrowAttachment(boolean isPhoenixArrow, int fireTime) {
        this.setPhoenixArrow(isPhoenixArrow);
        this.setFireTime(fireTime);
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    public void setPhoenixArrow(boolean isPhoenixArrow) {
        this.isPhoenixArrow = isPhoenixArrow;
    }

    /**
     * @return Whether an arrow is a Phoenix Arrow, as a {@link Boolean}.
     */
    public boolean isPhoenixArrow() {
        return this.isPhoenixArrow;
    }

    public void setFireTime(int time) {
        this.fireTime = time;
    }

    /**
     * @return How many ticks an entity shot by a Phoenix Arrow should stay on fire, as an {@link Integer}.
     */
    public int getFireTime() {
        return this.fireTime;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new PhoenixArrowSyncPacket(entityID, key, type, value);
    }
}
