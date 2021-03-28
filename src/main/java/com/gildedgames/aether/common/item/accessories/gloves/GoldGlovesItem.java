package com.gildedgames.aether.common.item.accessories.gloves;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class GoldGlovesItem extends AccessoryItem
{
    public GoldGlovesItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
