package com.gildedgames.aether.client.event.listeners;

import com.gildedgames.aether.common.registry.AetherDimensions;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DimensionClientListener
{
    @SubscribeEvent
    public static void onRenderFogColor(EntityViewRenderEvent.FogColors event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        ClientWorld world = (ClientWorld) renderInfo.getEntity().level;
        if (world.dimension() == AetherDimensions.AETHER_WORLD) {
            double height = renderInfo.getPosition().y;
            ClientWorld.ClientWorldInfo worldInfo = world.getLevelData();
            double d0 = height * worldInfo.getClearColorScale();
            FluidState fluidState = renderInfo.getFluidInCamera();
            if (d0 < 1.0D && !fluidState.is(FluidTags.LAVA)) { // Reverse implementation of FogRenderer.updateFogColor.
                if (d0 < 0.0D) {
                    d0 = 0.0D;
                }
                d0 = d0 * d0;
                if(d0 != 0.0D) {
                    event.setRed((float) ((double) event.getRed() / d0));
                    event.setGreen((float) ((double) event.getGreen() / d0));
                    event.setBlue((float) ((double) event.getBlue() / d0));
                }
            }
        }
    }
}
