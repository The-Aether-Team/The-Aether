package com.gildedgames.aether.network.packet.client;

import com.gildedgames.aether.entity.NpcDialogue;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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
    public boolean handle(Supplier<NetworkEvent.Context> context) {
        Minecraft minecraft = Minecraft.getInstance();
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (minecraft.level.getEntity(entityID) instanceof NpcDialogue npc) {
                npc.openDialogueScreen();
            }
        }
        return true;
    }
}
