package com.gildedgames.aether.item.food;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.capability.player.AetherPlayer;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class WhiteAppleItem extends Item
{
    public WhiteAppleItem() {
        super(new Item.Properties().food(AetherFoods.WHITE_APPLE).tab(AetherItemGroups.AETHER_FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) entityLiving.curePotionEffects(new ItemStack(AetherItems.WHITE_APPLE.get()));
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }
        if (entityLiving instanceof Player player) {
            AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                if (!player.level.isClientSide()) {
                    aetherPlayer.setRemedyMaximum(300);
                    aetherPlayer.setRemedyTimer(300);
                }
            });
        }
        return super.finishUsingItem(stack, worldIn, entityLiving);
    }
}
