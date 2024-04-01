package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.attachment.AetherDataAttachments;
import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CapabilityHooks {
    public static class AetherPlayerHooks {
        /**
         * @see AetherPlayerAttachment#onLogin(Player)
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogin(PlayerEvent.PlayerLoggedInEvent)
         */
        public static void login(Player player) {
            player.getData(AetherDataAttachments.AETHER_PLAYER).onLogin(player);
        }

        /**
         * @see AetherPlayerAttachment#onLogout(Player)
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent)
         */
        public static void logout(Player player) {
            player.getData(AetherDataAttachments.AETHER_PLAYER).onLogout(player);
        }

        /**
         * @see AetherPlayerAttachment#onJoinLevel(Player)
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerJoinLevel(EntityJoinLevelEvent)
         */
        public static void joinLevel(Entity entity) {
            if (entity instanceof Player player) {
                player.getData(AetherDataAttachments.AETHER_PLAYER).onJoinLevel(player);
            }
        }

        /**
         * @see AetherPlayerAttachment#onUpdate(Player)
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerUpdate(LivingEvent.LivingTickEvent)
         */
        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                player.getData(AetherDataAttachments.AETHER_PLAYER).onUpdate(player);
            }
        }

        /**
         * @see AetherPlayerAttachment#handleRespawn(boolean)
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerClone(PlayerEvent.Clone)
         */
        public static void clone(Player player, boolean wasDeath) {
            player.getData(AetherDataAttachments.AETHER_PLAYER).handleRespawn(wasDeath);
        }

        /**
         * Syncs capability data to the client when the player changes dimensions.
         *
         * @param player The {@link Player}.
         * @see com.aetherteam.aether.event.listeners.capability.AetherPlayerListener#onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent)
         */
        public static void changeDimension(Player player) {
            if (!player.level().isClientSide()) {
                player.getData(AetherDataAttachments.AETHER_PLAYER).forceSync(player.getId(), INBTSynchable.Direction.CLIENT);
            }
        }
    }

    public static class AetherTimeHooks {
        /**
         * Sync the Aether's time to the player on login.
         *
         * @param player The {@link Player}.
         */
        public static void login(Player player) {
            syncAetherTime(player);
        }

        /**
         * Sync the Aether's time to the player on dimension change.
         *
         * @param player The {@link Player}.
         */
        public static void changeDimension(Player player) {
            syncAetherTime(player);
        }

        /**
         * Sync the Aether's time to the player on respawn.
         *
         * @param player The {@link Player}.
         */
        public static void respawn(Player player) {
            syncAetherTime(player);
        }

        /**
         * Sync the Aether's time to the player.
         *
         * @param player The {@link Player}.
         */
        private static void syncAetherTime(Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.level().dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
                    player.level().getData(AetherDataAttachments.AETHER_TIME).updateEternalDay(serverPlayer);
                }
            }
        }
    }
}
