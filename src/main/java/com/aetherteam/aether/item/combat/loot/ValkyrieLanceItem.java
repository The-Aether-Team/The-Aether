package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class ValkyrieLanceItem extends SwordItem implements ValkyrieTool {
    public ValkyrieLanceItem() {
        super(AetherItemTiers.VALKYRIE, new Item.Properties().rarity(AetherItems.AETHER_LOOT).attributes(SwordItem.createAttributes(AetherItemTiers.VALKYRIE, 3, -2.7F)
            .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
            .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_RANGE_MODIFIER_UUID, RANGE_MODIFER, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.isPrimaryItemFor(stack, enchantment) && enchantment.is(Enchantments.SWEEPING_EDGE);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ItemAbility) {
        return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(ItemAbility) && ItemAbility != ItemAbilities.SWORD_SWEEP;
    }
}
