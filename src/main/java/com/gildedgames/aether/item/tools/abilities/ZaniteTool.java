package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.AetherTags;
import net.minecraft.world.item.ItemStack;

public interface ZaniteTool {
    static float increaseSpeed(ItemStack stack, float speed) {
        if (stack.is(AetherTags.Items.ZANITE_TOOLS)) {
            speed *= (2.0F * ((float) stack.getDamageValue()) / ((float) stack.getMaxDamage()) + 0.5F);
        }
        return speed;
    }
}
