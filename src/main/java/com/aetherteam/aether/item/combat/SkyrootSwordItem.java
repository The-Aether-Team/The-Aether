package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.SkyrootWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class SkyrootSwordItem extends SwordItem implements SkyrootWeapon {
    public SkyrootSwordItem() {
        super(AetherItemTiers.SKYROOT, new Item.Properties().attributes(SwordItem.createAttributes(AetherItemTiers.SKYROOT, 3.0F, -2.4F)));
    }
}
