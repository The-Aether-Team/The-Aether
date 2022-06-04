package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.DimensionClientHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DimensionClientListener {
    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.RenderFogEvent event) {
        Camera camera = event.getCamera();
        FogRenderer.FogMode fogMode = event.getMode();
        float farDistance = event.getFarPlaneDistance();
        Float renderNearFog = DimensionClientHooks.renderNearFog(camera, fogMode, farDistance);
        if (renderNearFog != null) {
            event.setNearPlaneDistance(renderNearFog);
            event.setCanceled(true);
        }
    }

    /**
     * The purpose of this event handler is to prevent the fog from turning black near the void in the Aether.
     * This works with any dimension using the Aether's dimension effects.
     */
    @SubscribeEvent
    public static void onRenderFogColor(EntityViewRenderEvent.FogColors event) {
        Camera camera = event.getCamera();
        float red = event.getRed();
        float green = event.getGreen();
        float blue = event.getBlue();
        Triple<Float, Float, Float> renderFogColors = DimensionClientHooks.renderFogColors(camera, red, green, blue);
        if (renderFogColors.getLeft() != null && renderFogColors.getMiddle() != null && renderFogColors.getRight() != null) {
            event.setRed(renderFogColors.getLeft());
            event.setGreen(renderFogColors.getMiddle());
            event.setBlue(renderFogColors.getRight());
        }
    }

    /**
     * Ticks time in clientside Aether levels.
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            DimensionClientHooks.tickTime();
        }
    }
}
