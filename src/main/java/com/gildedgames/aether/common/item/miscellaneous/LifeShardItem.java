package com.gildedgames.aether.common.item.miscellaneous;

import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class LifeShardItem extends Item
{
    public LifeShardItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);
        if (!playerEntity.isCreative()) {
            IAetherPlayer aetherPlayer = IAetherPlayer.get(playerEntity).orElseThrow(() -> new IllegalStateException("Player " + playerEntity.getName().getContents() + " has no AetherPlayer capability!"));
            if (aetherPlayer.getLifeShardCount() < aetherPlayer.getLifeShardLimit()) {
                playerEntity.swing(hand);
                if (!world.isClientSide) {
                    if (!playerEntity.abilities.instabuild) {
                        itemstack.shrink(1);
                    }
                    aetherPlayer.addToLifeShardCount(1);
                    return ActionResult.sidedSuccess(itemstack, world.isClientSide());
                }
            } else if (aetherPlayer.getLifeShardCount() >= aetherPlayer.getLifeShardLimit()) {
                playerEntity.displayClientMessage(new TranslationTextComponent("aether.life_shard_limit", aetherPlayer.getLifeShardLimit()), true);
            }
        }
        return ActionResult.pass(itemstack);
    }
}
