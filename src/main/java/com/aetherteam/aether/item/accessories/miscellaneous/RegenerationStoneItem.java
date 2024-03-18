package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RegenerationStoneItem extends AccessoryItem {
    public RegenerationStoneItem(Properties properties) {
        super(properties);
    }

    /**
     * Regenerates half a heart every 50 ticks, if the wearer is missing health.
     * @param stack The Trinket {@link ItemStack}.
     * @param slotContext The {@link SlotReference} of the Trinket.
     * @param livingEntity The {@link LivingEntity} of the Trinket.
     */
    @Override
    public void tick(ItemStack stack, SlotReference slotContext, LivingEntity livingEntity) {
        if (livingEntity.tickCount % 50 == 0) {
            if (livingEntity.getHealth() < livingEntity.getMaxHealth()) {
                livingEntity.heal(1.0F);
            }
        }
    }
}
