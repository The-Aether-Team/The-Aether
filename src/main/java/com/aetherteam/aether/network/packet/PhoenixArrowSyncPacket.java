package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import oshi.util.tuples.Quartet;

import java.util.Optional;

/**
 * Sync packet for values in the {@link com.aetherteam.aether.capability.arrow.PhoenixArrowCapability} class.
 */
public class PhoenixArrowSyncPacket extends SyncEntityPacket<PhoenixArrow> {
    public PhoenixArrowSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public PhoenixArrowSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static PhoenixArrowSyncPacket decode(FriendlyByteBuf buf) {
        return new PhoenixArrowSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Optional<PhoenixArrow> getCapability(Entity entity) {
        return PhoenixArrow.get((AbstractArrow) entity);
    }
}