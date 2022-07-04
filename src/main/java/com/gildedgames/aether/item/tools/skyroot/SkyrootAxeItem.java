package com.gildedgames.aether.item.tools.skyroot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class SkyrootAxeItem extends AxeItem {
    public SkyrootAxeItem() {
        super(AetherItemTiers.SKYROOT, 6.0F, -3.2F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
