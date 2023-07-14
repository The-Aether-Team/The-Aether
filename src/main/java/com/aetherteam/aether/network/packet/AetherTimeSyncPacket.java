package com.aetherteam.aether.network.packet;

import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncLevelPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Sync packet for values in the {@link com.aetherteam.aether.capability.time.AetherTimeCapability} class.
 */
public class AetherTimeSyncPacket extends SyncLevelPacket<AetherTime> {
    public AetherTimeSyncPacket(Triple<String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherTimeSyncPacket(String key, INBTSynchable.Type type, Object value) {
        super(key, type, value);
    }

    public static AetherTimeSyncPacket decode(FriendlyByteBuf buf) {
        return new AetherTimeSyncPacket(SyncLevelPacket.decodeValues(buf));
    }

    @Override
    protected LazyOptional<AetherTime> getCapability(Level level) {
        return AetherTime.get(level);
    }
}
