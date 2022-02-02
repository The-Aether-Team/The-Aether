package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.monster.Swet;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SwetAttackPacket extends AetherPacket {
    private final int swetID;
    private final double xPos;
    private final double yPos;
    private final double zPos;

    public SwetAttackPacket(int id, double x, double y, double z) {
        this.swetID = id;
        this.xPos = x;
        this.yPos = y;
        this.zPos = z;
    }

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
