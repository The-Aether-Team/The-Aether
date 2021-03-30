package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class InvisibilityCloakItem extends AccessoryItem
{
    public InvisibilityCloakItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onEquip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        livingEntity.setInvisible(true);
    }

    @Override
    public void onUnequip(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        livingEntity.setInvisible(false);
    }
}
