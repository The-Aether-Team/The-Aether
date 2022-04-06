package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.client.event.hooks.DimensionClientHooks;
import com.gildedgames.aether.client.world.AetherSkyRenderInfo;
import com.gildedgames.aether.common.event.hooks.DimensionHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.world.WorldEvent;
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

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> dimension = event.getDimension();
        DimensionClientHooks.dimensionTravel(entity, dimension);
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        DimensionClientHooks.disableAetherTravelDisplay();
    }
}
