package com.gildedgames.aether.common.item.accessories.misc;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class IronBubbleItem extends AccessoryItem
{
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
