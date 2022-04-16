package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class AetherPlayerSyncPacket extends AbstractAetherPacket {
    private final CompoundTag tag;

    public AetherPlayerSyncPacket(CompoundTag tag) {
        this.tag = tag;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(this.tag);
    }

    public static AetherPlayerSyncPacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        return new AetherPlayerSyncPacket(tag);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            AetherPlayer.get(Minecraft.getInstance().player).ifPresent(aetherPlayer -> aetherPlayer.deserializeSynchableNBT(this.tag));
        }
    }
}
