package com.aether.item.combat;

import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

import net.minecraft.item.Item.Properties;

public class FlamingSwordItem extends SwordItem
{
	public FlamingSwordItem() {
		super(ItemTier.DIAMOND, 3, -2.4f, new Item.Properties().maxDamage(502).rarity(AetherItems.AETHER_LOOT).group(AetherItemGroups.AETHER_WEAPONS));
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
