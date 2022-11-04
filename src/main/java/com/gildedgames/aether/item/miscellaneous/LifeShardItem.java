package com.gildedgames.aether.item.miscellaneous;

import com.gildedgames.aether.capability.player.AetherPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class LifeShardItem extends Item implements ConsumableItem {
    public LifeShardItem(Properties properties) {
        super(properties);
    }

    /**
     * Consumes the Life Shard as long as the currently used Life Shard count is below the limit, otherwise a message will be displayed to let the player know they can't consume any more.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return A success on client and consume on server (based on {@link InteractionResult#sidedSuccess(boolean)}) if the Life Shard can be consumed, otherwise pass is returned.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (!player.isCreative()) {
            Optional<AetherPlayer> aetherPlayerOptional = AetherPlayer.get(player).resolve();
            if (aetherPlayerOptional.isPresent()) {
                AetherPlayer aetherPlayer = aetherPlayerOptional.get();
                if (aetherPlayer.getLifeShardCount() < aetherPlayer.getLifeShardLimit()) {
                    player.swing(hand);
                    if (!level.isClientSide()) {
                        this.consume(this, heldStack, player);
                        aetherPlayer.addToLifeShardCount(1);
                        return InteractionResultHolder.consume(heldStack);
                    } else {
                        return InteractionResultHolder.success(heldStack);
                    }
                } else if (aetherPlayer.getLifeShardCount() >= aetherPlayer.getLifeShardLimit()) {
                    player.displayClientMessage(Component.translatable("aether.life_shard_limit", aetherPlayer.getLifeShardLimit()), true);
                }
            }
        }
        return InteractionResultHolder.pass(heldStack);
    }
}
