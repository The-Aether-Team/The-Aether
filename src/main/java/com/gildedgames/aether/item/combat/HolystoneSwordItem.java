package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.combat.abilities.weapon.HolystoneWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class HolystoneSwordItem extends SwordItem implements HolystoneWeapon {
    public HolystoneSwordItem() {
        super(AetherItemTiers.HOLYSTONE, 3, -2.4F, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.dropAmbrosium(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
