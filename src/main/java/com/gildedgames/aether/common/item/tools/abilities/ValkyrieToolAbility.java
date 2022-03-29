package com.gildedgames.aether.common.item.tools.abilities;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface ValkyrieToolAbility {
    UUID REACH_MODIFIER_UUID = new UUID(-2346749345421374890L, -8027384656528210169L);

    default double getReachDistanceModifier() {
        return 3.0D;
    }

    default Multimap<Attribute, AttributeModifier> createDefaultAttributes(@Nonnull EquipmentSlot equipmentSlot, UUID baseDamage, float attackDamage, UUID baseSpeed, float attackSpeed, Multimap<Attribute, AttributeModifier> defaultAttributes) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(baseDamage, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(baseSpeed, "Tool modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_MODIFIER_UUID, "Tool modifier", this.getReachDistanceModifier(), AttributeModifier.Operation.ADDITION));
        Multimap<Attribute, AttributeModifier> attributes = builder.build();
        return equipmentSlot == EquipmentSlot.MAINHAND ? attributes : defaultAttributes;
    }
}
