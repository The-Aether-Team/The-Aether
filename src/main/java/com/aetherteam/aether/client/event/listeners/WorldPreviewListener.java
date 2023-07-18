package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.event.hooks.WorldPreviewHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class WorldPreviewListener {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGuiOpenLowest(ScreenEvent.Opening event) {
        Screen newScreen = event.getNewScreen();
        WorldPreviewHooks.setupWorldPreview(newScreen); //todo test auto-alignment config
    }

    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        Minecraft minecraft = Minecraft.getInstance();
        WorldPreviewHooks.renderMenuWithWorld(stage, minecraft);
    }

    @SubscribeEvent
    public static void onCameraView(ViewportEvent.ComputeCameraAngles event) {
        WorldPreviewHooks.angleCamera();
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (WorldPreviewHooks.hideOverlays()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        PlayerRenderer renderer = event.getRenderer();
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        if (hide) {
            event.setCanceled(true);
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
    }

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
