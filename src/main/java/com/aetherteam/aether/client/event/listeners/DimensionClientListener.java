package com.aetherteam.aether.client.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.event.hooks.DimensionClientHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Triple;

@Mod.EventBusSubscriber(modid = Aether.MODID, value = Dist.CLIENT)
public class DimensionClientListener {
    /**
     * @see DimensionClientHooks#renderNearFog(Camera, FogRenderer.FogMode, float)
     * @see DimensionClientHooks#reduceLavaFog(Camera, float)
     */
    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
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
    @SubscribeEvent
    public static void onRenderFogColor(ViewportEvent.ComputeFogColor event) {
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
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            DimensionClientHooks.tickTime();
        }
    }
}
