package com.gildedgames.aether.item.tools.skyroot;

import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.SkyrootTool;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

/**
 * Double drops behavior is called by {@link com.gildedgames.aether.loot.functions.DoubleDrops}.
 */
public class SkyrootHoeItem extends HoeItem implements SkyrootTool {
    public SkyrootHoeItem() {
        super(AetherItemTiers.SKYROOT, 0, -3.0F, new Item.Properties());
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return 200;
    }
}
