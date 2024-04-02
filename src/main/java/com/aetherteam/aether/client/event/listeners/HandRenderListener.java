package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.HandRenderHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderHandEvent;

public class HandRenderListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen(IEventBus bus) {
        bus.addListener(HandRenderListener::onRenderHand);
    }

    /**
     * @see HandRenderHooks#renderGloveHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     * @see HandRenderHooks#renderShieldOfRepulsionHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     */
    public static void onRenderHand(RenderHandEvent event) {
        ItemInHandRenderer itemInHandRenderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
        AbstractClientPlayer abstractClientPlayer = Minecraft.getInstance().player;
        InteractionHand hand = event.getHand();
        float interpolatedPitch = event.getInterpolatedPitch();
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
}
