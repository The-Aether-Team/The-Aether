package com.aetherteam.aether.api;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.event.hooks.GuiHooks;
import com.aetherteam.aether.mixin.mixins.common.accessor.LevelStorageAccessAccessor;
import com.aetherteam.aether.mixin.mixins.common.accessor.MinecraftServerAccessor;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorldDisplayHelper {
    public static Level loadedLevel = null;
    public static LevelSummary loadedSummary = null;

    public static void toggleWorldPreview(boolean config) {
        if (config) {
            enableWorldPreview();
        } else {
            if (disableWorldPreview(new GenericDirtMessageScreen(Component.literal("")))) {
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
                            loadWorld(summary);
                        }
                    }
                } catch (ExecutionException | InterruptedException | UnsupportedOperationException e) {
                    AetherConfig.CLIENT.enable_world_preview.set(false);
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
            minecraft.forceSetScreen(new GenericDirtMessageScreen(Component.translatable("selectWorld.data_read")));
            loadedSummary = summary;
            minecraft.createWorldOpenFlows().loadLevel(minecraft.screen, summary.getLevelId());
        }
    }

    public static void setupLevelForDisplay() {
        Minecraft minecraft = Minecraft.getInstance();
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null) {
            loadedLevel = minecraft.level;
            Minecraft.getInstance().options.hideGui = true;
            Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
            deleteSessionLock();
        }
    }

    public static void quickLoad() {
        if (loadedSummary != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (minecraft.getLevelSource().levelExists(loadedSummary.getLevelId()) && minecraft.getSingleplayerServer() != null) {
                openSessionLock();
                fixWorld();
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
        Minecraft.getInstance().options.hideGui = false;
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        loadedLevel = null;
        loadedSummary = null;
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
