package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Stores Cloud Minions to {@link AetherPlayerAttachment} when summoned.
 */
public record CloudMinionPacket(int entityID, int rightCloudMinionID, int leftCloudMinionID) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "add_cloud_minions");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID());
        buf.writeInt(this.rightCloudMinionID());
        buf.writeInt(this.leftCloudMinionID());
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
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(this.entityID()) instanceof Player player && level.getEntity(this.rightCloudMinionID()) instanceof CloudMinion cloudMinionRight && level.getEntity(this.leftCloudMinionID()) instanceof CloudMinion cloudMinionLeft) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                if (data.getCloudMinions().isEmpty()) {
                    data.setCloudMinions(player, cloudMinionRight, cloudMinionLeft);
                }
            }
        }
    }
}
