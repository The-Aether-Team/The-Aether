package com.aetherteam.aether.item.tools.abilities;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface SkyrootTool {
    /**
     * Doubles the count of item drops as long as the harvesting tool doesn't have silk touch and the block is able to have double drops.
     *
     * @param drop  The {@link ItemStack} dropped from a block.
     * @param tool  The {@link ItemStack} of the tool used to harvest the block.
     * @param state The {@link BlockState} of the block.
     * @return The new modified {@link ItemStack} of the item drop.
     * @see com.aetherteam.aether.loot.functions.DoubleDrops
     */
    default ItemStack doubleDrops(Level level, ItemStack drop, @Nullable ItemStack tool, @Nullable BlockState state) {
        if (tool != null && tool.getEnchantmentLevel(level.holderOrThrow(Enchantments.SILK_TOUCH)) == 0) {
            if (state != null && state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)) {
                if (tool.isCorrectToolForDrops(state)) {
                    drop.setCount(2 * drop.getCount());
                }
            }
        }
        return drop;
    }
}
