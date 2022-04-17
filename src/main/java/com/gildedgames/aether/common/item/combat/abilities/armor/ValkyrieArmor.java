package com.gildedgames.aether.common.item.combat.abilities.armor;

import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.util.EquipmentUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface ValkyrieArmor {
    static void handleFlight(LivingEntity entity) {
        if (EquipmentUtil.hasFullValkyrieSet(entity)) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    Vec3 deltaMovement = player.getDeltaMovement();
                    if (!player.level.isClientSide) {
                        if (aetherPlayer.isJumping()) {
                            if (aetherPlayer.getFlightModifier() >= aetherPlayer.getFlightModifierMax()) {
                                aetherPlayer.setFlightModifier(aetherPlayer.getFlightModifierMax());
                            }
                            if (aetherPlayer.getFlightTimer() > 2) {
                                if (aetherPlayer.getFlightTimer() < aetherPlayer.getFlightTimerMax()) {
                                    aetherPlayer.setFlightModifier(aetherPlayer.getFlightModifier() + 0.25F);
                                    aetherPlayer.setFlightTimer(aetherPlayer.getFlightTimer() + 1);
                                }
                            } else {
                                aetherPlayer.setFlightTimer(aetherPlayer.getFlightTimer() + 1);
                            }
                        } else {
                            aetherPlayer.setFlightModifier(1.0F);
                        }
                        if (player.isOnGround()) {
                            aetherPlayer.setFlightTimer(0);
                            aetherPlayer.setFlightModifier(1.0F);
                        }
                    }
                    if (aetherPlayer.isJumping() && aetherPlayer.getFlightTimer() > 2 && aetherPlayer.getFlightTimer() < aetherPlayer.getFlightTimerMax()) {
                        player.setDeltaMovement(deltaMovement.x(), 0.025F * aetherPlayer.getFlightModifier(), deltaMovement.z());
                    }
                });
            }
        }
    }
}
