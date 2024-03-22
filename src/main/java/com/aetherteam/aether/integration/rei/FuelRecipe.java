package com.aetherteam.aether.integration.rei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record FuelRecipe(List<ItemStack> inputItems, int burnTime, Block usageBlock) { }
