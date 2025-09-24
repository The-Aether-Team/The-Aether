package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceRingItem extends RingItem implements FreezingAccessory {
    public IceRingItem(Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_RING, properties.repairable(AetherTags.Items.ICE_REPAIRING));
    }

    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        if (!entity.isInFluidType()) {
            this.freezeTick(stack, level, entity, hand);
        }
    }
}
