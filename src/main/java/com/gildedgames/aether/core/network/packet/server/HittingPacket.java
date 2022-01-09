package com.gildedgames.aether.core.network.packet.server;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;

public class HittingPacket extends AetherPacket
{
    private final int playerID;
    private final boolean isHitting;

    public HittingPacket(int playerID, boolean isHitting) {
        this.playerID = playerID;
        this.isHitting = isHitting;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeBoolean(this.isHitting);
    }

    public static HittingPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        boolean hitting = buf.readBoolean();
        return new HittingPacket(playerID, hitting);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            IAetherPlayer.get(serverPlayer).ifPresent(aetherPlayer -> aetherPlayer.setHitting(this.isHitting));
        }
    }
}
