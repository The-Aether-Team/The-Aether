package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.DimensionClientHooks;
import net.minecraft.client.Camera;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Triple;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DimensionClientListener {
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
        Triple<Float, Float, Float> renderFog = DimensionClientHooks.renderFog(camera, red, green, blue);
        if (renderFog.getLeft() != null && renderFog.getMiddle() != null && renderFog.getRight() != null) {
            event.setRed(renderFog.getLeft());
            event.setGreen(renderFog.getMiddle());
            event.setBlue(renderFog.getRight());
        }
    }
}
