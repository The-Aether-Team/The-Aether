package com.aetherteam.aether.network.packet.client;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;

public record CloudMinionPacket(int entityID, int rightCloudMinionID, int leftCloudMinionID) implements AetherPacket {
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
            if (world.getEntity(this.entityID) instanceof Player player && world.getEntity(this.rightCloudMinionID) instanceof CloudMinion cloudMinionRight && world.getEntity(this.leftCloudMinionID) instanceof CloudMinion cloudMinionLeft) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.getCloudMinions().isEmpty()) {
                        aetherPlayer.setCloudMinions(cloudMinionRight, cloudMinionLeft);
                    }
                });
            }
        }
    }
}
