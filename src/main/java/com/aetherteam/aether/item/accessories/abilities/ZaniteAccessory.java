package com.aetherteam.aether.item.accessories.abilities;

import net.minecraft.world.item.ItemStack;

public interface ZaniteAccessory {
    /**
     * Calculates damage increase based on a base value, the amount of damage taken (maximum durability - current durability), and the stack's maximum durability.<br><br>
     * <a href="https://www.desmos.com/calculator/drohdchhsx">See math visually.</a>
     *
     * @param speed A base {@link Float} value.
     * @param stack The {@link ItemStack} of the Curio.
     * @return The buffed {@link Float} value.
     */
    static float handleMiningSpeed(float speed, ItemStack stack) {
        return speed * (1.4F + (((float) stack.getDamageValue()) / (((float) stack.getMaxDamage()) * 3.0F)));
    }
}
