package com.gildedgames.aether.common.item.miscellaneous.bucket;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class SkyrootPoisonBucketItem extends Item
{
    public SkyrootPoisonBucketItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide) entityLiving.addEffect(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 500, 0, false, false));
        if (entityLiving instanceof Player && !((Player) entityLiving).getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack.isEmpty() ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        return ItemUtils.startUsingInstantly(worldIn, playerIn, handIn);
    }
}
