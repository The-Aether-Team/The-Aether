package com.aetherteam.aether.item.accessories.gloves;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterials;

public class LeatherGlovesItem extends GlovesItem {
    public LeatherGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.LEATHER, punchDamage, "leather_gloves", SoundEvents.ARMOR_EQUIP_LEATHER, properties);
    }
}
