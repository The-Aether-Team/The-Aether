package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record QueenDialoguePacket(int queenID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.queenID());
    }

    public static QueenDialoguePacket decode(FriendlyByteBuf buf) {
        int queenID = buf.readInt();
        return new QueenDialoguePacket(queenID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.queenID()) instanceof ValkyrieQueen valkyrieQueen) {
                valkyrieQueen.openDialogueScreen();
            }
        }
    }
}