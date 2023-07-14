package com.aetherteam.aether.network.packet;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import oshi.util.tuples.Quartet;

public class AetherPlayerSyncPacket extends SyncEntityPacket<AetherPlayer> {
    public AetherPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public AetherPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static AetherPlayerSyncPacket decode(FriendlyByteBuf buf) {
        return new AetherPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    protected LazyOptional<AetherPlayer> getCapability(Entity entity) {
        return AetherPlayer.get((Player) entity);
    }
}
