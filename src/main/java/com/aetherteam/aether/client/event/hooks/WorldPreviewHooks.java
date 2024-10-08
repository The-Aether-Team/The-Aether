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
import net.neoforged.neoforge.client.event.*;

public class WorldPreviewHooks {
    /**
     * When a {@link TitleScreen} is opened, if the {@link AetherConfig.Client#enable_world_preview} config is enabled
     * then the world preview is set up, but otherwise it is ensured to be inactive.
     *
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onGuiOpenLowest(ScreenEvent.Opening)
     */
    public static void setupWorldPreview(Screen screen) {
        if (screen instanceof TitleScreen && AetherConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.enableWorldPreview();
        } else if (screen instanceof TitleScreen && !AetherConfig.CLIENT.enable_world_preview.get()) {
            WorldDisplayHelper.resetActive();
        }
    }

    /**
     * Checks if the {@link TitleScreen} should be hidden during loading. This is used to make sure it
     * doesn't show up briefly during the loading screen process when the world preview is being set up.
     *
     * @param screen The currently rendered {@link Screen}.
     * @return Whether to hide the screen, as a {@link Boolean}.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onScreenRender(ScreenEvent.Render.Pre)
     */
    public static boolean hideScreen(Screen screen) {
        return screen instanceof TitleScreen && AetherConfig.CLIENT.enable_world_preview.get() && Minecraft.getInstance().level == null;
    }

    /**
     * After the level is loaded for the world preview by other events, when it gets rendered then
     * the panorama-style setup with the displayed menu is handled by {@link WorldDisplayHelper#setupLevelForDisplay()}.
     *
     * @param stage The {@link net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage} of rendering.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderLevelLast(RenderLevelStageEvent)
     */
    public static void renderMenuWithWorld(RenderLevelStageEvent.Stage stage) {
        Minecraft minecraft = Minecraft.getInstance();
        if (stage == RenderLevelStageEvent.Stage.AFTER_WEATHER) {
            if (WorldDisplayHelper.isActive()) {
                if (minecraft.screen == null || minecraft.screen instanceof PauseScreen) { // The menu can only be rendered if there is no screen or a PauseScreen when the level loads.
                    WorldDisplayHelper.setupLevelForDisplay();
                }
            }
        }
    }

    /**
     * Handles how the world should be displayed for the world preview. Rendering, sounds, and music are allowed to tick, but nothing else is.
     * This makes the world static and paused but also still animated.
     *
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onClientTick(TickEvent.ClientTickEvent)
     */
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

    /**
     * Angles and rotates the camera for the world preview display.
     *
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onCameraView(ViewportEvent.ComputeCameraAngles)
     */
    public static void angleCamera() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (WorldDisplayHelper.isActive() && player != null) {
            float f = (float) (minecraft.getDeltaFrameTime() * minecraft.options.panoramaSpeed().get()); // Ensures the rotation speed isn't tied to game tick speed
            float spin = wrapDegrees(player.getViewYRot(minecraft.getDeltaFrameTime()) + f * 0.2F);
            player.setYRot(spin);
            player.setXRot(0);
        }
    }

    /**
     * [CODE COPY] - {@link net.minecraft.client.renderer.PanoramaRenderer#wrap(float, float)}.<br><br>
     * Modified to have a static max at 360 degrees.
     */
    private static float wrapDegrees(float value) {
        return value > 360.0F ? value - 360.0F : value;
    }

    /**
     * @return Whether to hide player screen overlays in the world preview, as a {@link Boolean}.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderOverlay(RenderGuiOverlayEvent.Pre)
     */
    public static boolean hideOverlays() {
        return WorldDisplayHelper.isActive();
    }

    /**
     * @return Whether to hide the player in the world preview, as a {@link Boolean}.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderPlayer(RenderPlayerEvent.Pre)
     */
    public static boolean shouldHidePlayer() {
        return WorldDisplayHelper.isActive();
    }

    /**
     * Checks whether to hide an entity in the world preview.
     *
     * @param entity The {@link Entity}.
     * @return The {@link Boolean} result.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderEntity(RenderLivingEvent.Pre)
     */
    public static boolean shouldHideEntity(Entity entity) {
        return WorldDisplayHelper.isActive() && Minecraft.getInstance().player != null && Minecraft.getInstance().player.getVehicle() != null && Minecraft.getInstance().player.getVehicle().is(entity);
    }

    /**
     * Removes or enables an entity's shadow for world preview rendering.
     *
     * @param renderer The {@link EntityRenderer}.
     * @param flag     Whether the entity that the shadow belongs to is hidden.
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderPlayer(RenderPlayerEvent.Pre)
     * @see com.aetherteam.aether.client.event.listeners.WorldPreviewListener#onRenderPlayer(RenderPlayerEvent.Pre)
     */
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
