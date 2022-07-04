package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.block.AetherBlockStateProperties;
import com.gildedgames.aether.block.DoubleDrops;
import com.gildedgames.aether.AetherTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;

public interface SkyrootTool {
    static ItemStack doubleDrops(ItemStack drop, ItemStack stack, BlockState state) {
        if (stack != null && stack.is(AetherTags.Items.SKYROOT_TOOLS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            if (state != null && (!(state.getBlock() instanceof DoubleDrops) || state.getValue(AetherBlockStateProperties.DOUBLE_DROPS))) {
                if (stack.isCorrectToolForDrops(state)) {
                    drop.setCount(2 * drop.getCount());
                }
            }
        }
        return drop;
    }
}
