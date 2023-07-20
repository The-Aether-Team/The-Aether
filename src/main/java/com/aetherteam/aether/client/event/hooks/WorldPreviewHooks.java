package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.mixins.client.accessor.EntityRendererAccessor;
import com.aetherteam.nitrogen.client.NitrogenClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class WorldPreviewHooks {
    public static void setupWorldPreview(Screen screen) {
        if (screen instanceof TitleScreen && AetherConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.enableWorldPreview();
        }
    }

    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage, Minecraft minecraft) {
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (AetherConfig.CLIENT.enable_world_preview.get()) {
                if (WorldDisplayHelper.loadedSummary != null) {
                    if (minecraft.screen == null || minecraft.screen instanceof PauseScreen) {
                        setupMenu(minecraft);
                    }
                }
            } else {
                WorldDisplayHelper.loadedLevel = null;
                WorldDisplayHelper.loadedSummary = null;
            }
        }
    }

    public static void setupMenu(Minecraft minecraft) {
        WorldDisplayHelper.setupLevelForDisplay();
        NitrogenClient.MENU_HELPER.setShouldFade(false);
        Screen screen = NitrogenClient.MENU_HELPER.applyMenu(NitrogenClient.MENU_HELPER.getActiveMenu());
        if (screen != null) {
            minecraft.forceSetScreen(screen);
        }
    }

    public static void angleCamera() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null && player != null) {
            float f = (float) (minecraft.getDeltaFrameTime() * minecraft.options.panoramaSpeed().get());
            float spin = wrapDegrees(player.getViewYRot(minecraft.getDeltaFrameTime()) + f * 0.2F);
            player.setYRot(spin);
        }
    }

    private static float wrapDegrees(float value) {
        return value > 360.0F ? value - 360.0F : value;
    }

    public static boolean hideOverlays() {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
    }

    public static boolean shouldHidePlayer() {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null;
    }

    public static boolean shouldHideEntity(Entity entity) {
        return AetherConfig.CLIENT.enable_world_preview.get() && WorldDisplayHelper.loadedLevel != null && WorldDisplayHelper.loadedSummary != null
                && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity);
    }

    public static void adjustShadow(EntityRenderer<?> renderer, boolean flag) {
        EntityRendererAccessor entityRendererAccessor = (EntityRendererAccessor) renderer;
        if (flag) {
            entityRendererAccessor.aether$setShadowRadius(0.0F);
        } else {
            if (entityRendererAccessor.aether$getShadowRadius() == 0.0F) {
                entityRendererAccessor.aether$setShadowRadius(0.5F);
            }
        }
    }
}
