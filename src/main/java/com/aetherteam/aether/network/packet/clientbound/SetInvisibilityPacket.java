package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public record SetInvisibilityPacket(int playerID, boolean invisible) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID());
        buf.writeBoolean(this.invisible());
    }

    public static SetInvisibilityPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        boolean invisible = buf.readBoolean();
        return new SetInvisibilityPacket(playerID, invisible);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            if (Minecraft.getInstance().level.getEntity(this.playerID()) instanceof Player player) {
                player.setInvisible(this.invisible());
            }
        }
    }
}
