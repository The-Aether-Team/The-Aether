package com.gildedgames.aether.item.miscellaneous.bucket;

import com.gildedgames.aether.item.AetherItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;

public class SkyrootMilkBucketItem extends MilkBucketItem
{
    public SkyrootMilkBucketItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        if (entityLiving instanceof ServerPlayer) {
            ServerPlayer serverplayerentity = (ServerPlayer) entityLiving;
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayerentity, stack);
            serverplayerentity.awardStat(Stats.ITEM_USED.get(this));
        }
        if (entityLiving instanceof Player && !((Player)entityLiving).getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : stack;
    }
}
