package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.passive.AerbunnyEntity;
import com.gildedgames.aether.core.network.IAetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public class RemountAerbunnyPacket extends IAetherPacket.AetherPacket
{
    private final int entityID;
    private final int aerbunnyID;

    public RemountAerbunnyPacket(int entityID, int aerbunnyID) {
        this.entityID = entityID;
        this.aerbunnyID = aerbunnyID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.aerbunnyID);
    }

    public static RemountAerbunnyPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int aerbunnyID = buf.readInt();
        return new RemountAerbunnyPacket(entityID, aerbunnyID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            Level world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            Entity entity1 = world.getEntity(this.aerbunnyID);
            if (entity instanceof Player && entity1 instanceof AerbunnyEntity) {
                AerbunnyEntity aerbunny = (AerbunnyEntity) entity1;
                aerbunny.startRiding(entity);
            }
        }
    }
}
