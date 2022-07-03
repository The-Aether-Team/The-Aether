package com.gildedgames.aether.network.packet.server;

import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.network.AetherPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;

public record MilkCowPacket(int playerID, ItemStack stack, boolean isMainHand) implements AetherPacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.playerID);
        buf.writeItem(this.stack);
        buf.writeBoolean(this.isMainHand);
    }

    public static MilkCowPacket decode(FriendlyByteBuf buf) {
        int playerID = buf.readInt();
        ItemStack stack = buf.readItem();
        boolean isMainHand = buf.readBoolean();
        return new MilkCowPacket(playerID, stack, isMainHand);
    }

    @Override
    public void execute(Player playerEntity) {
        if (playerEntity != null && playerEntity.getServer() != null && playerEntity.level.getEntity(this.playerID) instanceof ServerPlayer serverPlayer) {
            InteractionHand hand = this.isMainHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
            ItemStack filledBucket = ItemUtils.createFilledResult(this.stack, serverPlayer, AetherItems.SKYROOT_MILK_BUCKET.get().getDefaultInstance());
            serverPlayer.setItemInHand(hand, filledBucket);
        }
    }
}
