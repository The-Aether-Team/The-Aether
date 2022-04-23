package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.client.event.hooks.CapabilityClientHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AetherTimeClientListener {
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        CapabilityClientHooks.AetherTimeHooks.setClientWorld();
    }
}
