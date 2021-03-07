package com.gildedgames.aether.common.item.misc;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherPotionEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SkyrootPoisonBucketItem extends Item {
    public SkyrootPoisonBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote) entityLiving.addPotionEffect(new EffectInstance(AetherPotionEffects.INEBRIATION.get(), 500, 0, false, false));
        if (entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        return DrinkHelper.startDrinking(worldIn, playerIn, handIn);
    }
}
