package com.aetherteam.aether.network.packet.clientbound;

import com.aetherteam.aether.Aether;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Sets a player's invisibility on the client side from the server side.
 */
public record SetInvisibilityPacket(int playerID, boolean invisible) implements BasePacket {
    public static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "set_invisibility_status");

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
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
