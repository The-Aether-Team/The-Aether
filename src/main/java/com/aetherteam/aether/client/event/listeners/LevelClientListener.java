package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class LevelClientListener {
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Frustum frustum = event.getFrustum();
        Minecraft minecraft = Minecraft.getInstance();
        LevelClientHooks.renderMenuWithWorld(stage, minecraft);
        LevelClientHooks.renderDungeonBlockOverlays(stage, poseStack, camera, frustum, minecraft);
    }

    @SubscribeEvent
    public static void onCameraView(ViewportEvent.ComputeCameraAngles event) {
        float prevYaw = event.getYaw();
        Float newYaw = LevelClientHooks.angleCamera(prevYaw);
        if (newYaw != null) {
            event.setPitch(0.0F);
            event.setYaw(newYaw);
        }
    }
}
