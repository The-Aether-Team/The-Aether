package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.capability.rankings.AetherRankings;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import static com.gildedgames.aether.core.network.AetherPacket.*;

public class AetherRankingsClientSyncPacket extends AbstractAetherPacket {
    private final int playerID;
    private final CompoundTag tag;

    public AetherRankingsClientSyncPacket(int playerID, CompoundTag tag) {
        this.playerID = playerID;
        this.tag = tag;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeNbt(this.tag);
    }

    public static AetherRankingsClientSyncPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        CompoundTag tag = buf.readNbt();
        return new AetherRankingsClientSyncPacket(playerID, tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(this.playerID) instanceof Player player) {
            AetherRankings.get(player).ifPresent(aetherRankings -> aetherRankings.deserializeSynchableNBT(this.tag));
        }
    }
}