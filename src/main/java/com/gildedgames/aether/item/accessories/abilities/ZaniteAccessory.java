package com.gildedgames.aether.item.accessories.abilities;

import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotResult;

public interface ZaniteAccessory {
    static float handleMiningSpeed(float speed, SlotResult slotResult) {
        ItemStack stack = slotResult.stack();
        return speed * (1.0F + (((float) stack.getDamageValue()) / (((float) stack.getMaxDamage()) * 3.0F)));
    }
}
