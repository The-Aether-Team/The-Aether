package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.ConsumableItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SkyrootPoisonBucketItem extends Item implements ConsumableItem {
    public SkyrootPoisonBucketItem(Properties properties) {
        super(properties);
    }

    /**
     * Inflicts Inebriation for 500 ticks and consumes the item using {@link ConsumableItem#consume(Item, ItemStack, LivingEntity)}.
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user The {@link LivingEntity} using the stack.
     * @return The {@link ItemStack} after using, which is a Skyroot Bucket if successful.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!level.isClientSide()) {
            user.addEffect(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 500, 0));
        }
        this.consume(this, stack, user);
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
