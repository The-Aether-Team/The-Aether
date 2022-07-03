package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.entity.monster.Swet;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record SwetAttackPacket(int swetID, double xPos, double yPos, double zPos) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.swetID);
        buf.writeDouble(this.xPos);
        buf.writeDouble(this.yPos);
        buf.writeDouble(this.zPos);
    }

    public static SwetAttackPacket decode(FriendlyByteBuf buf) {
        int id = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new SwetAttackPacket(id, x, y, z);
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level.getEntity(this.swetID) instanceof Swet swet) {
            swet.absMoveTo(xPos, yPos, zPos);
        }
    }
}
