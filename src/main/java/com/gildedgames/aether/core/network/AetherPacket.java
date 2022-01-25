package com.gildedgames.aether.core.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface AetherPacket
{
    void encode(FriendlyByteBuf buf);

    boolean handle(Supplier<NetworkEvent.Context> context);

    class BaseAetherPacket implements AetherPacket
    {
        @Override
        public void encode(FriendlyByteBuf buf) { }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> execute(context.get().getSender()));
            return true;
        }

        public void execute(Player player) { }
    }
}
