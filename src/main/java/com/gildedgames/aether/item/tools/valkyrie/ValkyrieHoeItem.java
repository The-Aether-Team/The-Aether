package com.gildedgames.aether.item.tools.valkyrie;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ValkyrieHoeItem extends HoeItem implements ValkyrieTool {
    public ValkyrieHoeItem() {
        super(AetherItemTiers.VALKYRIE, -4, 0.0F, new Item.Properties().rarity(AetherItems.AETHER_LOOT).tab(AetherItemGroups.AETHER_TOOLS));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.extendReachModifier(super.getAttributeModifiers(slot, stack), slot);
    }
}
