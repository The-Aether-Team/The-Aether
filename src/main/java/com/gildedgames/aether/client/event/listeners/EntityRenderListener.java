package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.EntityRenderHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EntityRenderListener {
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        EntityRenderHooks.adjustShadow(event.getRenderer());
        if (EntityRenderHooks.shouldRenderPlayer()) {
            event.setCanceled(true);
        }
    }
}
