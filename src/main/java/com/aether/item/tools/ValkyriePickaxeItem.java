package com.aether.item.tools;

import com.aether.item.tools.abilities.IValkyrieToolItem;
import com.aether.registry.AetherItemGroups;
import com.aether.registry.AetherItemTier;
import com.aether.registry.AetherItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

import net.minecraftforge.common.ForgeMod;

public class ValkyriePickaxeItem extends PickaxeItem implements IValkyrieToolItem
{
    private final int attackDamage;
    private final float attackSpeed;

    private Multimap<Attribute, AttributeModifier> pickaxeAttributes;

    public ValkyriePickaxeItem(int attackDamageIn, float attackSpeedIn) {
        super(AetherItemTier.VALKYRIE, attackDamageIn, attackSpeedIn, new Item.Properties().rarity(AetherItems.AETHER_LOOT).group(AetherItemGroups.AETHER_TOOLS));
        this.attackDamage = attackDamageIn;
        this.attackSpeed = attackSpeedIn;
    }

    /**
     * Adds the REACH_DISTANCE AttributeModifier to the entity using the item.
     * It's important to note that this attribute only works when targetting blocks.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", this.attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(reachModifierUUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
        this.pickaxeAttributes = builder.build();
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.pickaxeAttributes : super.getAttributeModifiers(equipmentSlot);
    }
}
