package com.aetherteam.aether.item.combat.loot;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.aetherteam.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import io.github.fabricators_of_create.porting_lib.tool.ToolAction;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import io.github.fabricators_of_create.porting_lib.tool.addons.ToolActionItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class ValkyrieLanceItem extends SwordItem implements ValkyrieTool, CustomEnchantingBehaviorItem, ToolActionItem {
    public ValkyrieLanceItem() {
        super(AetherItemTiers.VALKYRIE, 3, -2.7F, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        return this.extendReachModifier(super.getAttributeModifiers(stack, slot), slot);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category.canEnchant(stack.getItem()) && enchantment != Enchantments.SWEEPING_EDGE;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction) && toolAction != ToolActions.SWORD_SWEEP;
    }
}
