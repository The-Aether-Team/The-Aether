package com.gildedgames.aether.item.tools.skyroot;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

/**
 * Double drops behavior is called by {@link com.gildedgames.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootAxeItem extends AxeItem implements SkyrootTool {
    public SkyrootAxeItem() {
        super(AetherItemTiers.SKYROOT, 6.0F, -3.2F, new Item.Properties().tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
