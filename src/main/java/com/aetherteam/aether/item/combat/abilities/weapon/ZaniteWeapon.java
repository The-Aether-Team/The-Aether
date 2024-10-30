package com.aetherteam.aether.item.combat.abilities.weapon;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.concurrent.atomic.AtomicReference;

public interface ZaniteWeapon {
    /**
     * The unique identifier for the item's damage bonus attribute.
     */
    ResourceLocation DAMAGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "zanite_weapon_attack_damage");

    /**
     * Sets up the attribute for the damage bonus value for the item if it is in the entity's main hand.
     * @param modifiers The item's default {@link ItemAttributeModifiers}.
     * @param stack The {@link ItemStack} correlating to the item.
     * @return A new attributes {@link ItemAttributeModifiers.Entry} for the damage bonus attribute.
     * @see com.aetherteam.aether.item.combat.ZaniteSwordItem
     */
    default ItemAttributeModifiers.Entry increaseDamage(ItemAttributeModifiers modifiers, ItemStack stack) {
        return new ItemAttributeModifiers.Entry(Attributes.ATTACK_DAMAGE,
            new AttributeModifier(DAMAGE_MODIFIER_ID, this.calculateDamageIncrease(Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_ID, modifiers, stack), AttributeModifier.Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND);
    }

    /**
     * Calculates damage increase using the weapon's attack damage (minus player's base attack strength, which is default 1.0) inputted into the Zanite value buff function, which the original attack damage is then subtracted from to get the bonus damage amount from the difference.<br><br>
     * The minimum possible damage bonus is 1 and the maximum possible damage bonus is 7.<br><br>
     * <a href="https://www.desmos.com/calculator/rnnveeodba">See math visually.</a>
     * @param base The base damage modifier {@link Attribute} {@link Holder}.
     * @param bonusModifier The base damage modifier {@link ResourceLocation} ID.
     * @param modifiers The item's default {@link ItemAttributeModifiers}.
     * @param stack The {@link ItemStack} correlating to the item.
     * @return The damage bonus value for the zanite weapon, as an {@link Integer}.
     */
    default int calculateDamageIncrease(Holder<Attribute> base, ResourceLocation bonusModifier, ItemAttributeModifiers modifiers, ItemStack stack) {
        AtomicReference<Double> baseStat = new AtomicReference<>(0.0);
        modifiers.forEach(EquipmentSlotGroup.MAINHAND, (attribute, modifier) -> {
            if (attribute.value() == base.value() && !modifier.id().equals(bonusModifier)) {
                baseStat.updateAndGet(v -> v + modifier.amount());
            }
        });
        double baseDamage = baseStat.get();
        double boostedDamage = EquipmentUtil.calculateZaniteBuff(stack, baseDamage);
        boostedDamage -= baseDamage;
        if (boostedDamage < 0.0) {
            boostedDamage = 0.0;
        }
        return (int) Math.round(boostedDamage);
    }
}
