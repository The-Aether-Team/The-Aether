package com.gildedgames.aether.common.item.accessories.pendant;

import com.gildedgames.aether.common.item.accessories.abilities.IIceAccessory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class IcePendantItem extends PendantItem
{
    public IcePendantItem(Properties properties) {
        super("ice_pendant", properties);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        IIceAccessory.handleLiquidFreezing(identifier, index, livingEntity, stack);
    }
}
