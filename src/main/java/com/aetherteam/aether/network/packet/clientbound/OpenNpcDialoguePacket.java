package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.NpcDialogue;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * Packet sent to the client to initiate an NPC dialogue.
 */
public record OpenNpcDialoguePacket(int entityID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static OpenNpcDialoguePacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new OpenNpcDialoguePacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.entityID) instanceof NpcDialogue npc) {
                npc.openDialogueScreen();
            }
        }
    }
}
