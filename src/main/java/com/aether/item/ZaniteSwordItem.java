package com.aether.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import net.minecraft.item.Item.Properties;

public class ZaniteSwordItem extends SwordItem {

	public ZaniteSwordItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
		super(tier, attackDamageIn, attackSpeedIn, builder);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
		if (equipmentSlot == EquipmentSlotType.MAINHAND) {
			return ImmutableMultimap.of(
					Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", calculateIncrease(stack), AttributeModifier.Operation.ADDITION),
					Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION)
			);
		}
		return super.getAttributeModifiers(equipmentSlot, stack);
	}
	
	public float calculateIncrease(ItemStack tool) {
		int current = tool.getDamage();
		int maxDamage = tool.getMaxDamage();
		
		if (maxDamage - 50 <= current) {
			return 7.0F;
		}
		else if (maxDamage - 110 <= current) {
			return 6.0F;
		}
		else if (maxDamage - 200 <= current) {
			return 5.0F;
		}
		else if (maxDamage - 239 <= current) {
			return 4.0F;
		}
		else {
			return 3.0F;
		}
	}

}
