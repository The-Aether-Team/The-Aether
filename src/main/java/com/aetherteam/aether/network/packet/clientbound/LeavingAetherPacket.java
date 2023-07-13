package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.event.hooks.DimensionHooks;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record LeavingAetherPacket(boolean playerLeavingAether) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(this.playerLeavingAether);
    }

    public static LeavingAetherPacket decode(FriendlyByteBuf buf) {
        boolean playerLeavingAether = buf.readBoolean();
        return new LeavingAetherPacket(playerLeavingAether);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            DimensionHooks.playerLeavingAether = this.playerLeavingAether;
        }
    }
}
