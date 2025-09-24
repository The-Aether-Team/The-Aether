package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RegenerationStoneItem extends AccessoryItem {
    public RegenerationStoneItem(Properties properties) {
        super(properties, AccessoryContainer.SlotType.ACCESSORY);
    }

    /**
     * Regenerates half a heart every 50 ticks, if the wearer is missing health.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference The {@link SlotReference} of the accessory.
     */
    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        if (entity.tickCount % 50 == 0) {
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(1.0F);
            }
        }
    }
}
