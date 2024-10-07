package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.NpcDialogue;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * This packet is sent to the server whenever the player chooses an important action in the NPC dialogue.
 */
public record NpcPlayerInteractPacket(int entityID, byte interactionID) implements CustomPacketPayload {
    public static final Type<NpcPlayerInteractPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "set_npc_interaction_action"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NpcPlayerInteractPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        NpcPlayerInteractPacket::entityID,
        ByteBufCodecs.BYTE,
        NpcPlayerInteractPacket::interactionID,
        NpcPlayerInteractPacket::new);

    @Override
    public Type<NpcPlayerInteractPacket> type() {
        return TYPE;
    }

    public static void execute(NpcPlayerInteractPacket payload, IPayloadContext context) {
        Player playerEntity = context.player();
        if (playerEntity.getServer() != null && playerEntity.level().getEntity(payload.entityID()) instanceof NpcDialogue npc) {
            npc.handleNpcInteraction(playerEntity, payload.interactionID());
        }
    }
}
