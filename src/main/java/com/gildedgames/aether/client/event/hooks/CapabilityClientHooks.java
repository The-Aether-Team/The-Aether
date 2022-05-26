package com.gildedgames.aether.client.event.hooks;

import com.gildedgames.aether.client.registry.AetherKeys;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.network.AetherPacketHandler;
import com.gildedgames.aether.core.network.packet.server.HittingPacket;
import com.gildedgames.aether.core.network.packet.server.JumpPacket;
import com.gildedgames.aether.core.network.packet.server.MovementPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;

public class CapabilityClientHooks {
    public static class AetherPlayerHooks {
        public static void movementInput(Player player, Input input) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                boolean isJumping = input.jumping;
                if (isJumping != aetherPlayer.isJumping()) {
                    AetherPacketHandler.sendToServer(new JumpPacket(player.getId(), isJumping));
                    aetherPlayer.setJumping(isJumping);
                }
                boolean isMoving = isJumping || input.up || input.down || input.left || input.right || player.isFallFlying();
                if (isMoving != aetherPlayer.isMoving()) {
                    AetherPacketHandler.sendToServer(new MovementPacket(player.getId(), isMoving));
                    aetherPlayer.setMoving(isMoving);
                }
            });
        }

        public static void mouseInput(int button) {
            checkHit(button);
            checkJumpAbility(button);
        }

        public static void keyInput(int key) {
            checkHit(key);
            checkJumpAbility(key);
        }

        private static void checkHit(int input) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    boolean isAttack = input == Minecraft.getInstance().options.keyAttack.getKey().getValue();
                    boolean isPressing = Minecraft.getInstance().options.keyAttack.isDown();
                    boolean isHitting = isAttack && isPressing;
                    AetherPacketHandler.sendToServer(new HittingPacket(player.getId(), isHitting));
                    aetherPlayer.setHitting(isHitting);
                });
            }
        }

        private static void checkJumpAbility(int input) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    if (input == AetherKeys.gravititeJumpAbility.getKey().getValue()) {
                        aetherPlayer.setGravititeJumpActive(AetherKeys.gravititeJumpAbility.isDown());
                    }
                });
            }
        }
    }
}
