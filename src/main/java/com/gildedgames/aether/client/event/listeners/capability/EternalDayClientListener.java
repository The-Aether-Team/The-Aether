package com.gildedgames.aether.client.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EternalDayClientListener
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.RenderTickEvent event) {
        if (event.side == LogicalSide.CLIENT) {
            ClientWorld world = Minecraft.getInstance().level;
            if (world != null) {
                IEternalDay.get(world).ifPresent(eternalDay -> eternalDay.clientTick(world));
            }
        }
    }
}
