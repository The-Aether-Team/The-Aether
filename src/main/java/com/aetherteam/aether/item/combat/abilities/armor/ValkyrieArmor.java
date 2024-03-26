package com.aetherteam.aether.item.combat.abilities.armor;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.aether.mixin.mixins.common.accessor.ServerGamePacketListenerImplAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public interface ValkyrieArmor {
    /**
     * Allows an entity temporary upwards flight if wearing a full set of Valkyrie Armor. This only works for players.
     *
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityUpdate(LivingEvent.LivingTickEvent)
     */
    static void handleFlight(LivingEntity entity) {
        if (EquipmentUtil.hasFullValkyrieSet(entity)) {
            if (entity instanceof Player player && !player.getAbilities().flying) { // The player can't have creative flight enabled, otherwise it causes issues.
                var data = player.getData(AetherDataAttachments.AETHER_PLAYER);
                Vec3 deltaMovement = player.getDeltaMovement();
                // Updates the flight modifier and timer values.
                if (data.isJumping() && !onGround(player)) { // Checks if the player is off the ground and holding the jump key (space bar by default).
                    if (data.getFlightModifier() >= data.getFlightModifierMax()) { // Limits the flight modifier to a maximum value.
                        data.setFlightModifier(data.getFlightModifierMax());
                    }
                    if (data.getFlightTimer() > 2) { // Starts allowing the player to fly after a 2 tick delay of being off the ground.
                        if (data.getFlightTimer() < data.getFlightTimerMax()) { // Allows the player to fly until the maximum value is hit.
                            data.setFlightModifier(data.getFlightModifier() + 0.25F);
                            data.setFlightTimer(data.getFlightTimer() + 1);
                        }
                    } else {
                        data.setFlightTimer(data.getFlightTimer() + 1);
                    }
                } else if (!data.isJumping()) {
                    // Resets only the modifier if the player stops holding the jump key midair. The timer doesn't reset though and remains frozen, and will continue where it left off when the key is held again, preventing infinite flight.
                    data.setFlightModifier(1.0F);
                }
                if (onGround(player)) { // Resets both timer and modifier if the player is on the ground.
                    data.setFlightTimer(0);
                    data.setFlightModifier(1.0F);
                }
                // Modifies the player's upwards movement based on the set flight modifier and timer values.
                if (data.isJumping() && !onGround(player) && data.getFlightTimer() > 2 && data.getFlightTimer() < data.getFlightTimerMax() && data.getFlightModifier() > 1.0F) {
                    player.setDeltaMovement(deltaMovement.x(), 0.025F * data.getFlightModifier(), deltaMovement.z());
                }
                if (player instanceof ServerPlayer serverPlayer) { // Prevents the player from being kicked for flying.
                    ServerGamePacketListenerImplAccessor serverGamePacketListenerImplAccessor = (ServerGamePacketListenerImplAccessor) serverPlayer.connection;
                    serverGamePacketListenerImplAccessor.aether$setAboveGroundTickCount(0);
                }
            }
        }
    }

    private static boolean onGround(Player player) {
        return player.onGround() || player.isInFluidType();
    }
}
