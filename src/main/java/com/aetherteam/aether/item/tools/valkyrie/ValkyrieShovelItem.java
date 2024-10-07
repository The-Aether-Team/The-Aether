package com.aetherteam.aether.item.tools.valkyrie;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;

public class ValkyrieShovelItem extends ShovelItem implements ValkyrieTool {
    public ValkyrieShovelItem() {
        super(AetherItemTiers.VALKYRIE, new Item.Properties().rarity(AetherItems.AETHER_LOOT).attributes(ShovelItem.createAttributes(AetherItemTiers.VALKYRIE, 1.5F, -3.3F)));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.extendReachModifier(super.getAttributeModifiers(slot, stack), slot);
    }
}
