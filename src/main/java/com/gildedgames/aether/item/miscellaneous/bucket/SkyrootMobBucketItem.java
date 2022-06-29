package com.gildedgames.aether.item.miscellaneous.bucket;

import com.gildedgames.aether.item.AetherItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SkyrootMobBucketItem extends MobBucketItem {
    public SkyrootMobBucketItem(Supplier<? extends EntityType<?>> entitySupplier, Supplier<? extends Fluid> fluidSupplier, Supplier<? extends SoundEvent> soundSupplier, Item.Properties properties) {
        super(entitySupplier, fluidSupplier, soundSupplier, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        InteractionResultHolder<ItemStack> result = super.use(pLevel, pPlayer, pHand);
        if (result.getObject().is(Items.BUCKET)) {
            result = InteractionResultHolder.sidedSuccess(new ItemStack(AetherItems.SKYROOT_BUCKET.get()), pLevel.isClientSide);
        }
        return result;
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundTag nbt) {
        return null;
    }
}
