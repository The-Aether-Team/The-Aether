package com.aetherteam.aether.item.accessories.miscellaneous;

import com.aetherteam.aether.inventory.container.AccessoryContainer;
import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IronBubbleItem extends AccessoryItem {
    public IronBubbleItem(Properties properties) {
        super(properties, AccessoryContainer.SlotType.ACCESSORY);
    }

    /**
     * Keeps the wearer's air supply at 30 if they're underwater.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference The {@link SlotReference} of the accessory.
     */
    @Override
    public void tick(ItemStack stack, Level level, LivingEntity entity, InteractionHand hand) {
        if (entity.isUnderWater()) {
            entity.setAirSupply(30);
        }
    }
}
