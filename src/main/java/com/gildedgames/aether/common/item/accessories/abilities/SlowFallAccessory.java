package com.gildedgames.aether.common.item.accessories.abilities;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public interface SlowFallAccessory {
    default void handleSlowFall(LivingEntity livingEntity) {
        AttributeInstance gravity = livingEntity.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            if (livingEntity.getDeltaMovement().y() <= 0.0 && !livingEntity.isOnGround() && !livingEntity.isFallFlying() && !livingEntity.isInWater() && !livingEntity.isInLava() && !livingEntity.isShiftKeyDown() && gravity.getValue() > 0.0075) {
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1.0, 0.6, 1.0));
            }
        }
        livingEntity.resetFallDistance();
    }
}
