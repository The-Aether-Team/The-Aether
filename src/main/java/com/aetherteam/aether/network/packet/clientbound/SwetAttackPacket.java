package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record SwetAttackPacket(int entityID, double xPos, double yPos, double zPos) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeDouble(this.xPos);
        buf.writeDouble(this.yPos);
        buf.writeDouble(this.zPos);
    }

    public static SwetAttackPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        double xPos = buf.readDouble();
        double yPos = buf.readDouble();
        double zPos = buf.readDouble();
        return new SwetAttackPacket(entityID, xPos, yPos, zPos);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.entityID) instanceof Swet swet) {
            swet.absMoveTo(this.xPos, this.yPos, this.zPos);
        }
    }
}
