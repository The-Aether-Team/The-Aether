package com.gildedgames.aether.item.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class BurnableBlockItem extends BlockItem
{
    public BurnableBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 300;
    }
}
