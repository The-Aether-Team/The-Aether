package com.aetherteam.aether.event.hooks;

import com.aetherteam.aether.capability.INBTSynchable;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.capability.player.AetherPlayerCapability;
import com.aetherteam.aether.capability.time.AetherTime;
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

        public static void joinLevel(Entity entity) {
            if (entity instanceof Player player) {
                AetherPlayer.get(player).ifPresent(AetherPlayer::onJoinLevel);
            }
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
