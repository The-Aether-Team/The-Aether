package com.gildedgames.aether.core.network.packet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import static com.gildedgames.aether.core.network.IAetherPacket.*;

public class SetPositionPacket extends AetherPacket
{
    private final int entityID;
    private final double x, y, z;

    public SetPositionPacket(int entityID, double x, double y, double z) {
        this.entityID = entityID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    public static SetPositionPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new SetPositionPacket(entityID, x, y, z);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            if (entity != null) {
                entity.setPos(this.x, this.y, this.z);
            }
        }
    }
}
