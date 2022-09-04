package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.LevelClientHooks;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class LevelClientListener {
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        Minecraft minecraft = Minecraft.getInstance();
        LevelClientHooks.renderMenuWithWorld(stage, minecraft);
        LevelClientHooks.renderDungeonBlockOverlays(stage, poseStack, camera, minecraft);
    }
}
