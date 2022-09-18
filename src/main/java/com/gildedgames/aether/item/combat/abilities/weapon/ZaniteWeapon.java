package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.UUID;

public interface ZaniteWeapon {
	/**
	 * The unique identifier for the item's damage bonus attribute.
	 */
	UUID DAMAGE_MODIFIER_UUID = UUID.fromString("CAE1DE8D-8A7F-4391-B6BD-C060B1DD49C5");

	/**
	 * Sets up the attributes for the item if it is a zanite weapon and is in the entity's main hand, adding an attribute for the damage bonus value alongside the default item attributes.
	 * @param map The item's default attributes.
	 * @param stack The stack correlating to the item.
	 * @param slot The slot the stack is in.
	 * @return The new attributes made up of the old attributes and the damage bonus attribute.
	 */
    default Multimap<Attribute, AttributeModifier> increaseDamage(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if (stack.is(AetherTags.Items.ZANITE_WEAPONS) && slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
			attributeBuilder.putAll(map);
			attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_UUID, "Damage modifier", this.calculateIncrease(map, stack), AttributeModifier.Operation.ADDITION));
			map = attributeBuilder.build();
		}
		return map;
    }

	/**
	 * Calculates damage increase based on the weapon's attack damage (minus player's base attack strength, which is default 1.0), the stack's maximum durability, and the amount of damage taken (maximum durability - current durability).<br>
	 * <a href="https://www.desmos.com/calculator/6nscexk6ez">See math visually.</a>
	 * @param map The item's default attributes.
	 * @param stack The stack correlating to the item.
	 * @return The damage bonus value for the zanite weapon.
	 */
	private int calculateIncrease(Multimap<Attribute, AttributeModifier> map, ItemStack stack) {
		double baseDamage = 0.0;
		for (Iterator<AttributeModifier> it = map.get(Attributes.ATTACK_DAMAGE).stream().iterator(); it.hasNext();) {
			AttributeModifier modifier = it.next();
			baseDamage += modifier.getAmount();
		}
		double boostedDamage = baseDamage * (2.0 * ((double) stack.getDamageValue()) / ((double) stack.getMaxDamage()) + 0.5);
		boostedDamage -= baseDamage;
		if (boostedDamage < 0.0) {
			boostedDamage = 0.0;
		}
		return (int) Math.round(boostedDamage);
	}
}
