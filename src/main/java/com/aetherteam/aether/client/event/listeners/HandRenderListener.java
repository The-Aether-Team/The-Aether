package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.HandRenderHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class HandRenderListener {
    /**
     * @see HandRenderHooks#renderGloveHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     * @see HandRenderHooks#renderShieldOfRepulsionHandOverlay(ItemInHandRenderer, AbstractClientPlayer, InteractionHand, float, float, float, PoseStack, MultiBufferSource, int)
     */
    @SubscribeEvent
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
