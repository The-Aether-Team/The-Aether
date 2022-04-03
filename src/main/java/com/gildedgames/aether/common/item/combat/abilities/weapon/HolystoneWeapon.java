package com.gildedgames.aether.common.item.combat.abilities.weapon;

import com.gildedgames.aether.common.registry.AetherItems;
import com.gildedgames.aether.common.registry.AetherTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface HolystoneWeapon {
    static void dropAmbrosium(LivingEntity target, DamageSource damageSource) {
        if (damageSource != null && damageSource.getEntity() instanceof LivingEntity source) {
            ItemStack itemStack = source.getMainHandItem();
            if (itemStack.is(AetherTags.Items.HOLYSTONE_WEAPONS)) {
                if (!source.level.isClientSide && target.level.getRandom().nextInt(20) == 0) {
                    target.spawnAtLocation(AetherItems.AMBROSIUM_SHARD.get());
                }
            }
        }
    }
}
