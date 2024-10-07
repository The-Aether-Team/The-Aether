package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.combat.abilities.weapon.GravititeWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class GravititeSwordItem extends SwordItem implements GravititeWeapon {
    public GravititeSwordItem() {
        super(AetherItemTiers.GRAVITITE, new Item.Properties().attributes(SwordItem.createAttributes(AetherItemTiers.GRAVITITE, 3.0F, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.launchEntity(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
