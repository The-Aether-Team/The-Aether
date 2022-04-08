package com.gildedgames.aether.common.item.combat.abilities.weapon;

import com.gildedgames.aether.common.registry.AetherTags;
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
    UUID DAMAGE_MODIFIER_UUID = UUID.fromString("CAE1DE8D-8A7F-4391-B6BD-C060B1DD49C5");

    static Multimap<Attribute, AttributeModifier> increaseSpeed(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if (stack.is(AetherTags.Items.ZANITE_WEAPONS) && slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
			attributeBuilder.putAll(map);
			attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_UUID, "Damage modifier", calculateIncrease(map, stack), AttributeModifier.Operation.ADDITION));
			map = attributeBuilder.build();
		}
		return map;
    }

	private static int calculateIncrease(Multimap<Attribute, AttributeModifier> map, ItemStack stack) {
		float baseDamage = 0.0F;
		for (Iterator<AttributeModifier> it = map.get(Attributes.ATTACK_DAMAGE).stream().iterator(); it.hasNext();) {
			AttributeModifier modifier = it.next();
			baseDamage += modifier.getAmount();
		}
		float boostedDamage = baseDamage * (2.0F * ((float) stack.getDamageValue()) / ((float) stack.getMaxDamage()) + 0.5F);
		boostedDamage -= baseDamage;
		if (boostedDamage < 0.0F) {
			boostedDamage = 0.0F;
		}
		return Math.round(boostedDamage);
	}
}
