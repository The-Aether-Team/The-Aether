package com.aetherteam.aether.item.accessories.pendant;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IcePendantItem extends PendantItem implements FreezingAccessory {
    public IcePendantItem(Properties properties) {
        super("ice_pendant", AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_PENDANT, properties.repairable(AetherTags.Items.ICE_REPAIRING));
    }

    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        if (!entity.isInFluidType()) {
            this.freezeTick(stack, level, entity, hand);
        }
    }
}
