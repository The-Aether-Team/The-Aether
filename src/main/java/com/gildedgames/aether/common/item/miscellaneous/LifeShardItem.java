package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

public class LifeShardItem extends Item
{
    public LifeShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!playerEntity.isCreative()) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get(playerEntity).orElseThrow(() -> new IllegalStateException("Player " + playerEntity.getName().getContents() + " has no AetherPlayer capability!"));
            if (aetherPlayer.getLifeShardCount() < aetherPlayer.getLifeShardLimit()) {
                playerEntity.swing(hand);
                if (!world.isClientSide && !playerEntity.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                aetherPlayer.addToLifeShardCount(1);
                return InteractionResultHolder.success(itemstack);
            } else if (aetherPlayer.getLifeShardCount() >= aetherPlayer.getLifeShardLimit()) {
                playerEntity.displayClientMessage(new TranslatableComponent("aether.life_shard_limit", aetherPlayer.getLifeShardLimit()), true);
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
