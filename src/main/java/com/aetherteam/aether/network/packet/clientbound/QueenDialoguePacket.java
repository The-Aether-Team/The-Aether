package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.dungeon.boss.ValkyrieQueen;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public record QueenDialoguePacket(int queenID) implements BasePacket {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "open_valkyrie_queen_dialogue");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
