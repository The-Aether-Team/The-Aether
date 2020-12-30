package com.aether.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class VampireBladeItem extends SwordItem {
    public VampireBladeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.getHealth() < attacker.getMaxHealth()) {
            attacker.heal(1.0F);
        }
        return super.hitEntity(stack, target, attacker);
    }
}
