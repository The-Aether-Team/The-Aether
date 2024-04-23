package com.aetherteam.aether.item.materials;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class SkyrootStickItem extends Item {
    public SkyrootStickItem(Properties properties) {
        super(properties);
        FuelRegistry.INSTANCE.add(this, 100);
    }
}
