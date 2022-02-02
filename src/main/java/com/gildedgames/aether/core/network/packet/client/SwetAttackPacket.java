package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.monster.Swet;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class SwetAttackPacket extends AetherPacket {
    private int swetID;
    private double xSpeed;
    private double ySpeed;
    private double zSpeed;

    public SwetAttackPacket(int id, double x, double y, double z) {
        this.swetID = id;
        this.xSpeed = x;
        this.ySpeed = y;
        this.zSpeed = z;
    }

    public SwetAttackPacket(int id, Vec3 delta) {
        this.swetID = id;
        this.xSpeed = delta.x;
        this.ySpeed = delta.y;
        this.zSpeed = delta.z;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.swetID);
        buf.writeDouble(this.xSpeed);
        buf.writeDouble(this.ySpeed);
        buf.writeDouble(this.zSpeed);
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
            swet.setDeltaMovement(swet.getDeltaMovement().x + (this.xSpeed * 1.5F), swet.getDeltaMovement().y, swet.getDeltaMovement().z + (this.zSpeed * 1.5F));
        }
    }
}
