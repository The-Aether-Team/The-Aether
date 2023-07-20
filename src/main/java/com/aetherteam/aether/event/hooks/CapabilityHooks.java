package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CapabilityHooks {
    public static class AetherPlayerHooks {
        /**
         * @see AetherPlayerCapability#onLogin()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogin(PlayerEvent.PlayerLoggedInEvent)
         */
        public static void login(Player player) {
            AetherPlayer.get(player).ifPresent(AetherPlayer::onLogin);
        }

        /**
         * @see AetherPlayerCapability#onLogout()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent)
         */
        public static void logout(Player player) {
            AetherPlayer.get(player).ifPresent(AetherPlayer::onLogout);
        }

        /**
         * @see AetherPlayerCapability#onJoinLevel()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerJoinLevel(EntityJoinLevelEvent)
         */
        public static void joinLevel(Entity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(AetherPlayer::onJoinLevel);
            }
        }

        /**
         * @see AetherPlayerCapability#onUpdate()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerUpdate(LivingEvent.LivingTickEvent)
         */
        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(AetherPlayer::onUpdate);
            }
        }

        /**
         * @see AetherPlayerCapability#clone()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerClone(PlayerEvent.Clone)
         */
        public static void clone(Player originalPlayer, Player newPlayer, boolean wasDeath) {
            originalPlayer.reviveCaps();
            AetherPlayer originalAetherPlayer = AetherPlayer.get(originalPlayer).orElseThrow(() -> new IllegalStateException("Player " + originalPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            AetherPlayer newAetherPlayer = AetherPlayer.get(newPlayer).orElseThrow(() -> new IllegalStateException("Player " + newPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            newAetherPlayer.copyFrom(originalAetherPlayer, wasDeath);
            originalPlayer.invalidateCaps();
        }

        /**
         * Syncs capability data to the client when the player changes dimensions.
         * @param player The {@link Player}.
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent)
         */
        public static void changeDimension(Player player) {
            if (!player.getLevel().isClientSide()) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer instanceof AetherPlayerCapability capability) {
                        capability.forceSync(INBTSynchable.Direction.CLIENT);
                    }
                });
            }
        }
    }

    public static class AetherTimeHooks {
        /**
         * Sync the Aether's time to the player on login.
         * @param player The {@link Player}.
         */
        public static void login(Player player) {
            syncAetherTime(player);
        }

        /**
         * Sync the Aether's time to the player on dimension change.
         * @param player The {@link Player}.
         */
        public static void changeDimension(Player player) {
            syncAetherTime(player);
        }
        /**
         * Sync the Aether's time to the player on respawn.
         * @param player The {@link Player}.
         */
        public static void respawn(Player player) {
            syncAetherTime(player);
        }

        /**
         * Sync the Aether's time to the player.
         * @param player The {@link Player}.
         */
        private static void syncAetherTime(Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.getLevel().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
                    AetherTime.get(serverPlayer.getLevel()).ifPresent((aetherTime) -> aetherTime.updateEternalDay(serverPlayer));
                }
            }
        }
    }
}
