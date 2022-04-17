package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.rankings.AetherRankings;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import static com.gildedgames.aether.core.network.AetherPacket.*;

public class AetherRankingsServerSyncPacket extends AbstractAetherPacket {
    private final int playerID;
    private final CompoundTag tag;

    public AetherRankingsServerSyncPacket(int playerID, CompoundTag tag) {
        this.playerID = playerID;
        this.tag = tag;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeNbt(this.tag);
    }

    public static AetherRankingsServerSyncPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        CompoundTag tag = buf.readNbt();
        return new AetherRankingsServerSyncPacket(playerID, tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            AetherRankings.get(serverPlayer).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
        }
    }
}
