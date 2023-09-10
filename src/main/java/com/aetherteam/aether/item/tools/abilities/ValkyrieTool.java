package com.aetherteam.aether.item.tools.abilities;

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
    UUID REACH_DISTANCE_MODIFIER_UUID = UUID.fromString("0A87A51E-A43F-4EEF-A770-07C2160D373D");
    /**
     * The unique identifier for the item's attack range distance in the main hand.
     */
    UUID ATTACK_RANGE_MODIFIER_UUID = UUID.fromString("3B9D1CB7-D7E4-453E-BD7D-37D7F7846736");

    /**
     * Sets up the attributes for the item if it is in the entity's main hand, adding attributes for the mining reach distance and the attack range distance alongside the default item attributes.<br><br>
     * @param map The item's default attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}).
     * @param slot The {@link EquipmentSlot} the stack is in.
     * @return The new attributes ({@link Multimap Multimap&lt;Attribute, AttributeModifier&gt;}) made up of the old attributes and the reach attributes.
     * @see com.aetherteam.aether.item.tools.valkyrie
     * @see com.aetherteam.aether.item.combat.loot.ValkyrieLanceItem
     */
    default Multimap<Attribute, AttributeModifier> extendReachModifier(Multimap<Attribute, AttributeModifier> map, EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = ImmutableMultimap.builder();
            attributeBuilder.putAll(map);
            attributeBuilder.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(REACH_DISTANCE_MODIFIER_UUID, "Reach distance modifier", this.getModifier(), AttributeModifier.Operation.ADDITION));
            attributeBuilder.put(ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(ATTACK_RANGE_MODIFIER_UUID, "Attack range modifier", this.getModifier(), AttributeModifier.Operation.ADDITION));
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