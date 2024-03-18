package com.aetherteam.aether.item.combat.abilities.armor;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.item.EquipmentUtil;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface GravititeArmor {
    /**
     * Launches the entity if wearing a full set of Gravitite armor ({@link EquipmentUtil#hasFullGravititeSet(LivingEntity)}).<br><br>
     * If they're a player, there are additional checks for if the Gravitite jump ability is active. This is dependent on {@link AetherPlayerCapability#isGravititeJumpActive()},
     * which is set by {@link com.aetherteam.aether.client.event.hooks.CapabilityClientHooks.AetherPlayerHooks} if the player is holding down the {@link com.aetherteam.aether.client.AetherKeys#GRAVITITE_JUMP_ABILITY} key (this is by default {@link org.lwjgl.glfw.GLFW#GLFW_KEY_LEFT_SHIFT}).
     * @param entity The {@link LivingEntity} wearing the armor.
     * @see com.aetherteam.aether.event.listeners.abilities.ArmorAbilityListener#onEntityJump(LivingEntityEvents.LivingJumpEvent)
     */
    static void boostedJump(LivingEntity entity) {
        if (EquipmentUtil.hasFullGravititeSet(entity)) {
            if (entity instanceof Player player) {
                AetherPlayer.getOptional(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer.getPlayer().onGround() && aetherPlayer.isGravititeJumpActive()) {
                        aetherPlayer.getPlayer().push(0.0, 1.0, 0.0);
                        if (aetherPlayer.getPlayer() instanceof ServerPlayer serverPlayer) {
                            serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
                        }
                    }
                });
            } else {
                entity.push(0.0, 1.0, 0.0);
            }
        }
    }
}
