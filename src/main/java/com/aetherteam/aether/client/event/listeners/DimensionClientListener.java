package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.client.AetherClient;
import com.aetherteam.aether.client.event.hooks.DimensionClientHooks;
import com.aetherteam.nitrogen.fabric.client.events.FogAdjustmentHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogColorHelper;
import com.aetherteam.nitrogen.fabric.client.events.FogEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientListener {
    /**
     * @see AetherClient#eventSetup()
     */
    public static void listen() {
        FogEvents.ON_FOG_RENDER.register(DimensionClientListener::onRenderFog);
        FogEvents.ON_FOG_COLORING.register(DimensionClientListener::onRenderFogColor);
        ClientTickEvents.END_CLIENT_TICK.register(client -> DimensionClientListener.onClientTick());
    }

    /**
     * @see DimensionClientHooks#renderNearFog(Camera, FogRenderer.FogMode, float)
     * @see DimensionClientHooks#reduceLavaFog(Camera, float)
     */
    public static void onRenderFog(FogAdjustmentHelper event) {
        Camera camera = event.getCamera();
        FogRenderer.FogMode fogMode = event.getMode();
        Float renderNearFog = DimensionClientHooks.renderNearFog(camera, fogMode, event.getFarPlaneDistance());
        if (!event.isCanceled() && renderNearFog != null) {
            event.setNearPlaneDistance(renderNearFog);
            event.setCanceled(true);
        }
        Float reduceLavaFog = DimensionClientHooks.reduceLavaFog(camera, event.getNearPlaneDistance());
        if (!event.isCanceled() && reduceLavaFog != null) {
            event.setNearPlaneDistance(reduceLavaFog);
            event.setFarPlaneDistance(reduceLavaFog * 4);
            event.setCanceled(true);
        }
    }

    /**
     * @see DimensionClientHooks#renderFogColors(Camera, float, float, float)
     * @see DimensionClientHooks#adjustWeatherFogColors(Camera, float, float, float)
     */
    public static void onRenderFogColor(FogColorHelper event) {
        //if (true) return;
        Camera camera = event.getCamera();
        Triple<Float, Float, Float> renderFogColors = DimensionClientHooks.renderFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (renderFogColors != null) {
            event.setRed(renderFogColors.getLeft());
            event.setGreen(renderFogColors.getMiddle());
            event.setBlue(renderFogColors.getRight());
        }
        Triple<Float, Float, Float> adjustWeatherFogColors = DimensionClientHooks.adjustWeatherFogColors(camera, event.getRed(), event.getGreen(), event.getBlue());
        if (adjustWeatherFogColors != null) {
            event.setRed(adjustWeatherFogColors.getLeft());
            event.setGreen(adjustWeatherFogColors.getMiddle());
            event.setBlue(adjustWeatherFogColors.getRight());
        }
    }

    /**
     * @see DimensionClientHooks#tickTime()
     */
    public static void onClientTick() {
        DimensionClientHooks.tickTime();
    }
}
