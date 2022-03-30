package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.item.ItemStack;

public interface ZaniteTool {
    static float increaseSpeed(ItemStack stack, float originalSpeed) { //TODO: Make less dependent on tier durability values. we have ways of doing this for the zanite accessories.
        if (stack.is(AetherTags.Items.ZANITE_TOOLS)) {
            if (originalSpeed == 6.0F) {
                int current = stack.getDamageValue();
                int maxDamage = stack.getMaxDamage();

                if (maxDamage - 50 <= current && current <= maxDamage) {
                    return 12.0F;
                } else if (maxDamage - 110 <= current && current <= maxDamage - 51) {
                    return 8.0F;
                } else if (maxDamage - 200 <= current && current <= maxDamage - 111) {
                    return 6.0F;
                } else if (maxDamage - 239 <= current && current <= maxDamage - 201) {
                    return 4.0F;
                } else {
                    return 2.0F;
                }
            } else {
                return 1.0F;
            }
        }
        return originalSpeed;
    }
}
