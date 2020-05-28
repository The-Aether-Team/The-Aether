package com.aether.item;

import java.util.Collections;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class ZaniteSwordItem extends SwordItem {

	public ZaniteSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		if (slot == EquipmentSlotType.MAINHAND) {
			multimap.replaceValues(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), Collections.singleton(new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", calculateIncrease(stack), AttributeModifier.Operation.ADDITION)));
		}
		
		return multimap;
	}
	
	public float calculateIncrease(ItemStack tool) {
		int current = tool.getDamage();
		int maxDamage = tool.getMaxDamage();
		
		if (maxDamage - 50 <= current) {
			return 7.0f;
		}
		else if (maxDamage - 110 <= current) {
			return 6.0f;
		}
		else if (maxDamage - 200 <= current) {
			return 5.0f;
		}
		else if (maxDamage - 239 <= current) {
			return 4.0f;
		}
		else {
			return 3.0f;
		}
	}

}
