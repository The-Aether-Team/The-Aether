package com.aetherteam.aether.item.block;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.Nullable;

public class AmbrosiumBlockItem extends BlockItem {
    public AmbrosiumBlockItem(Block block, Properties properties) {
        super(block, properties);
        FuelRegistry.INSTANCE.add(this, 16000);
    }
}
