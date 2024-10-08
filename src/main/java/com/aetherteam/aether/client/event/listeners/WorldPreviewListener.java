package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.WorldPreviewHooks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.*;

public class WorldPreviewListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(EventPriority.LOWEST, WorldPreviewListener::onGuiOpenLowest);
        bus.addListener(WorldPreviewListener::onScreenRender);
        bus.addListener(WorldPreviewListener::onRenderLevelLast);
        bus.addListener(WorldPreviewListener::onClientTick);
        bus.addListener(WorldPreviewListener::onCameraView);
        bus.addListener(WorldPreviewListener::onRenderOverlay);
        bus.addListener(WorldPreviewListener::onRenderPlayer);
        bus.addListener(WorldPreviewListener::onRenderEntity);
    }

    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    public static void onGuiOpenLowest(ScreenEvent.Opening event) {
        Screen newScreen = event.getNewScreen();
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    public static void onScreenRender(ScreenEvent.Render.Pre event) {
        Screen screen = event.getScreen();
        if (WorldPreviewHooks.hideScreen(screen)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld(RenderLevelStageEvent.Stage)
     */
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        WorldPreviewHooks.renderMenuWithWorld(stage);
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    public static void onClientTick(ClientTickEvent event) {
        WorldPreviewHooks.tickMenuWhenPaused();
    }

    /**
     * @see WorldPreviewHooks#angleCamera()
     */
    public static void onCameraView(ViewportEvent.ComputeCameraAngles event) {
        WorldPreviewHooks.angleCamera();
    }

    /**
     * @see WorldPreviewHooks#hideOverlays()
     */
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (WorldPreviewHooks.hideOverlays()) {
            event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PlayerRenderer renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

    /**
     * @see WorldPreviewHooks#shouldHideEntity(Entity)
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static void onRenderEntity(RenderLivingEvent.Pre<?, ?> event) {
        Entity entity = event.getEntity();
        EntityRenderer<?> renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }
}
