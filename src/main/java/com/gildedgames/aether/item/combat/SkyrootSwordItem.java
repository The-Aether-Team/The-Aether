package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItemTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class SkyrootSwordItem extends SwordItem {
    public SkyrootSwordItem() {
        super(AetherItemTiers.SKYROOT, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
