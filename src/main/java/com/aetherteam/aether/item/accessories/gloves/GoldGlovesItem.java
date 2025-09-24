package com.aetherteam.aether.item.accessories.gloves;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterials;

public class GoldGlovesItem extends GlovesItem {
    public GoldGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.GOLD, punchDamage, "gold_gloves", SoundEvents.ARMOR_EQUIP_GOLD, properties);
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return super.makesPiglinsNeutral(stack, wearer);
    }
}
