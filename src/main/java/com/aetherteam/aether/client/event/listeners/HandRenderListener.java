package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.HandRenderHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.event.client.RenderHandCallback;
import io.github.fabricators_of_create.porting_lib.event.client.RenderHandCallback.RenderHandEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;

public class HandRenderListener {
    /**
     * @see HandRenderHooks#renderGloveHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     * @see HandRenderHooks#renderShieldOfRepulsionHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     */
    public static void onRenderHand(RenderHandEvent event) {
        ItemInHandRenderer itemInHandRenderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
        AbstractClientPlayer abstractClientPlayer = Minecraft.getInstance().player;
        InteractionHand hand = event.getHand();
        float interpolatedPitch = event.getPitch();
        float swingProgress = event.getSwingProgress();
        float equipProgress = event.getEquipProgress();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource multiBufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        if (!event.isCanceled()) {
            HandRenderHooks.renderGloveHandOverlay(itemInHandRenderer, abstractClientPlayer, hand, interpolatedPitch, swingProgress, equipProgress, poseStack, multiBufferSource, packedLight);
            HandRenderHooks.renderShieldOfRepulsionHandOverlay(itemInHandRenderer, abstractClientPlayer, hand, interpolatedPitch, swingProgress, equipProgress, poseStack, multiBufferSource, packedLight);
        }
    }

    public static void init() {
        RenderHandCallback.EVENT.register(HandRenderListener::onRenderHand);
    }
}
