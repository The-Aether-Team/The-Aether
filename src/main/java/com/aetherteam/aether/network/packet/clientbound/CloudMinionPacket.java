package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.entity.miscellaneous.CloudMinion;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Stores Cloud Minions to {@link AetherPlayerAttachment} when summoned.
 */
public record CloudMinionPacket(int entityID, int rightCloudMinionID, int leftCloudMinionID) implements CustomPacketPayload {
    public static final Type<CloudMinionPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "add_cloud_minions"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CloudMinionPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        CloudMinionPacket::entityID,
        ByteBufCodecs.INT,
        CloudMinionPacket::rightCloudMinionID,
        ByteBufCodecs.INT,
        CloudMinionPacket::leftCloudMinionID,
        CloudMinionPacket::new);

    @Override
    public Type<CloudMinionPacket> type() {
        return TYPE;
    }

    public static void execute(CloudMinionPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level level = Minecraft.getInstance().player.level();
            if (level.getEntity(payload.entityID()) instanceof Player player && level.getEntity(payload.rightCloudMinionID()) instanceof CloudMinion cloudMinionRight && level.getEntity(payload.leftCloudMinionID()) instanceof CloudMinion cloudMinionLeft) {
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                if (data.getCloudMinions().isEmpty()) {
                    data.setCloudMinions(player, cloudMinionRight, cloudMinionLeft);
                }
            }
        }
    }
}
