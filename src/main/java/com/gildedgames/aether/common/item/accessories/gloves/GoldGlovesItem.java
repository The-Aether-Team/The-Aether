package com.gildedgames.aether.common.item.accessories.gloves;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class GoldGlovesItem extends GlovesItem
{
    public GoldGlovesItem(double punchDamage, Properties properties) {
        super(punchDamage, "gold_gloves", properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }
}
