package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.event.hooks.DimensionHooks;
import com.gildedgames.aether.network.AetherPacket.AbstractAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class LeavingAetherPacket extends AbstractAetherPacket {
    private final boolean playerLeavingAether;

    public LeavingAetherPacket(boolean playerLeavingAether) {
        this.playerLeavingAether = playerLeavingAether;
    }

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
