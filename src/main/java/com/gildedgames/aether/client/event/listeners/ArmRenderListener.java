package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.ArmRenderHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ArmRenderListener {
    @SubscribeEvent
    public static void onRenderArm(RenderArmEvent event) {
        AbstractClientPlayer abstractClientPlayer = event.getPlayer();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource multiBufferSource = event.getMultiBufferSource();
        int packedLight = event.getPackedLight();
        HumanoidArm humanoidArm = event.getArm();
        ArmRenderHooks.renderGloveArmOverlay(abstractClientPlayer, poseStack, multiBufferSource, packedLight, humanoidArm);
        ArmRenderHooks.renderShieldOfRepulsionArmOverlay(abstractClientPlayer, poseStack, multiBufferSource, packedLight, humanoidArm);
    }
}
