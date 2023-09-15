package com.aetherteam.aether.item.accessories.gloves;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.DyeableLeatherItem;

public class LeatherGlovesItem extends GlovesItem implements DyeableLeatherItem {
    public LeatherGlovesItem(double punchDamage, Properties properties) {
        super(ArmorMaterials.LEATHER, punchDamage, "leather_gloves", () -> SoundEvents.ARMOR_EQUIP_LEATHER, properties);
    }
}
