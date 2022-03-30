package com.gildedgames.aether.common.item.combat.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public interface ZaniteWeapon {
    UUID DAMAGE_MODIFIER_UUID = UUID.fromString("CAE1DE8D-8A7F-4391-B6BD-C060B1DD49C5");

    static Multimap<Attribute, AttributeModifier> increaseSpeed(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if (stack.is(AetherTags.Items.ZANITE_WEAPONS) && slot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
			attributeBuilder.putAll(map);
			attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_UUID, "Damage modifier", calculateIncrease(stack), AttributeModifier.Operation.ADDITION));
			map = attributeBuilder.build();
		}
		return map;
    }

	static float calculateIncrease(ItemStack tool) { //TODO: Make less dependent on tier durability values. we have ways of doing this for the zanite accessories.
		int current = tool.getDamageValue();
		int maxDamage = tool.getMaxDamage();

		if (maxDamage - 50 <= current) {
			return 4.0F;
		}
		else if (maxDamage - 110 <= current) {
			return 3.0F;
		}
		else if (maxDamage - 200 <= current) {
			return 2.0F;
		}
		else if (maxDamage - 239 <= current) {
			return 1.0F;
		}
		else {
			return 0.0F;
		}
	}
}
