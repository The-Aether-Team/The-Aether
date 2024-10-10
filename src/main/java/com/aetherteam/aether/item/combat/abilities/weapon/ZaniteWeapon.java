package com.aetherteam.aether.item.combat.abilities.weapon;

import com.aetherteam.aether.item.EquipmentUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;
import java.util.UUID;

public interface ZaniteWeapon { //todo
//    /**
//     * The unique identifier for the item's damage bonus attribute.
//     */
//    UUID DAMAGE_MODIFIER_UUID = UUID.fromString("CAE1DE8D-8A7F-4391-B6BD-C060B1DD49C5");
//
//    /**
//     * Sets up the attributes for the item if it is in the entity's main hand, adding an attribute for the damage bonus value alongside the default item attributes.
//     *
//     * @param map   The item's default attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}).
//     * @param stack The {@link ItemStack} correlating to the item.
//     * @param slot  The {@link EquipmentSlot} the stack is in.
//     * @return The new attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}) made up of the old attributes and the damage bonus attribute.
//     * @see com.aetherteam.aether.item.combat.ZaniteSwordItem
//     */
//    default Multimap<Attribute, AttributeModifier> increaseDamage(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
//        if (slot == EquipmentSlot.MAINHAND) {
//            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
//            attributeBuilder.putAll(map);
//            attributeBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(DAMAGE_MODIFIER_UUID, "Damage modifier", this.calculateIncrease(map, stack), AttributeModifier.Operation.ADDITION));
//            map = attributeBuilder.build();
//        }
//        return map;
//    }
//
//    /**
//     * Calculates damage increase using the weapon's attack damage (minus player's base attack strength, which is default 1.0) inputted into the Zanite value buff function, which the original attack damage is then subtracted from to get the bonus damage amount from the difference.<br><br>
//     * The minimum possible damage bonus is 1 and the maximum possible damage bonus is 7.<br><br>
//     * <a href="https://www.desmos.com/calculator/rnnveeodba">See math visually.</a>
//     *
//     * @param map   The item's default attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}).
//     * @param stack The {@link ItemStack} correlating to the item.
//     * @return The damage bonus value for the zanite weapon, as an {@link Integer}.
//     */
//    private int calculateIncrease(Multimap<Attribute, AttributeModifier> map, ItemStack stack) {
//        double baseDamage = 0.0;
//        for (Iterator<AttributeModifier> it = map.get(Attributes.ATTACK_DAMAGE).stream().iterator(); it.hasNext(); ) {
//            AttributeModifier modifier = it.next();
//            baseDamage += modifier.amount();
//        }
//        double boostedDamage = EquipmentUtil.calculateZaniteBuff(stack, baseDamage);
//        boostedDamage -= baseDamage;
//        if (boostedDamage < 0.0) {
//            boostedDamage = 0.0;
//        }
//        return (int) Math.round(boostedDamage);
//    }
}
