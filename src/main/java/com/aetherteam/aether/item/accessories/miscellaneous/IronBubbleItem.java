package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IronBubbleItem extends AccessoryItem {
    public IronBubbleItem(Properties properties) {
        super(properties);
    }

    /**
     * Keeps the wearer's air supply at 30 if they're underwater.
     * @param stack The Trinket {@link ItemStack}.
     * @param slotContext The {@link SlotReference} of the Trinket.
     * @param livingEntity The {@link LivingEntity} of the Trinket.
     */
    @Override
    public void tick(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity) {
        if (livingEntity.isUnderWater()) {
            livingEntity.setAirSupply(30);
        }
    }
}
