package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IronBubbleItem extends AccessoryItem {
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    /**
     * Keeps the wearer's air supply at 30 if they're underwater.
     * @param stack The Trinket {@link ItemStack}.
     * @param slotContext The {@link SlotReference} of the Accessory.
     */
    @Override
    public void tick(ItemStack stack, SlotReference slotContext) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
