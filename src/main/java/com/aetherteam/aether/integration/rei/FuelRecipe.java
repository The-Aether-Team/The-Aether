package com.aetherteam.aether.integration.rei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record FuelRecipe(List<ItemStack> itemStacks, int burnTime, Block block) {

    public List<ItemStack> getInput() {
        return itemStacks;
    }

    public int getBurnTime() {
        return burnTime;
    }


    public Block getUsage() {
        return block;
    }
}
