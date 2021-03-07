package com.gildedgames.aether.core.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IAetherPacket
{
    void encode(PacketBuffer buf);

    boolean handle(Supplier<NetworkEvent.Context> context);

    class AetherPacket implements IAetherPacket
    {
        @Override
        public void encode(PacketBuffer buf) {

        }

        @Override
        public boolean handle(Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> execute(context.get().getSender()));
            return true;
        }

        public void execute(PlayerEntity player) {

        }
    }
}
