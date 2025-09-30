package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record PortalInteractPacket(int playerID, boolean mainHand) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID());
        buf.writeBoolean(this.mainHand());
    }

    public static PortalInteractPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        boolean mainHand = buf.readBoolean();
        return new PortalInteractPacket(playerID, mainHand);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Entity entity = Minecraft.getInstance().player.level().getEntity(this.playerID());
            if (entity instanceof Player player) {
                player.swing(this.mainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
                player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
            }
        }
    }
}
