package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.WorldPreviewHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.event.client.CameraSetupCallback;
import io.github.fabricators_of_create.porting_lib.event.client.LivingEntityRenderEvents;
import io.github.fabricators_of_create.porting_lib.event.client.RenderPlayerEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class WorldPreviewListener {
    /**
     * @see WorldPreviewHooks#setupWorldPreview(Screen)
     */
    public static void onGuiOpenLowest(Screen newScreen) {
        WorldPreviewHooks.setupWorldPreview(newScreen);
    }

    /**
     * @see WorldPreviewHooks#hideScreen(Screen)
     */
    public static boolean onScreenRender(Screen screen) {
        return WorldPreviewHooks.hideScreen(screen);
    }

    /**
     * @see WorldPreviewHooks#renderMenuWithWorld()
     */
    public static void onRenderLevelLast(WorldRenderContext context) {
        WorldPreviewHooks.renderMenuWithWorld();
    }

    /**
     * @see WorldPreviewHooks#tickMenuWhenPaused()
     */
    public static void onClientTick(Minecraft client) {
        WorldPreviewHooks.tickMenuWhenPaused();
    }

    /**
     * @see WorldPreviewHooks#angleCamera()
     */
    public static boolean onCameraView(CameraSetupCallback.CameraInfo info) {
        WorldPreviewHooks.angleCamera();
        return false;
    }

//    /** TODO: PORT
//     * @see WorldPreviewHooks#hideOverlays()
//     */
//    @SubscribeEvent
//    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
//        if (WorldPreviewHooks.hideOverlays()) {
//            event.setCanceled(true);
//        }
//    }

    /**
     * @see WorldPreviewHooks#shouldHidePlayer()
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static boolean onRenderPlayer(Player player, PlayerRenderer renderer, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        boolean hide = WorldPreviewHooks.shouldHidePlayer();
        boolean canceled = false;
        if (hide) {
            canceled = true;
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
        return canceled;
    }

    /**
     * @see WorldPreviewHooks#shouldHideEntity(Entity)
     * @see WorldPreviewHooks#adjustShadow(EntityRenderer, boolean)
     */
    public static <T extends LivingEntity, M extends EntityModel<T>> boolean onRenderEntity(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, float partialRenderTick, PoseStack matrixStack, MultiBufferSource buffers, int light) {
        boolean hide = WorldPreviewHooks.shouldHideEntity(entity);
        boolean canceled = false;
        if (hide) {
            canceled = true;
        }
        WorldPreviewHooks.adjustShadow(renderer, hide);
        return canceled;
    }

    public static void init() {
        WorldRenderEvents.LAST.register(WorldPreviewListener::onRenderLevelLast);
        ClientTickEvents.END_CLIENT_TICK.register(WorldPreviewListener::onClientTick);
        LivingEntityRenderEvents.PRE.register(WorldPreviewListener::onRenderEntity);
        CameraSetupCallback.EVENT.register(WorldPreviewListener::onCameraView);
        RenderPlayerEvents.PRE.register(WorldPreviewListener::onRenderPlayer);
    }
}
