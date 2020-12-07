package com.aether.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraftforge.common.ForgeMod;

public class ValkyrieAxeItem extends AxeItem implements IValkyrieToolItem {

    private Multimap<Attribute, AttributeModifier> axeAttributes;

    public ValkyrieAxeItem(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    /**
     * Adds the REACH_DISTANCE AttributeModifier to the entity using the item.
     * It's important to note that this attribute only works when targetting blocks.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(reachModifierUUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
        this.axeAttributes = builder.build();
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.axeAttributes : super.getAttributeModifiers(equipmentSlot);
    }
}
