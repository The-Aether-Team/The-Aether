package com.gildedgames.aether.common.item.accessories.abilities;

import net.minecraft.world.entity.LivingEntity;

public interface SlowFallAccessory
{
    default void handleSlowFall(LivingEntity livingEntity) {
        if (livingEntity.getDeltaMovement().y < 0.0 && !livingEntity.isOnGround() && !livingEntity.isInWater() && !livingEntity.isShiftKeyDown()) {
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
        livingEntity.fallDistance *= 0.0F;
    }
}
