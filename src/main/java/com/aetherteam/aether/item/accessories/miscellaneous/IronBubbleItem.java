package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IronBubbleItem extends AccessoryItem {
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    /**
     * Keeps the wearer's air supply at 30 if they're underwater.
     *
     * @param slotContext The {@link SlotContext} of the Curio.
     * @param stack       The Curio {@link ItemStack}.
     */
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
