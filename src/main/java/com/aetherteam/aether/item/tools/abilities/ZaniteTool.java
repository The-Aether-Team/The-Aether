package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.item.ItemStack;

public interface ZaniteTool {
    /**
     * Calculates mining speed increase using the default mining speed inputted into the Zanite value buff function.
     *
     * @param stack The {@link ItemStack} being used for mining.
     * @param speed The mining speed of the stack as a {@link Float}.
     * @return The buffed mining speed of the zanite tool as a {@link Float}.
     * @see com.aetherteam.aether.event.hooks.AbilityHooks.ToolHooks#handleZaniteToolAbility(ItemStack, float)
     */
    default float increaseSpeed(ItemStack stack, float speed) {
        return (float) EquipmentUtil.calculateZaniteBuff(stack, speed);
    }
}
