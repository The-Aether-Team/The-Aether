package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.LevelClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class LevelClientListener {
    @SubscribeEvent
    public static void onRenderLevelLast(RenderLevelStageEvent event) {
        LevelClientHooks.renderMenuWithWorld(Minecraft.getInstance());
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        LevelClientHooks.adjustShadow(event.getRenderer());
        if (LevelClientHooks.shouldRenderPlayer()) {
            event.setCanceled(true);
        }
    }
}
