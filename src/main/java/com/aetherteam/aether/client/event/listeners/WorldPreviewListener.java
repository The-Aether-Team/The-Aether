package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.WorldPreviewHooks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class WorldPreviewListener {
    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiOpenLowest(ScreenEvent.Opening event) {
        Screen newScreen = event.getNewScreen();
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render event) {
        // TODO: 1.19.4 / 1.20.1, fix incorrect usage of `setCanceled` here as per Forge Changes
        Screen screen = event.getScreen();
        if (WorldPreviewHooks.hideScreen(screen)) {
            //event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld(RenderLevelStageEvent.Stage)
     */
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        WorldPreviewHooks.renderMenuWithWorld(stage);
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            WorldPreviewHooks.tickMenuWhenPaused();
        }
    }

    /**
     * @see WorldPreviewHooks#angleCamera()
     */
    @SubscribeEvent
    public static void onCameraView(ViewportEvent.ComputeCameraAngles event) {
        WorldPreviewHooks.angleCamera();
    }

    /**
     * @see WorldPreviewHooks#hideOverlays()
     */
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (WorldPreviewHooks.hideOverlays()) {
            event.setCanceled(true);
        }
    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    @SubscribeEvent
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
    @SubscribeEvent
    public static <T extends LivingEntity, M extends EntityModel<T>> void onRenderEntity(RenderLivingEvent.Pre<T, M> event) {
        Entity entity = event.getEntity();
        EntityRenderer<?> renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }
}
