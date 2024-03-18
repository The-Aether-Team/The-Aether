package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.capability.time.AetherTime;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CapabilityHooks {
    public static class AetherPlayerHooks {
        /**
         * @see AetherPlayerCapability#onLogin()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogin(Player)
         */
        public static void login(Player player) {
            AetherPlayer.getOptional(player).ifPresent(AetherPlayer::onLogin);
        }

        /**
         * @see AetherPlayerCapability#onLogout()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogout(Player)
         */
        public static void logout(Player player) {
            AetherPlayer.getOptional(player).ifPresent(AetherPlayer::onLogout);
        }

        /**
         * @see AetherPlayerCapability#onJoinLevel()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerJoinLevel(Entity, Level, boolean)
         */
        public static void joinLevel(Entity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.getOptional(player).ifPresent(AetherPlayer::onJoinLevel);
            }
        }

        /**
         * @see AetherPlayerCapability#onUpdate()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerUpdate(LivingEntityEvents.LivingTickEvent)
         */
        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.getOptional(player).ifPresent(AetherPlayer::onUpdate);
            }
        }

        /**
         * @see AetherPlayerCapability#clone()
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerClone(ServerPlayer, ServerPlayer, boolean)
         */
        public static void clone(Player originalPlayer, Player newPlayer, boolean wasDeath) {
            AetherPlayer originalAetherPlayer = AetherPlayer.getOptional(originalPlayer).orElseThrow(() -> new IllegalStateException("Player " + originalPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            AetherPlayer newAetherPlayer = AetherPlayer.getOptional(newPlayer).orElseThrow(() -> new IllegalStateException("Player " + newPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            newAetherPlayer.copyFrom(originalAetherPlayer, wasDeath);
        }

        /**
         * Syncs capability data to the client when the player changes dimensions.
         * @param player The {@link Player}.
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerChangeDimension(ServerPlayer, ServerLevel, ServerLevel)
         */
        public static void changeDimension(Player player) {
            if (!player.level().isClientSide()) {
                if (AetherPlayer.get(player) instanceof AetherPlayerCapability capability) {
                    capability.forceSync(INBTSynchable.Direction.CLIENT);
                }
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
                if (player.level().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
                    AetherTime.get(serverPlayer.level()).ifPresent((aetherTime) -> aetherTime.updateEternalDay(serverPlayer));
                }
            }
        }
    }
}
