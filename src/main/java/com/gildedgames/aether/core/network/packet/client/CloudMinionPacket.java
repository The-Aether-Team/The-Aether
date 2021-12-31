package com.gildedgames.aether.core.network.packet.client;

import com.gildedgames.aether.common.entity.miscellaneous.CloudMinionEntity;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.gildedgames.aether.core.network.IAetherPacket.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

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
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeInt(this.rightCloudMinionID);
        buf.writeInt(this.leftCloudMinionID);
    }

    public static CloudMinionPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        int rightCloudMinionID = buf.readInt();
        int leftCloudMinionID = buf.readInt();
        return new CloudMinionPacket(entityID, rightCloudMinionID, leftCloudMinionID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level;
            if (world.getEntity(this.entityID) instanceof Player player && world.getEntity(this.rightCloudMinionID) instanceof CloudMinionEntity cloudMinionRight && world.getEntity(this.leftCloudMinionID) instanceof CloudMinionEntity cloudMinionLeft) {
                IAetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.getCloudMinions().isEmpty()) {
                        aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                    }
                });
            }
        }
    }
}
