package com.aetherteam.aether.client.event.hooks;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.AetherMusicManager;
import com.aetherteam.aether.client.WorldDisplayHelper;
import com.aetherteam.aether.mixin.mixins.client.accessor.EntityRendererAccessor;
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
        } else if (screen instanceof TitleScreen && !AetherConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.resetActive();
        }
    }

    public static boolean hideScreen(Screen screen) {
        return screen instanceof TitleScreen && AetherConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().level == null;
    }

    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage) {
        Minecraft minecraft = Minecraft.getInstance();
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (WorldDisplayHelper.isActive()) {
                if (minecraft.screen == null || minecraft.screen instanceof PauseScreen) {
                    WorldDisplayHelper.setupLevelForDisplay();
                }
            } else {
                WorldDisplayHelper.resetActive();
            }
        }
    }

    public static void tickMenuWhenPaused() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            if (WorldDisplayHelper.isActive() && minecraft.isPaused()) {
                minecraft.gameRenderer.tick();
                minecraft.levelRenderer.tick();
                AetherMusicManager.tick();
                minecraft.getMusicManager().tick();
                minecraft.getSoundManager().tick(false);
                minecraft.level.animateTick(minecraft.player.getBlockX(), minecraft.player.getBlockY(), minecraft.player.getBlockZ());
                Minecraft.getInstance().particleEngine.tick();
            }
        }
    }

    public static void angleCamera() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (WorldDisplayHelper.isActive() && player != null) {
            float f = (float) (minecraft.getDeltaFrameTime() * minecraft.options.panoramaSpeed().get());
            float spin = wrapDegrees(player.getViewYRot(minecraft.getDeltaFrameTime()) + f * 0.2F);
            player.setYRot(spin);
            player.setXRot(0);
        }
    }

    private static float wrapDegrees(float value) {
        return value > 360.0F ? value - 360.0F : value;
    }

    public static boolean hideOverlays() {
        return WorldDisplayHelper.isActive();
    }

    public static boolean shouldHidePlayer() {
        return WorldDisplayHelper.isActive();
    }

    public static boolean shouldHideEntity(Entity entity) {
        return WorldDisplayHelper.isActive() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity);
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
