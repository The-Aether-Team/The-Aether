package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DimensionClientListener
{
    @SubscribeEvent
    public static void onRenderFogColor(EntityViewRenderEvent.FogColors event) { //TODO: Determine if this code is necessary anymore.
        Camera renderInfo = event.getCamera();
        ClientLevel world = (ClientLevel) renderInfo.getEntity().level;
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            double height = renderInfo.getPosition().y;
            ClientLevel.ClientLevelData worldInfo = world.getLevelData();
            double d0 = height * worldInfo.getClearColorScale();
            FogType fluidState = renderInfo.getFluidInCamera();
            if (d0 < 1.0D && fluidState != FogType.LAVA) { // Reverse implementation of FogRenderer.updateFogColor.
                if (d0 < 0.0D) {
                    d0 = 0.0D;
                }
                d0 = d0 * d0;
                if (d0 != 0.0D) {
                    event.setRed((float) ((double) event.getRed() / d0));
                    event.setGreen((float) ((double) event.getGreen() / d0));
                    event.setBlue((float) ((double) event.getBlue() / d0));
                }
            }
        }
    }
}
