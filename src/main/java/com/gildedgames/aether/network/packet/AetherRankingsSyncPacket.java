package com.gildedgames.aether.network.packet;

import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record AetherRankingsSyncPacket(int playerID, CompoundTag tag) implements AetherPacket {
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
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer && this.tag != null) {
            AetherRankings.get(serverPlayer).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
        } else {
            if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(this.playerID) instanceof Player player && this.tag != null) {
                AetherRankings.get(player).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
            }
        }
    }
}
