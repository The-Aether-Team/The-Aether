package com.gildedgames.aether.client.handlers;

import com.gildedgames.aether.registry.AetherDimensions;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class AetherClientDimensionHandler
{
    @SubscribeEvent
    public static void onRenderFogColor(EntityViewRenderEvent.FogColors event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        ClientWorld world = (ClientWorld) renderInfo.getRenderViewEntity().world;
        if(world.getDimensionKey() == AetherDimensions.AETHER_WORLD) {
            double height = renderInfo.getProjectedView().y;
            ClientWorld.ClientWorldInfo worldInfo = world.getWorldInfo();
            double d0 = height * worldInfo.getFogDistance();
            FluidState fluidState = renderInfo.getFluidState();
            if(d0 < 1.0D && !fluidState.isTagged(FluidTags.LAVA)) { // Reverse implementation of FogRenderer.updateFogColor.
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
