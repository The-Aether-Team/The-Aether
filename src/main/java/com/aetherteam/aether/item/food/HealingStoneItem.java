package com.aetherteam.aether.item.food;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class HealingStoneItem extends Item {
    public HealingStoneItem() {
        super(new Item.Properties().rarity(Rarity.RARE).food(AetherFoods.HEALING_STONE));
    }

    /**
     * @param stack The {@link ItemStack}.
     * @return Whether the item should render an enchantment glint, as a {@link Boolean}.
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
