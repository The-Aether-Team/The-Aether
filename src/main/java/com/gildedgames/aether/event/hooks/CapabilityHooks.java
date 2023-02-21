package com.gildedgames.aether.event.hooks;

import com.gildedgames.aether.api.CustomizationsOptions;
import com.gildedgames.aether.data.resources.registries.AetherDimensions;
import com.gildedgames.aether.capability.player.AetherPlayer;
import com.gildedgames.aether.capability.player.AetherPlayerCapability;
import com.gildedgames.aether.capability.rankings.AetherRankings;
import com.gildedgames.aether.capability.rankings.AetherRankingsCapability;
import com.gildedgames.aether.capability.time.AetherTime;
import com.gildedgames.aether.network.AetherPacketHandler;
import com.gildedgames.aether.network.packet.server.RankingsForcePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class CapabilityHooks {
    public static class AetherPlayerHooks {
        public static void login(Player player) {
            AetherPlayer.get(player).ifPresent(AetherPlayer::onLogin);
        }

        public static void logout(Player player) {
            AetherPlayer.get(player).ifPresent((aetherPlayer) ->  {
                aetherPlayer.onLogout();
                AttributeInstance health = player.getAttribute(Attributes.MAX_HEALTH);
                if (health != null && health.hasModifier(aetherPlayer.getLifeShardHealthAttributeModifier())) {
                    aetherPlayer.setSavedHealth(player.getHealth());
                }
            });
        }

        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(AetherPlayer::onUpdate);
            }
        }

        public static void clone(Player originalPlayer, Player newPlayer, boolean wasDeath) {
            originalPlayer.reviveCaps();
            AetherPlayer originalAetherPlayer = AetherPlayer.get(originalPlayer).orElseThrow(
                    () -> new IllegalStateException("Player " + originalPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            AetherPlayer newAetherPlayer = AetherPlayer.get(newPlayer).orElseThrow(
                    () -> new IllegalStateException("Player " + newPlayer.getName().getContents() + " has no AetherPlayer capability!"));
            newAetherPlayer.copyFrom(originalAetherPlayer, wasDeath);

            if (!wasDeath) {
                newAetherPlayer.setSavedHealth(originalAetherPlayer.getPlayer().getHealth());
            } else {
                newAetherPlayer.setSavedHealth(1024.0F); //Max health.
            }
            originalPlayer.invalidateCaps();
        }

        public static void changeDimension(Player player) {
            if (!player.level.isClientSide()) {
                AetherPlayer.get(player).ifPresent(aetherPlayer -> {
                    if (aetherPlayer instanceof AetherPlayerCapability capability) {
                        capability.markForced(true);
                        capability.updateSyncableNBTFromServer(player.level);
                    }
                });
            }
        }
    }

    public static class AetherRankingsHooks {
        public static void join(Entity entity) {
            if (entity instanceof Player player && player.level.isClientSide()) {
                CustomizationsOptions.INSTANCE.load();
                CustomizationsOptions.INSTANCE.sync();
                AetherPacketHandler.sendToServer(new RankingsForcePacket(player.getId()));
            }
        }

        public static void update(LivingEntity entity) {
            if (entity instanceof Player player) {
                AetherRankings.get(player).ifPresent(AetherRankings::onUpdate);
            }
        }

        public static void clone(Player originalPlayer, Player newPlayer) {
            originalPlayer.reviveCaps();
            AetherRankings originalAetherRankings = AetherRankings.get(originalPlayer).orElseThrow(
                    () -> new IllegalStateException("Player " + originalPlayer.getName().getContents() + " has no AetherRankings capability!"));
            AetherRankings newAetherRankings = AetherRankings.get(newPlayer).orElseThrow(
                    () -> new IllegalStateException("Player " + newPlayer.getName().getContents() + " has no AetherRankings capability!"));
            newAetherRankings.copyFrom(originalAetherRankings);
            originalPlayer.invalidateCaps();
        }

        public static void changeDimension(Player player) {
            if (!player.level.isClientSide()) {
                AetherRankings.get(player).ifPresent(aetherRankings -> {
                    if (aetherRankings instanceof AetherRankingsCapability capability) {
                        capability.markForced(true);
                        capability.updateSyncableNBTFromServer(player.level);
                    }
                });
            }
        }
    }

    public static class AetherTimeHooks {
        public static void login(Player player) {
            syncAetherTime(player);
        }

        public static void changeDimension(Player player) {
            syncAetherTime(player);
        }

        public static void respawn(Player player) {
            syncAetherTime(player);
        }

        private static void syncAetherTime(Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                if (player.level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
                    AetherTime.get(serverPlayer.level).ifPresent(cap -> cap.updateEternalDay(serverPlayer));
                }
            }
        }
    }
}
