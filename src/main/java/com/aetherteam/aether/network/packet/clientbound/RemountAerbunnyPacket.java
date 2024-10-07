package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.entity.passive.Aerbunny;
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
 * Mounts an Aerbunny to the player using stored NBT data if the player previously logged out with a mounted Aerbunny. This is called by {@link AetherPlayerAttachment#remountAerbunny(Player)}.<br><br>
 * This also stores the summoned Aerbunny back into the capability so the player is tracked as having a mounted Aerbunny.
 */
public record RemountAerbunnyPacket(int vehicleID, int aerbunnyID) implements CustomPacketPayload {
    public static final Type<RemountAerbunnyPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "remount_aerbunny"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RemountAerbunnyPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        RemountAerbunnyPacket::vehicleID,
        ByteBufCodecs.INT,
        RemountAerbunnyPacket::aerbunnyID,
        RemountAerbunnyPacket::new);

    @Override
    public Type<RemountAerbunnyPacket> type() {
        return TYPE;
    }

    public static void execute(RemountAerbunnyPacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Level world = Minecraft.getInstance().player.level();
            if (world.getEntity(payload.vehicleID()) instanceof Player player && world.getEntity(payload.aerbunnyID()) instanceof Aerbunny aerbunny) {
                aerbunny.startRiding(player);
                player.getData(AetherDataAttachments.AETHER_PLAYER).setMountedAerbunny(aerbunny);
            }
        }
    }
}
