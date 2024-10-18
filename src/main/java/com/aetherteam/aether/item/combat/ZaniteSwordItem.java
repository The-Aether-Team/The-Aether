package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.ZaniteWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class ZaniteSwordItem extends SwordItem implements ZaniteWeapon {
    public ZaniteSwordItem() {
        super(AetherItemTiers.ZANITE, new Item.Properties().attributes(SwordItem.createAttributes(AetherItemTiers.ZANITE, 3.0F, -2.4F)));
    }
}
