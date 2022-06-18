package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.rankings.AetherRankings;
import com.gildedgames.aether.core.capability.rankings.AetherRankingsCapability;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class RankingsForcePacket extends AbstractAetherPacket {
    private final int playerID;

    public RankingsForcePacket(int playerID) {
        this.playerID = playerID;
    }

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
