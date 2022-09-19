package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.world.item.ItemStack;

public interface ZaniteTool {
    /**
     * Calculates mining speed increase using the default mining speed inputted into the zanite value buff function.
     * @param stack The stack being used for mining.
     * @param speed The mining speed of the stack.
     * @return The buffed mining speed of the zanite tool.
     */
    default float increaseSpeed(ItemStack stack, float speed) {
        return (float) EquipmentUtil.calculateZaniteBuff(stack, speed);
    }
}
