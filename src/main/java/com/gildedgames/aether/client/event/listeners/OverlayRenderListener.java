package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.renderer.overlay.AetherOverlays;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.PointOfView;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRenderListener
{
    @SubscribeEvent
    public static void onRenderOverlayPre(RenderGameOverlayEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.getWindow();
        ClientPlayerEntity player = minecraft.player;
        if (player != null) {
            IAetherPlayer.get(player).ifPresent(handler -> {
                if (event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE && minecraft.options.getCameraType() == PointOfView.FIRST_PERSON) {
                    AetherOverlays.renderInebriationOverlay(minecraft, window, handler);
                    AetherOverlays.renderRemedyOverlay(minecraft, window, handler);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onRenderOverlayPost(RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        MainWindow window = minecraft.getWindow();
        ClientPlayerEntity player = minecraft.player;
        if (player != null) {
            IAetherPlayer.get(player).ifPresent(handler -> {
                if (event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
                    AetherOverlays.renderAetherPortalOverlay(event, minecraft, window, handler);
                }
            });
        }
    }
}
