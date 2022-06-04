package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.LevelClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.client.event.RenderLevelLastEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class LevelClientListener {
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelLastEvent event) {
        LevelClientHooks.renderMenuWithWorld(Minecraft.getInstance());
    }
}
