package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class AetherPlayerSyncPacket extends AbstractAetherPacket {
    private final int playerID;
    private final CompoundTag tag;

    public AetherPlayerSyncPacket(int playerID, CompoundTag tag) {
        this.playerID = playerID;
        this.tag = tag;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeNbt(this.tag);
    }

    public static AetherPlayerSyncPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        CompoundTag tag = buf.readNbt();
        return new AetherPlayerSyncPacket(playerID, tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().level.getEntity(this.playerID);
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> aetherPlayer.deserializeSynchableNBT(this.tag));
            }
        }
    }
}
