package com.gildedgames.aether.item.tools.valkyrie;

import com.gildedgames.aether.item.AetherItems;
import com.gildedgames.aether.item.combat.AetherItemTiers;
import com.gildedgames.aether.item.tools.abilities.ValkyrieTool;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ValkyrieShovelItem extends ShovelItem implements ValkyrieTool {
    public ValkyrieShovelItem() {
        super(AetherItemTiers.VALKYRIE, 1.5F, -3.3F, new Item.Properties().rarity(AetherItems.AETHER_LOOT));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.extendReachModifier(super.getAttributeModifiers(slot, stack), slot);
    }

    /**
     * When in a creative tab, this adds a tooltip to an item indicating what dungeon it can be found in.
     * @param stack The {@link ItemStack} with the tooltip.
     * @param level The {@link Level} the item is rendered in.
     * @param components A {@link List} of {@link Component}s making up this item's tooltip.
     * @param flag A {@link TooltipFlag} for the tooltip type.
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        if (flag.isCreative()) {
            components.add(AetherItems.SILVER_DUNGEON_TOOLTIP);
        }
    }
}
