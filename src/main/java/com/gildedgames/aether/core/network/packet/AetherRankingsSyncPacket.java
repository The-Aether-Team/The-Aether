package com.gildedgames.aether.core.network.packet;

import com.gildedgames.aether.core.capability.rankings.AetherRankings;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class AetherRankingsSyncPacket extends AbstractAetherPacket {
    private final int playerID;
    private final CompoundTag tag;

    public AetherRankingsSyncPacket(int playerID, CompoundTag tag) {
        this.playerID = playerID;
        this.tag = tag;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeNbt(this.tag);
    }

    public static AetherRankingsSyncPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        CompoundTag tag = buf.readNbt();
        return new AetherRankingsSyncPacket(playerID, tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            AetherRankings.get(serverPlayer).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
        }  else {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(this.playerID) instanceof Player player) {
                AetherRankings.get(player).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
            }
        }
    }
}
