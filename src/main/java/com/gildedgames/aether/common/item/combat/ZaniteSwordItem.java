package com.gildedgames.aether.common.item.combat;

import com.gildedgames.aether.common.registry.AetherItemGroups;
import com.gildedgames.aether.common.registry.AetherItemTiers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class ZaniteSwordItem extends SwordItem
{
	public ZaniteSwordItem() {
		super(AetherItemTiers.ZANITE, 5, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			return ImmutableMultimap.of(
					Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", calculateIncrease(stack), AttributeModifier.Operation.ADDITION),
					Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4f, AttributeModifier.Operation.ADDITION)
			);
		}
		return super.getAttributeModifiers(equipmentSlot, stack);
	}
	
	public float calculateIncrease(ItemStack tool) {
		int current = tool.getDamageValue();
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
