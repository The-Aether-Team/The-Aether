package com.aetherteam.aether.client;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.mixin.mixins.common.accessor.LevelStorageAccessAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.MinecraftServerAccessor;
import com.aetherteam.cumulus.client.CumulusClient;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorldDisplayHelper {
    public static boolean menuActive = false;
    private static LevelSummary loadedSummary = null;

    public static void toggleWorldPreview() {
        if (AetherConfig.CLIENT.enable_world_preview.get()) {
            enableWorldPreview();
        } else {
            disableWorldPreview();
        }
    }

    public static void enableWorldPreview() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            loadLevel();
        }
    }

    public static void loadLevel() {
        Minecraft minecraft = Minecraft.getInstance();
        LevelSummary summary = getLevelSummary();
        if (summary != null && minecraft.getLevelSource().levelExists(summary.getLevelId())) {
            setActive();
            minecraft.forceSetScreen(new GenericDirtMessageScreen(Component.translatable("selectWorld.data_read")));
            minecraft.createWorldOpenFlows().loadLevel(minecraft.screen, summary.getLevelId());
        } else {
            resetHelperState();
            resetConfig();
        }
    }

    public static void enterLoadedLevel() {
        Minecraft minecraft = Minecraft.getInstance();
        LevelSummary summary = getLevelSummary();
        if (summary != null && minecraft.getLevelSource().levelExists(summary.getLevelId()) && minecraft.getSingleplayerServer() != null) {
            openSessionLock();
            resetStates();
            minecraft.forceSetScreen(null);
        }
    }

    public static void disableWorldPreview() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null) {
            stopLevel(new GenericDirtMessageScreen(Component.literal("")));
            setMenu();
        }
    }

    public static void stopLevel(Screen screen) {
        resetStates();
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (minecraft.level != null) {
            if (server != null) {
                server.halt(false);
            }
            if (screen != null) {
                minecraft.clearLevel(screen);
            } else {
                minecraft.clearLevel();
            }
        }
    }

    public static void setMenu() {
        CumulusClient.MENU_HELPER.setShouldFade(false);
        Screen screen = CumulusClient.MENU_HELPER.applyMenu(CumulusClient.MENU_HELPER.getActiveMenu());
        if (screen != null) {
            Minecraft.getInstance().forceSetScreen(screen);
        }
    }

    public static LevelSummary getLevelSummary() {
        if (loadedSummary == null) {
            findLevelSummary();
        }
        return loadedSummary;
    }

    public static void findLevelSummary() {
        Minecraft minecraft = Minecraft.getInstance();
        LevelStorageSource source = minecraft.getLevelSource();
        try {
            List<LevelSummary> summaryList = new ArrayList<>(source.loadLevelSummaries(source.findLevelCandidates()).get());
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
                }
            }
        } catch (ExecutionException | InterruptedException | UnsupportedOperationException e) {
            resetHelperState();
            resetConfig();
            e.printStackTrace();
        }
    }

    public static boolean sameSummaries(LevelSummary summary) {
        return getLevelSummary().getLevelId().equals(summary.getLevelId());
    }

    public static void resetStates() {
        resetPlayerState();
        resetHelperState();
    }

    public static void resetPlayerState() {
        Minecraft.getInstance().options.hideGui = false;
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
    }

    public static void resetHelperState() {
        loadedSummary = null;
        menuActive = false;
    }

    public static void resetConfig() {
        AetherConfig.CLIENT.enable_world_preview.set(false);
        AetherConfig.CLIENT.enable_world_preview.save();
    }

    public static void setActive() {
        menuActive = true;
    }

    public static boolean isActive() {
        return menuActive;
    }

    public static void setupLevelForDisplay() {
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            Minecraft.getInstance().options.hideGui = true;
            Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
            deleteSessionLock();
            WorldDisplayHelper.setMenu();
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
                LevelStorageAccessAccessor levelStorageAccessAccessor = (LevelStorageAccessAccessor) storageAccess;
                levelStorageAccessAccessor.aether$setLock(DirectoryLock.create(storageAccess.getWorldDir()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelStorageSource.LevelStorageAccess getStorageAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            MinecraftServerAccessor minecraftServerAccessor = (MinecraftServerAccessor) server;
            return minecraftServerAccessor.aether$getStorageSource();
        } else {
            return null;
        }
    }
}
