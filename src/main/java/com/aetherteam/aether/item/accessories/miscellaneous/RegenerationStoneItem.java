package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RegenerationStoneItem extends AccessoryItem {
    public RegenerationStoneItem(Properties properties) {
        super(properties);
    }

    /**
     * Regenerates half a heart every 50 ticks, if the wearer is missing health.
     *
     * @param slotContext The {@link SlotContext} of the Curio.
     * @param stack       The Curio {@link ItemStack}.
     */
    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.tickCount % 50 == 0) {
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0F);
            }
        }
    }
}
