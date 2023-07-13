package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.entity.NpcDialogue;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

/**
 * This packet is sent to the server whenever the player chooses an important action in the NPC dialogue.
 */
public record NpcPlayerInteractPacket(int entityID, byte interactionID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
        buf.writeByte(this.interactionID);
    }

    public static NpcPlayerInteractPacket decode(FriendlyByteBuf buf) {
        return new NpcPlayerInteractPacket(buf.readInt(), buf.readByte());
    }

    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.entityID) instanceof NpcDialogue npc) {
            npc.handleNpcInteraction(playerEntity, this.interactionID);
        }
    }
}
