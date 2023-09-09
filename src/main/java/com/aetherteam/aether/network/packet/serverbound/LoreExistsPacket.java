package com.aetherteam.aether.network.packet.serverbound;

import com.aetherteam.aether.inventory.menu.LoreBookMenu;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Communicates whether a language entry for lore exists from the client to the server.
 */
public record LoreExistsPacket(int playerID, ItemStack itemStack, boolean exists) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID());
        buf.writeItem(this.itemStack());
        buf.writeBoolean(this.exists());
    }

    public static LoreExistsPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        ItemStack itemStack = buf.readItem();
        boolean exists = buf.readBoolean();
        return new LoreExistsPacket(playerID, itemStack, exists);
    }

    @Override
    public void execute(@Nullable Player player) {
        if (player != null && player.getServer() != null && player.level().getEntity(this.playerID()) instanceof ServerPlayer && player.containerMenu instanceof LoreBookMenu menu) {
            menu.setLoreEntryExists(this.exists());
        }
    }
}
