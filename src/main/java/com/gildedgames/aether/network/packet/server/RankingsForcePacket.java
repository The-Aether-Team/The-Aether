package com.gildedgames.aether.network.packet.server;

import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.gildedgames.aether.capability.rankings.AetherRankingsCapability;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record RankingsForcePacket(int playerID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
    }

    public static RankingsForcePacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        return new RankingsForcePacket(playerID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            AetherRankings.get(serverPlayer).ifPresent(aetherRankings -> {
                if (aetherRankings instanceof AetherRankingsCapability capability) {
                    capability.markForced(true);
                }
            });
        }
    }
}
