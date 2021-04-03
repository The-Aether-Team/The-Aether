package com.gildedgames.aether.common.item.accessories.ring;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class IceRingItem extends AccessoryItem
{
    public IceRingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        IIceAccessory.handleLiquidFreezing(identifier, index, livingEntity, stack);
    }
}
