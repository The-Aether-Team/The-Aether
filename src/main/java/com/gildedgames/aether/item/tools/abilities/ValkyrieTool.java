package com.gildedgames.aether.item.tools.abilities;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public interface ValkyrieTool {
    /**
     * The unique identifier for the item's mining reach distance in the main hand.
     */
    UUID REACH_DISTANCE_MODIFIER_MAINHAND_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");
    /**
     * The unique identifier for the item's mining reach distance in the offhand.
     */
    UUID REACH_DISTANCE_MODIFIER_OFFHAND_UUID = UUID.fromString("42104790-AE67-4EDB-87FD-BD5D63EA7B17");
    /**
     * The unique identifier for the item's attack range distance in the main hand.
     */
    UUID ATTACK_RANGE_MODIFIER_MAINHAND_UUID = UUID.fromString("3B9D1CB7-D7E4-453E-BD7D-37D7F7846736");
    /**
     * The unique identifier for the item's attack range distance in the offhand.
     */
    UUID ATTACK_RANGE_MODIFIER_OFFHAND_UUID = UUID.fromString("87771DE8-25F0-4D55-A545-61628712FD07");

    /**
     * Sets up the attributes for the item if it is in the entity's main hand or offhand, adding attributes for the mining reach distance and the attack range distance alongside the default item attributes.
     * Different UUIDs for each hand are necessary to avoid bugs when switching items between hands. parse(Map&lt;String, String&gt;, String, String)
     * @param map The item's default attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}).
     * @param slot The {@link EquipmentSlot} the stack is in.
     * @return The new attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}) made up of the old attributes and the reach attributes.
     * @see com.gildedgames.aether.item.tools.valkyrie
     * @see com.gildedgames.aether.item.combat.loot.ValkyrieLanceItem
     */
    default Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            UUID reachDistanceUUID = slot == EquipmentSlot.MAINHAND ? REACH_DISTANCE_MODIFIER_MAINHAND_UUID : REACH_DISTANCE_MODIFIER_OFFHAND_UUID;
            UUID attackRangeUUID = slot == EquipmentSlot.MAINHAND ? ATTACK_RANGE_MODIFIER_MAINHAND_UUID : ATTACK_RANGE_MODIFIER_OFFHAND_UUID;

            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(reachDistanceUUID, "Reach distance modifier", this.getModifier(), AttributeModifier.Operation.ADDITION));
            attributeBuilder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(attackRangeUUID, "Attack range modifier", this.getModifier(), AttributeModifier.Operation.ADDITION));
            map = attributeBuilder.build();
        }
        return map;
    }

    /**
     * @return The default reach modifier value as a {@link Double}.
     */
    private double getModifier() {
        return 3.5;
    }
}