package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RegenerationStoneItem extends AccessoryItem {
    public RegenerationStoneItem(Properties properties) {
        super(properties);
    }

    /**
     * Regenerates half a heart every 50 ticks, if the wearer is missing health.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference The {@link SlotReference} of the accessory.
     */
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity.tickCount % 50 == 0) {
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0F);
            }
        }
    }
}
