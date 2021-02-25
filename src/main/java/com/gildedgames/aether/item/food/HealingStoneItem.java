package com.gildedgames.aether.item.food;

import com.gildedgames.aether.registry.AetherItemGroups;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class HealingStoneItem extends Item
{
    public HealingStoneItem() {
        super(new Item.Properties().rarity(Rarity.RARE)
                .food(new Food.Builder().setAlwaysEdible().hunger(0).effect(() -> new EffectInstance(Effects.REGENERATION, 610, 0), 1.0F).build())
                .group(AetherItemGroups.AETHER_FOOD));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
