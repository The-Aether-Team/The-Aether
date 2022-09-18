package com.gildedgames.aether.item.combat;

import com.gildedgames.aether.item.AetherItemGroups;
import com.gildedgames.aether.item.combat.abilities.weapon.GravititeWeapon;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import javax.annotation.Nonnull;

public class GravititeSword extends SwordItem implements GravititeWeapon {
    public GravititeSword() {
        super(AetherItemTiers.GRAVITITE, 3, -2.4F, new Item.Properties().tab(AetherItemGroups.AETHER_WEAPONS));
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        this.launchEntity(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
