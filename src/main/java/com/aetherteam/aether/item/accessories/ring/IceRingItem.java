package com.aetherteam.aether.item.accessories.ring;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.item.accessories.abilities.FreezingAccessory;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IceRingItem extends RingItem implements FreezingAccessory {
    public IceRingItem(Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_RING, properties);
    }

    @Override
    public boolean isValidRepairItem(ItemStack repairItem, ItemStack repairMaterial) {
        return repairMaterial.is(AetherTags.Items.ICE_REPAIRING);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity) {
        if (!livingEntity.isInFluidType()) {
            this.freezeTick(slotContext, stack);
        }
    }
}
