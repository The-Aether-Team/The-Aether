package com.gildedgames.aether.common.event.hooks;

import com.gildedgames.aether.common.registry.AetherTags;
import com.gildedgames.aether.core.capability.cape.CapeEntity;
import com.gildedgames.aether.core.capability.player.AetherPlayer;
import com.gildedgames.aether.core.capability.rankings.AetherRankings;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.util.AetherSleepStatus;
import com.gildedgames.aether.core.util.LevelUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

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
    }

    public static class AetherRankingsHooks {
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
    }

    public static class AetherTimeHooks {
        // TODO Rebuild into a Level set
        public static Level world;

        public static void login(Player player) {
            syncAetherTime(player);
        }

        public static void changeDimension(Player player) {
            syncAetherTime(player);
        }

        private static void syncAetherTime(Player player) {
            if (player != null && player.level instanceof ServerLevel world) {
                MinecraftServer server = world.getServer();
                for (ServerLevel serverLevel : server.getAllLevels()) {
                    if (LevelUtil.inTag(serverLevel, AetherTags.Dimensions.ETERNAL_DAY)) {
                        AetherTime.get(world).ifPresent(AetherTime::syncToClient);
                    }
                }
            }
        }

        public static void load(LevelAccessor accessor) {
            if (accessor instanceof Level level && LevelUtil.inTag(level, AetherTags.Dimensions.ETERNAL_DAY)) {
                world = level;
                if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                    serverLevel.sleepStatus = new AetherSleepStatus();
                }
            }
        }

        public static void unload(LevelAccessor accessor) {
            if (accessor instanceof Level level && LevelUtil.inTag(level, AetherTags.Dimensions.ETERNAL_DAY)) {
                world = null;
            }
        }

        public static void tick(Level level) {
            if (level instanceof ServerLevel serverLevel && LevelUtil.inTag(level, AetherTags.Dimensions.ETERNAL_DAY)) {
                AetherTime.get(serverLevel).ifPresent(aetherTime -> aetherTime.serverTick(serverLevel));
            }
        }
    }

    public static class CapeEntityHooks {
        public static void update(LivingEntity entity) {
            CapeEntity.get(entity).ifPresent(CapeEntity::onUpdate);
        }
    }
}
