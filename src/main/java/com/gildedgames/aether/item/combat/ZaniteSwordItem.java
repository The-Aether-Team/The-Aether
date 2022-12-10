package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.combat.abilities.weapon.ZaniteWeapon;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class ZaniteSwordItem extends SwordItem implements ZaniteWeapon {
    public ZaniteSwordItem() {
        super(AetherItemTiers.ZANITE, 3, -2.4F, new Item.Properties());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return this.increaseDamage(super.getAttributeModifiers(slot, stack), stack, slot);
    }
}
