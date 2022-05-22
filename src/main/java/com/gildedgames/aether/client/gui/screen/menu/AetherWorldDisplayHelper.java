package com.gildedgames.aether.client.gui.screen.menu;

import com.gildedgames.aether.core.AetherConfig;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.DirectoryLock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.util.*;

public class AetherWorldDisplayHelper {
    public static Level loadedLevel = null;
    public static LevelSummary loadedSummary = null;
    public static boolean loadingLevel = false;
    public static GameType loadedGameMode = null;
    public static Vec3 loadedPosition = null;
    public static GameRules loadedGameRules = null;

    public static void enableWorldPreview(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        if (AetherConfig.CLIENT.enable_world_preview.get()) {
            if (loadedLevel == null) {

                LevelStorageSource source = minecraft.getLevelSource();

                try {
                    var summaryList = source.getLevelList();
                    if (summaryList.size() > 0) {
                        int i = 0;
                        boolean enabled = true;
                        LevelSummary summary = summaryList.get(i);
                        while (summary.isLocked() || summary.isDisabled()) {
                            i++;
                            if (i >= summaryList.size()) {
                                enabled = false;
                                break;
                            }
                            summary = summaryList.get(i);
                        }
                        if (enabled) {
                            loadedSummary = summary;
                            loadWorld(screen, summary);
                        }
                    }
                } catch (LevelStorageException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fixWorld() {
        Minecraft minecraft = Minecraft.getInstance();
        var player = minecraft.getSingleplayerServer().getPlayerList().getPlayers().get(0);
        player.setGameMode(loadedGameMode);
        player.setPos(loadedPosition);
        var world = (ServerLevel)player.getCommandSenderWorld();
        world.getGameRules().assignFrom(loadedGameRules, player.getServer());
    }

    public static LevelStorageSource.LevelStorageAccess getStorageAccess() {
        Minecraft minecraft = Minecraft.getInstance();
        Field field = ObfuscationReflectionHelper.findField(MinecraftServer.class, "storageSource");
        try {
            return (LevelStorageSource.LevelStorageAccess)field.get(minecraft.getSingleplayerServer());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void deleteSessionLock() {

        try {
            getStorageAccess().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSessionLock() {
        Minecraft minecraft = Minecraft.getInstance();

        try {
            var access = getStorageAccess();
            Field lockField = ObfuscationReflectionHelper.findField(LevelStorageSource.LevelStorageAccess.class, "lock");
            lockField.set(access, DirectoryLock.create(access.getWorldDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadWorld(Screen screen, LevelSummary summary) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        if (minecraft.getLevelSource().levelExists(summary.getLevelId())) {
            minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
            loadingLevel = true;
            loadedSummary = summary;
            minecraft.loadLevel(summary.getLevelId());
        }
    }

    public static void disableWorldPreview(Screen screen) {
        Minecraft minecraft = Minecraft.getInstance();
        if (loadedLevel != null) {
            if (minecraft.getSingleplayerServer() != null) {
                //if (!minecraft.getSingleplayerServer().isStopped()) {
                minecraft.getSingleplayerServer().halt(false);

                minecraft.level = null;
            }
        }
        loadingLevel = false;
        loadedLevel = null;
        loadedSummary = null;
    }

    public static void quickLoad() {
        if (loadedSummary != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (minecraft.getLevelSource().levelExists(loadedSummary.getLevelId())) {
                //this.minecraft.forceSetScreen(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
                loadingLevel = false;
                openSessionLock();
                var player = minecraft.getSingleplayerServer().getPlayerList().getPlayers().get(0);
                player.setGameMode(loadedGameMode);
                player.setPos(loadedPosition);
                var world = (ServerLevel)player.getCommandSenderWorld();
                world.getGameRules().assignFrom(loadedGameRules, player.getServer());
                loadedGameRules = null;
                minecraft.forceSetScreen(null);
                loadedLevel = null;
                loadedSummary = null;
            }
        }
    }
}
