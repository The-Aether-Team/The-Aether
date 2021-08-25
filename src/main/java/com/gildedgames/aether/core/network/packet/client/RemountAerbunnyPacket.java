package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class RemountAerbunnyPacket extends IAetherPacket.AetherPacket
{
    private final int entityID;
    private final int aerbunnyID;

    public RemountAerbunnyPacket(int entityID, int aerbunnyID) {
        this.entityID = entityID;
        this.aerbunnyID = aerbunnyID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.aerbunnyID);
    }

    public static RemountAerbunnyPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        int aerbunnyID = buf.readInt();
        return new RemountAerbunnyPacket(entityID, aerbunnyID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            Entity entity1 = world.getEntity(this.aerbunnyID);
            if (entity instanceof PlayerEntity && entity1 instanceof AerbunnyEntity) {
                AerbunnyEntity aerbunny = (AerbunnyEntity) entity1;
                aerbunny.startRiding(entity);
            }
        }
    }
}
