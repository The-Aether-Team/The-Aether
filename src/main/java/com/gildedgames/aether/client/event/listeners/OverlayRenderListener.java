package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.gui.overlay.AetherOverlays;
import com.gildedgames.aether.core.capability.interfaces.IAetherPlayer;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.CameraType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class OverlayRenderListener
{
    @SubscribeEvent
    public static void onRenderOverlayPre(RenderGameOverlayEvent.Pre event) {
//        Minecraft minecraft = Minecraft.getInstance();
//        Window window = minecraft.getWindow();
//        LocalPlayer player = minecraft.player;
//        if (player != null) {
//            if (event.getType() == RenderGameOverlayEvent.ElementType.VIGNETTE && minecraft.options.getCameraType() == CameraType.FIRST_PERSON) {
//                IAetherPlayer.get(player).ifPresent(handler -> {
//                    AetherOverlays.renderInebriationOverlay(minecraft, window, handler);
//                    AetherOverlays.renderRemedyOverlay(minecraft, window, handler);
//                    AetherOverlays.renderRepulsionShieldOverlay(minecraft, window, handler);
//                });
//            }
//        }
    }

    @SubscribeEvent
    public static void onRenderOverlayPost(RenderGameOverlayEvent.Post event) {
//        Minecraft minecraft = Minecraft.getInstance();
//        Window window = minecraft.getWindow();
//        LocalPlayer player = minecraft.player;
//        if (player != null) {
//            if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
//                AetherOverlays.renderHammerCooldownOverlay(event, minecraft, window, player);
//            } else if (event.getType() == RenderGameOverlayEvent.ElementType.PORTAL) {
//                IAetherPlayer.get(player).ifPresent(handler -> AetherOverlays.renderAetherPortalOverlay(event, minecraft, window, handler));
//            }
//        }
    }
}
