package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;

public class SetVehiclePacket extends IAetherPacket.AetherPacket {
    private int passengerID;
    private int vehicleID;

    public SetVehiclePacket(int passenger, int vehicle) {
        this.passengerID = passenger;
        this.vehicleID = vehicle;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeVarInt(passengerID);
        buf.writeVarInt(vehicleID);
    }

    public static SetVehiclePacket decode(PacketBuffer buf) {
        return new SetVehiclePacket(buf.readVarInt(), buf.readVarInt());
    }

    @Override
    public void execute(PlayerEntity player) {
        ClientPlayerEntity clientPlayer = Minecraft.getInstance().player;
        if(clientPlayer != null && clientPlayer.level != null) {
            Entity passenger = clientPlayer.level.getEntity(passengerID);
            Entity vehicle = clientPlayer.level.getEntity(vehicleID);
            if (passenger != null && vehicle != null)
                passenger.startRiding(vehicle);
        }
    }
}
