package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Swings the player's hand when feeding a Moa.
 */
public record MoaInteractPacket(int playerID, boolean mainHand) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID());
        buf.writeBoolean(this.mainHand());
    }

    public static MoaInteractPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        boolean rightHand = buf.readBoolean();
        return new MoaInteractPacket(playerID, rightHand);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(this.playerID());
            if (entity instanceof Player player) {
                player.swing(this.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
            }
        }
    }
}
