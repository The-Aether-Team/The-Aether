package com.gildedgames.aether.common.item.accessories.ring;

import com.gildedgames.aether.client.registry.AetherSoundEvents;
import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class IceRingItem extends RingItem implements IIceAccessory
{
    public IceRingItem(Properties properties) {
        super(AetherSoundEvents.ITEM_ACCESSORY_EQUIP_ICE_RING, properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        handleLiquidFreezing(identifier, index, livingEntity, stack);
    }
}
