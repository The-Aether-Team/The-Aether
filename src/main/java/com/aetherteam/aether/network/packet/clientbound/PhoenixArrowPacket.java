package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.capability.arrow.PhoenixArrow;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.network.FriendlyByteBuf;

public record PhoenixArrowPacket(int entityID, boolean isPhoenix) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeBoolean(this.isPhoenix);
    }

    public static PhoenixArrowPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        boolean isPhoenix = buf.readBoolean();
        return new PhoenixArrowPacket(entityID, isPhoenix);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.entityID) instanceof AbstractArrow arrow) {
            PhoenixArrow.get(arrow).ifPresent(phoenixArrow -> phoenixArrow.setPhoenixArrow(this.isPhoenix));
        }
    }
}
