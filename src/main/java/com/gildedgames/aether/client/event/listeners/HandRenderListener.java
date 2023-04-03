package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.Aether;
import com.gildedgames.aether.client.event.hooks.HandRenderHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class HandRenderListener {
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
