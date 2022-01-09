package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;

public class SetVehiclePacket extends AetherPacket
{
    private final int passengerID;
    private final int vehicleID;

    public SetVehiclePacket(int passenger, int vehicle) {
        this.passengerID = passenger;
        this.vehicleID = vehicle;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.passengerID);
        buf.writeInt(this.vehicleID);
    }

    public static SetVehiclePacket decode(FriendlyByteBuf buf) {
        int passenger = buf.readInt();
        int vehicle = buf.readInt();
        return new SetVehiclePacket(passenger, vehicle);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity passenger = Minecraft.getInstance().player.level.getEntity(this.passengerID);
            Entity vehicle = Minecraft.getInstance().player.level.getEntity(this.vehicleID);
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle);
            }
        }
    }
}
