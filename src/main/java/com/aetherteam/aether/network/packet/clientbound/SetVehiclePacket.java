package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Sets a passenger to ride a vehicle. Called from {@link com.aetherteam.aether.event.hooks.DimensionHooks#entityFell}.
 */
public record SetVehiclePacket(int passengerID, int vehicleID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.passengerID());
        buf.writeInt(this.vehicleID());
    }

    public static SetVehiclePacket decode(FriendlyByteBuf buf) {
        int passengerID = buf.readInt();
        int vehicleID = buf.readInt();
        return new SetVehiclePacket(passengerID, vehicleID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity passenger = Minecraft.getInstance().player.getLevel().getEntity(this.passengerID());
            Entity vehicle = Minecraft.getInstance().player.getLevel().getEntity(this.vehicleID());
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle);
            }
        }
    }
}
