package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Used to move the player on the client when they are hit by a ZephyrSnowBallEntity on the server.
 */
public record ZephyrSnowballHitPacket(int entityID, double xSpeed, double zSpeed) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zephyr_snowball_knockback_player");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeDouble(this.xSpeed());
        buf.writeDouble(this.zSpeed());
    }

    public static ZephyrSnowballHitPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        double x = buf.readDouble();
        double z = buf.readDouble();
        return new ZephyrSnowballHitPacket(entityID, x, z);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(this.entityID()) instanceof LocalPlayer localPlayer) {
            if (!localPlayer.isBlocking()) {
                localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().x(), localPlayer.getDeltaMovement().y() + 0.5, localPlayer.getDeltaMovement().z());
            }
            localPlayer.setDeltaMovement(localPlayer.getDeltaMovement().x() + (this.xSpeed() * 1.5F), localPlayer.getDeltaMovement().y(), localPlayer.getDeltaMovement().z() + (this.zSpeed() * 1.5F));
        }
    }
}
