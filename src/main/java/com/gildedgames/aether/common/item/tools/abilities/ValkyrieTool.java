package com.gildedgames.aether.common.item.tools.abilities;

import com.gildedgames.aether.common.registry.AetherTags;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public interface ValkyrieTool {
    UUID REACH_DISTANCE_MODIFIER_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");
    UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("3B9D1CB7-D7E4-453E-BD7D-37D7F7846736");

    static Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, ItemStack stack, EquipmentSlot slot) {
        if ((stack.is(AetherTags.Items.VALKYRIE_TOOLS) || stack.is(AetherTags.Items.VALKYRIE_WEAPONS)) && (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND)) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_DISTANCE_MODIFIER_UUID, "Reach distance modifier", getModifier(), AttributeModifier.Operation.ADDITION));
            attributeBuilder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ATTACK_RANGE_MODIFIER_UUID, "Attack range modifier", getModifier(), AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

    private static double getModifier() {
        return 3.5D;
    }
}