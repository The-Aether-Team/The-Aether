package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Sets a passenger to ride a vehicle. Called from {@link com.aetherteam.aether.event.hooks.DimensionHooks#entityFell}.
 */
public record SetVehiclePacket(int passengerID, int vehicleID) implements CustomPacketPayload {
    public static final Type<SetVehiclePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "set_mount"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetVehiclePacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        SetVehiclePacket::passengerID,
        ByteBufCodecs.INT,
        SetVehiclePacket::vehicleID,
        SetVehiclePacket::new);

    @Override
    public Type<SetVehiclePacket> type() {
        return TYPE;
    }

    public static void execute(SetVehiclePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity passenger = Minecraft.getInstance().player.level().getEntity(payload.passengerID());
            Entity vehicle = Minecraft.getInstance().player.level().getEntity(payload.vehicleID());
            if (passenger != null && vehicle != null) {
                passenger.startRiding(vehicle);
            }
        }
    }
}
