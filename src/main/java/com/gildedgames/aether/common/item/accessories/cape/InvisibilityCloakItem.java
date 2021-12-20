package com.gildedgames.aether.common.item.accessories.cape;

import com.gildedgames.aether.common.item.accessories.AccessoryItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

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
