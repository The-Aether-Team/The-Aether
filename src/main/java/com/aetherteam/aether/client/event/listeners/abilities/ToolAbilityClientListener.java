package com.aetherteam.aether.client.event.listeners.abilities;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.AbilityHooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class ToolAbilityClientListener {
    @SubscribeEvent
    public static void resetDebuffToolState(ClientPlayerNetworkEvent.LoggingOut event) {
        AbilityHooks.ToolHooks.resetDebuffToolsState();
    }
}
