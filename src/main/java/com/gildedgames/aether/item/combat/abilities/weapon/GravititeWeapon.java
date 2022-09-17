package com.gildedgames.aether.item.combat.abilities.weapon;

import com.gildedgames.aether.AetherTags;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface GravititeWeapon {
    static void launchEntity(LivingEntity target, DamageSource damageSource) {
        if (damageSource != null && damageSource.getEntity() instanceof LivingEntity source && !damageSource.isProjectile() && !damageSource.isExplosion() && !damageSource.isMagic()) {
            ItemStack itemStack = source.getMainHandItem();
            if (itemStack.is(AetherTags.Items.GRAVITITE_WEAPONS)) {
                target.push(0.0, 1.0, 0.0);
                if (target instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                }
            }
        }
    }
}
