package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record QueenDialoguePacket(int queenID) implements CustomPacketPayload {
    public static final Type<QueenDialoguePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "open_valkyrie_queen_dialogue"));

    public static final StreamCodec<RegistryFriendlyByteBuf, QueenDialoguePacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        QueenDialoguePacket::queenID,
        QueenDialoguePacket::new);

    @Override
    public Type<QueenDialoguePacket> type() {
        return TYPE;
    }

    public static void execute(QueenDialoguePacket payload, IPayloadContext context) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(payload.queenID()) instanceof ValkyrieQueen valkyrieQueen) {
                valkyrieQueen.openDialogueScreen();
            }
        }
    }
}
