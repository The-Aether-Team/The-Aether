package com.gildedgames.aether.item.accessories.gloves;

import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.sounds.SoundEvents;

public class LeatherGlovesItem extends GlovesItem implements DyeableLeatherItem {
    public LeatherGlovesItem(double punchDamage, Properties properties) {
        super(punchDamage, "leather_gloves", () -> SoundEvents.ARMOR_EQUIP_LEATHER, properties);
    }
}
