package com.gildedgames.aether.item.miscellaneous.bucket;

import com.gildedgames.aether.item.AetherItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SkyrootMobBucketItem extends MobBucketItem {
    public SkyrootMobBucketItem(Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier, Item.Properties properties) {
        super(entitySupplier, fluidSupplier, soundSupplier, properties);
    }

    /**
     * Sets the bucket after usage to a Skyroot Bucket if it returns as a Bucket. Otherwise behavior is the same as {@link MobBucketItem}.
     * @param level The {@link Level} of the user.
     * @param player The {@link Player} using this item.
     * @param hand The {@link InteractionHand} in which the item is being used.
     * @return The super {@link InteractionResultHolder} or a {@link net.minecraft.world.InteractionResult#sidedSuccess(boolean)}.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);
        if (result.getObject().is(Items.BUCKET)) {
            result = InteractionResultHolder.sidedSuccess(new ItemStack(AetherItems.SKYROOT_BUCKET.get()), level.isClientSide());
        }
        return result;
    }

    /**
     * We don't initialize the Forge {@link net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper} for Skyroot Buckets.
     */
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag tag) {
        return null;
    }
}
