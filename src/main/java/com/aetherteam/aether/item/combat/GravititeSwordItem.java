package com.aetherteam.aether.item.combat;

import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.combat.abilities.weapon.GravititeWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

public class GravititeSwordItem extends SwordItem implements GravititeWeapon {
    public GravititeSwordItem() {
        super(AetherItemTiers.GRAVITITE, 3, -2.4F, new Item.Properties().tab(AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        this.launchEntity(target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
