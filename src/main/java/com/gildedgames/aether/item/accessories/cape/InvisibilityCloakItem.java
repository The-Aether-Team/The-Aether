package com.gildedgames.aether.item.accessories.cape;

import com.gildedgames.aether.item.accessories.AccessoryItem;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;

public class InvisibilityCloakItem extends AccessoryItem
{
    public InvisibilityCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().setInvisible(true);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        slotContext.entity().setInvisible(false);
    }
}
