package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.serverbound.HittingPacket;
import com.aetherteam.aether.network.packet.serverbound.JumpPacket;
import com.aetherteam.aether.network.packet.serverbound.MovementPacket;
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
                    if (input == AetherKeys.GRAVITITE_JUMP_ABILITY.getKey().getValue()) {
                        aetherPlayer.setGravititeJumpActive(AetherKeys.GRAVITITE_JUMP_ABILITY.isDown());
                    }
                });
            }
        }
    }
}
