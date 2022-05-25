package com.gildedgames.aether.client.gui.screen.menu;

import com.gildedgames.aether.client.event.hooks.GuiHooks;
import com.gildedgames.aether.core.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AetherWorldDisplayHelper {
    public static Level loadedLevel = null;
    public static LevelSummary loadedSummary = null;

    public static GameType loadedGameMode = null;
    public static Vec3 loadedPosition = null;
    public static GameRules loadedGameRules = null;

    public static void toggleWorldPreview(boolean config) {
        if (config) {
            enableWorldPreview();
        } else {
            if (disableWorldPreview(new GenericDirtMessageScreen(new TextComponent("")))) {
                Minecraft.getInstance().forceSetScreen(GuiHooks.getMenu());
            }
        }
    }

    public static void enableWorldPreview() {
        Minecraft minecraft = Minecraft.getInstance();
        if (AetherConfig.CLIENT.enable_world_preview.get()) {
            if (loadedLevel == null) {
                LevelStorageSource source = minecraft.getLevelSource();
                try {
                    List<LevelSummary> summaryList = source.getLevelList();
                    Collections.sort(summaryList);
                    if (summaryList.size() > 0) {
                        LevelSummary summary = null;

                        for (int i = summaryList.size() - 1; i >= 0; i--) {
                            LevelSummary s = summaryList.get(i);
                            if (!s.isLocked() && !s.isDisabled()) {
                                summary = s;
                            }
                        }

                        if (summary != null) {
                            loadedSummary = summary;
                            loadWorld(summary);
                        }
                    }
                } catch (LevelStorageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean disableWorldPreview(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        if (loadedLevel != null) {
            AetherConfig.CLIENT.enable_world_preview.set(false);
            AetherConfig.CLIENT.enable_world_preview.save();
            stopWorld(minecraft, screen);
            return true;
        }
        return false;
    }

    public static void loadWorld(LevelSummary summary) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getLevelSource().levelExists(summary.getLevelId())) {
            minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
            loadedSummary = summary;
            minecraft.loadLevel(summary.getLevelId());
        }
    }

    public static void setupLevelForDisplay() {
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            loadedLevel = minecraft.level;

            ServerPlayer serverPlayer = minecraft.getSingleplayerServer().getPlayerList().getPlayers().get(0);
            GameRules gameRules = server.getGameRules();

            loadedGameMode = serverPlayer.gameMode.getGameModeForPlayer();
            loadedGameRules = new GameRules();
            loadedGameRules.assignFrom(gameRules, server);
            loadedPosition = serverPlayer.position();

            serverPlayer.setGameMode(GameType.SPECTATOR);
            gameRules.getRule(GameRules.RULE_MOBGRIEFING).set(false, server);
            gameRules.getRule(GameRules.RULE_DOMOBSPAWNING).set(false, server);
            gameRules.getRule(GameRules.RULE_RANDOMTICKING).set(0, server);
            serverPlayer.setPos(loadedPosition);

            deleteSessionLock();
        }
    }

    public static void quickLoad() {
        if (loadedSummary != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (minecraft.getLevelSource().levelExists(loadedSummary.getLevelId()) && minecraft.getSingleplayerServer() != null ) {
                openSessionLock();
                ServerPlayer player = minecraft.getSingleplayerServer().getPlayerList().getPlayers().get(0);
                ServerLevel level = (ServerLevel) player.getCommandSenderWorld();
                resetValues(player, level);
                loadedLevel = null;
                loadedSummary = null;
                minecraft.forceSetScreen(null);
            }
        }
    }

    public static void stopWorld(Minecraft minecraft, Screen screen) {
        fixWorld();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            server.halt(false);
        }
        if (screen != null) {
            minecraft.clearLevel(screen);
        } else {
            minecraft.clearLevel();
        }
    }

    public static void fixWorld() {
        loadedLevel = null;
        loadedSummary = null;
        IntegratedServer server = Minecraft.getInstance().getSingleplayerServer();
        if (server != null) {
            ServerPlayer player = server.getPlayerList().getPlayers().get(0);
            ServerLevel level = (ServerLevel) player.getCommandSenderWorld();
            resetValues(player, level);
        }
    }

    public static void resetValues(ServerPlayer player, ServerLevel level) {
        if (loadedGameMode != null) {
            player.setGameMode(loadedGameMode);
            loadedGameMode = null;
        }
        if (loadedGameRules != null) {
            level.getGameRules().assignFrom(loadedGameRules, player.getServer());
            loadedGameRules = null;
        }
        if (loadedPosition != null) {
            player.setPos(loadedPosition);
            loadedPosition = null;
        }
    }

    public static void deleteSessionLock() {
        try {
            LevelStorageSource.LevelStorageAccess storageAccess = getStorageAccess();
            if (storageAccess != null) {
                storageAccess.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openSessionLock() {
        try {
            LevelStorageSource.LevelStorageAccess storageAccess = getStorageAccess();
            if (storageAccess != null) {
                storageAccess.lock = DirectoryLock.create(storageAccess.getWorldDir());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelStorageSource.LevelStorageAccess getStorageAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            return server.storageSource;
        } else {
            return null;
        }
    }
}
