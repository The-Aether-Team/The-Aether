package com.gildedgames.aether.item.tools.abilities;

import com.gildedgames.aether.AetherTags;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public interface ValkyrieTool {
    UUID REACH_DISTANCE_MODIFIER_MAINHAND_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");
    UUID REACH_DISTANCE_MODIFIER_OFFHAND_UUID = UUID.fromString("42104790-AE67-4EDB-87FD-BD5D63EA7B17");

    UUID ATTACK_RANGE_MODIFIER_MAINHAND_UUID = UUID.fromString("3B9D1CB7-D7E4-453E-BD7D-37D7F7846736");
    UUID ATTACK_RANGE_MODIFIER_OFFHAND_UUID = UUID.fromString("87771DE8-25F0-4D55-A545-61628712FD07");

    static Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if ((stack.is(AetherTags.Items.VALKYRIE_TOOLS) || stack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)) {
            UUID reachDistanceUUID = slot == EquipmentSlot.MAINHAND ? REACH_DISTANCE_MODIFIER_MAINHAND_UUID : REACH_DISTANCE_MODIFIER_OFFHAND_UUID;
            UUID attackRange = slot == EquipmentSlot.MAINHAND ? ATTACK_RANGE_MODIFIER_MAINHAND_UUID : ATTACK_RANGE_MODIFIER_OFFHAND_UUID;
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(reachDistanceUUID, "Reach distance modifier", getModifier(), AttributeModifier.Operation.ADDITION));
            attributeBuilder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(attackRange, "Attack range modifier", getModifier(), AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

    private static double getModifier() {
        return 3.5D;
    }
}