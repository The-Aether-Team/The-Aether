package com.gildedgames.aether.item.tools.skyroot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItemTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class SkyrootPickaxeItem extends PickaxeItem {
    public SkyrootPickaxeItem() {
        super(AetherItemTiers.SKYROOT, 1, -2.8F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
