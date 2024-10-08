package com.aetherteam.aether.item.tools.valkyrie;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;

public class ValkyriePickaxeItem extends PickaxeItem implements ValkyrieTool {
    public ValkyriePickaxeItem() {
        super(AetherItemTiers.VALKYRIE, new Item.Properties().rarity(AetherItems.AETHER_LOOT).attributes(PickaxeItem.createAttributes(AetherItemTiers.VALKYRIE, 1.0F, -3.1F)
            .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)));
    }
}
