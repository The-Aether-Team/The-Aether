package com.gildedgames.aether.item.combat.abilities.armor;

import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.mixin.mixins.accessor.ServerGamePacketListenerImplAccessor;
import com.gildedgames.aether.util.EquipmentUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public interface ValkyrieArmor {
    /**
     * Allows an entity temporary upwards flight if wearing a full set of Valkyrie Armor. This only works for players.
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.gildedgames.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void handleFlight(LivingEntity entity) {
        if (EquipmentUtil.hasFullValkyrieSet(entity)) {
            if (entity instanceof Player player && !player.getAbilities().flying) { // The player can't have creative flight enabled, otherwise it causes issues.
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    Vec3 deltaMovement = player.getDeltaMovement();
                    if (!player.getLevel().isClientSide()) { // Updates the flight modifier and timer values.
                        if (aetherPlayer.isJumping() && !player.isOnGround()) { // Checks if the player is off the ground and holding the jump key (space bar by default).
                            if (aetherPlayer.getFlightModifier() >= aetherPlayer.getFlightModifierMax()) { // Limits the flight modifier to a maximum value.
                                aetherPlayer.setFlightModifier(aetherPlayer.getFlightModifierMax());
                            }
                            if (aetherPlayer.getFlightTimer() > 2) { // Starts allowing the player to fly after a 2 tick delay of being off the ground.
                                if (aetherPlayer.getFlightTimer() < aetherPlayer.getFlightTimerMax()) { // Allows the player to fly until the maximum value is hit.
                                    aetherPlayer.setFlightModifier(aetherPlayer.getFlightModifier() + 0.25F);
                                    aetherPlayer.setFlightTimer(aetherPlayer.getFlightTimer() + 1);
                                }
                            } else {
                                aetherPlayer.setFlightTimer(aetherPlayer.getFlightTimer() + 1);
                            }
                        } else if (!aetherPlayer.isJumping()) {
                            // Resets only the modifier if the player stops holding the jump key midair. The timer doesn't reset though and remains frozen, and will continue where it left off when the key is held again, preventing infinite flight.
                            aetherPlayer.setFlightModifier(1.0F);
                        }
                        if (player.isOnGround()) { // Resets both timer and modifier if the player is on the ground.
                            aetherPlayer.setFlightTimer(0);
                            aetherPlayer.setFlightModifier(1.0F);
                        }
                    }
                    // Modifies the player's upwards movement based on the set flight modifier and timer values.
                    if (aetherPlayer.isJumping() && !player.isOnGround() && aetherPlayer.getFlightTimer() > 2 && aetherPlayer.getFlightTimer() < aetherPlayer.getFlightTimerMax() && aetherPlayer.getFlightModifier() > 1.0F) {
                        player.setDeltaMovement(deltaMovement.x(), 0.025F * aetherPlayer.getFlightModifier(), deltaMovement.z());
                    }
                    if (player instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                        ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                        serverGamePacketListenerImplAccessor.setAboveGroundTickCount(0);
                    }
                });
            }
        }
    }
}
