package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.client.AetherKeys;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;

public class CapabilityClientHooks {
    public static class AetherPlayerHooks {
        /**
         * Tracks whether the player is jumping or moving on the client to the {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
         * @param player The {@link Player}.
         * @param input The {@link Input}.
         * @see com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener#onMove(MovementInputUpdateEvent)
         */
        public static void movementInput(Player player, Input input) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                boolean isJumping = input.jumping;
                if (isJumping != aetherPlayer.isJumping()) {
                    aetherPlayer.setSynched(INBTSynchable.Direction.SERVER, "setJumping", isJumping);
                }
                boolean isMoving = isJumping || input.up || input.down || input.left || input.right || player.isFallFlying();
                if (isMoving != aetherPlayer.isMoving()) {
                    aetherPlayer.setSynched(INBTSynchable.Direction.SERVER, "setMoving", isMoving);
                }
            });
        }

        /**
         * Checks for mouse input.
         * @param button The {@link Integer} ID for the button.
         * @see com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener#onClick(InputEvent.MouseButton.Post)
         */
        public static void mouseInput(int button) {
            checkHit(button);
            checkJumpAbility(button);
        }

        /**
         * Checks for key input.
         * @param key The {@link Integer} ID for the key.
         * @see com.aetherteam.aether.client.event.listeners.capability.AetherPlayerClientListener#onPress(InputEvent.Key)
         */
        public static void keyInput(int key) {
            checkHit(key);
            checkJumpAbility(key);
        }

        /**
         * Checks whether the player is hitting, and tracks that to the {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
         * @param input The {@link Integer} for the ID of the input.
         */
        private static void checkHit(int input) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    boolean isAttack = input == Minecraft.getInstance().options.keyAttack.getKey().getValue();
                    boolean isPressing = Minecraft.getInstance().options.keyAttack.isDown();
                    boolean isHitting = isAttack && isPressing;
                    aetherPlayer.setSynched(INBTSynchable.Direction.SERVER, "setHitting", isHitting);
                });
            }
        }

        /**
         * Checks whether the player is pressing the {@link AetherKeys#GRAVITITE_JUMP_ABILITY} key, and tracks that to the {@link com.aetherteam.aether.capability.player.AetherPlayerCapability}.
         * @param input The {@link Integer} for the ID of the input.
         */
        private static void checkJumpAbility(int input) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                AetherPlayer.get(player).ifPresent((aetherPlayer) -> {
                    if (input == AetherKeys.GRAVITITE_JUMP_ABILITY.getKey().getValue()) {
                        aetherPlayer.setSynched(INBTSynchable.Direction.SERVER, "setGravititeJumpActive", AetherKeys.GRAVITITE_JUMP_ABILITY.isDown());
                    }
                });
            }
        }
    }
}
