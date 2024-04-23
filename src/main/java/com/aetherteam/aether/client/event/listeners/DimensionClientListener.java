package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.DimensionClientHooks;
import com.mojang.blaze3d.shaders.FogShape;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;
import org.apache.commons.lang3.tuple.Triple;

public class DimensionClientListener {
    /**
     * @see DimensionClientHooks#renderNearFog(Camera, FogRenderer.FogMode, float)
     * @see DimensionClientHooks#reduceLavaFog(Camera, float)
     */
    public static boolean onRenderFog(FogRenderer.FogMode fogMode, FogType type, Camera camera, float partialTick, float renderDistance, float nearDistance, float farDistance, FogShape shape, FogEvents.FogData data) {
        Float renderNearFog = DimensionClientHooks.renderNearFog(camera, fogMode, data.getFarPlaneDistance());
        if (renderNearFog != null) {
            data.setNearPlaneDistance(renderNearFog);
            return true;
        }
        Float reduceLavaFog = DimensionClientHooks.reduceLavaFog(camera, data.getNearPlaneDistance());
        if (reduceLavaFog != null) {
            data.setNearPlaneDistance(reduceLavaFog);
            data.setFarPlaneDistance(reduceLavaFog * 4);
            return true;
        }
        return false;
    }

    /**
     * @see DimensionClientHooks#renderFogColors(Camera, float, float, float)
     * @see DimensionClientHooks#adjustWeatherFogColors(Camera, float, float, float)
     */
    public static void onRenderFogColor(FogEvents.ColorData event, float partialTicks) {
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
    public static void onClientTick(Minecraft client) {
        DimensionClientHooks.tickTime();
    }

    public static void init() {
        FogEvents.RENDER_FOG.register(DimensionClientListener::onRenderFog);
        FogEvents.SET_COLOR.register(DimensionClientListener::onRenderFogColor);
        ClientTickEvents.START_CLIENT_TICK.register(DimensionClientListener::onClientTick);
    }
}
