package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.entity.NpcDialogue;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * Packet sent to the client to initiate an NPC dialogue.
 */
public record OpenNpcDialoguePacket(int entityID) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static OpenNpcDialoguePacket decode(FriendlyByteBuf buf) {
        return new OpenNpcDialoguePacket(buf.readInt());
    }

    @Override
    public void execute(Player player) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(entityID) instanceof NpcDialogue npc) {
                npc.openDialogueScreen();
            }
        }
    }
}
