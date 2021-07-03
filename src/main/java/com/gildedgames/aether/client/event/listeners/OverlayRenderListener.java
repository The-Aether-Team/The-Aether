package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.renderer.overlay.AetherOverlays;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRenderListener
{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            IAetherPlayer.get(player).ifPresent(handler -> {
                if(event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
                    AetherOverlays.renderAetherPortalOverlay(event, Minecraft.getInstance(), event.getWindow(), handler);
                }
            });
        }
    }
}
