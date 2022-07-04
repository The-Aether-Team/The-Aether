package com.gildedgames.aether.item.accessories.miscellaneous;

import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class IronBubbleItem extends AccessoryItem
{
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
