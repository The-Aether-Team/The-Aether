package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;

public class FlamingSwordItem extends SwordItem
{
	public FlamingSwordItem() {
		super(ItemTier.DIAMOND, 3, -2.4f, new Item.Properties().durability(502).rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int defaultTime = 30;
		int fireAspectModifier = EnchantmentHelper.getFireAspect(attacker);
		if (fireAspectModifier > 0) {
			defaultTime += (fireAspectModifier * 4);
		}
		target.setSecondsOnFire(defaultTime);
		return super.hurtEnemy(stack, target, attacker);
	}
}
