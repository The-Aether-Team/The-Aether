package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class CloudMinionPacket extends AetherPacket
{
    private final int entityID;
    private final int rightCloudMinionID, leftCloudMinionID;

    public CloudMinionPacket(int entityID, int rightCloudMinionID, int leftCloudMinionID) {
        this.entityID = entityID;
        this.rightCloudMinionID = rightCloudMinionID;
        this.leftCloudMinionID = leftCloudMinionID;
    }

    @Override
    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.rightCloudMinionID);
        buf.writeInt(this.leftCloudMinionID);
    }

    public static CloudMinionPacket decode(PacketBuffer buf) {
        int entityID = buf.readInt();
        int rightCloudMinionID = buf.readInt();
        int leftCloudMinionID = buf.readInt();
        return new CloudMinionPacket(entityID, rightCloudMinionID, leftCloudMinionID);
    }

    @Override
    public void execute(PlayerEntity playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null) {
            World world = Minecraft.getInstance().player.level;
            Entity entity = world.getEntity(this.entityID);
            Entity cloudMinionRight = world.getEntity(this.rightCloudMinionID);
            Entity cloudMinionLeft = world.getEntity(this.leftCloudMinionID);
            if (entity instanceof PlayerEntity && cloudMinionRight instanceof CloudMinionEntity && cloudMinionLeft instanceof CloudMinionEntity) {
                IAetherPlayer.get((PlayerEntity) entity).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.getCloudMinionEntities().isEmpty()) {
                        aetherPlayer.setCloudMinions((CloudMinionEntity) cloudMinionRight, (CloudMinionEntity) cloudMinionLeft);
                    }
                });
            }
        }
    }
}
