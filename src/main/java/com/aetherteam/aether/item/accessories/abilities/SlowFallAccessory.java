package com.aetherteam.aether.item.accessories.abilities;

import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public interface SlowFallAccessory {
    /**
     * Slows the wearer's fall by multiplying their vertical motion by 0.6 if the wearer is falling (vertical motion less than 0).<br><br>
     * The slow falling also depends on if the wearer is off the ground, if they are not fall flying (using an Elytra), if they aren't in a liquid, if they aren't holding shift, and if their gravity value is greater than 0.0075 (to avoid any permanent floating bugs or exploits).<br><br>
     * Also resets the wearer's fall distance.
     * @param livingEntity The {@link LivingEntity} wearing the accessory.
     */
    default void handleSlowFall(LivingEntity livingEntity) {
        AttributeInstance gravity = livingEntity.getAttribute(net.neoforged.neoforge.common.NeoForgeMod.ENTITY_GRAVITY.value());
        if (gravity != null) {
            if (livingEntity.getDeltaMovement().y() <= -0.06 && !livingEntity.onGround() && !livingEntity.isFallFlying() && !livingEntity.isInFluidType() && !livingEntity.isShiftKeyDown() && gravity.getValue() > 0.0075) {
                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1.0, 0.6, 1.0));
            }
        }
        livingEntity.checkSlowFallDistance();
        if (livingEntity instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
            ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
            serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
        }
    }
}
