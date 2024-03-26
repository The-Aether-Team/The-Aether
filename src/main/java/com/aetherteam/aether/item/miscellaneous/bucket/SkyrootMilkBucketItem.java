package com.aetherteam.aether.item.miscellaneous.bucket;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.miscellaneous.ConsumableItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCures;

public class SkyrootMilkBucketItem extends MilkBucketItem implements ConsumableItem {
    public SkyrootMilkBucketItem(Properties properties) {
        super(properties);
    }

    /**
     * Acts like a vanilla Milk Bucket and consumes the item using {@link ConsumableItem#consume(Item, ItemStack, LivingEntity)}.
     *
     * @param stack The {@link ItemStack} in use.
     * @param level The {@link Level} of the user.
     * @param user  The {@link LivingEntity} using the stack.
     * @return The {@link ItemStack} after using, which is a Skyroot Bucket if successful.
     */
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!level.isClientSide()) {
            user.removeEffectsCuredBy(EffectCures.MILK);
        }
        this.consume(this, stack, user);
        return stack.isEmpty() ? new ItemStack(AetherItems.SKYROOT_BUCKET.get()) : stack;
    }
}
