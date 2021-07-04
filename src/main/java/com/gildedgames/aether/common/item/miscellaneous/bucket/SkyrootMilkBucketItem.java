package com.gildedgames.aether.common.item.miscellaneous.bucket;

import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class SkyrootMilkBucketItem extends MilkBucketItem
{
    public SkyrootMilkBucketItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.instabuild) {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : stack;
    }
}
