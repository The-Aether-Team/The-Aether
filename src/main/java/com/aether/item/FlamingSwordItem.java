package com.aether.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import net.minecraft.item.Item.Properties;

public class FlamingSwordItem extends SwordItem {

	public FlamingSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int defaultTime = 30;
		int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(attacker);
		if (fireAspectModifier > 0) {
			defaultTime += (fireAspectModifier * 4);
		}
		target.setFire(defaultTime);
		return super.hitEntity(stack, target, attacker);
	}

}
