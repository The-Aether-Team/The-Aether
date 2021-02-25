package com.gildedgames.aether.network.packet;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;


public class ExtendedAttackPacket {
    private int targetEntityID;
    public ExtendedAttackPacket(int target) {
        this.targetEntityID = target;
    }

    public void encode(PacketBuffer buf) {
        buf.writeVarInt(targetEntityID);
    }

    public static ExtendedAttackPacket decode(PacketBuffer buf) {
        return new ExtendedAttackPacket(buf.readVarInt());
    }

    public void handlePacket(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() != NetworkDirection.PLAY_TO_SERVER) {
            return;
        }
        context.get().enqueueWork(() -> {
            ServerPlayerEntity player = context.get().getSender();
            Entity target = player.world.getEntityByID(targetEntityID);
            if(target != null) {
                double reach = player.getAttributeValue(ForgeMod.REACH_DISTANCE.get());
                if(player.getDistanceSq(target) < reach * reach) {
                    player.attackTargetEntityWithCurrentItem(target);
                }
            }
        });
        context.get().setPacketHandled(true);
    }
}
