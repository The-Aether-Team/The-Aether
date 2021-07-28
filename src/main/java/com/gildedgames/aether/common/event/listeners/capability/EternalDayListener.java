package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EternalDayListener
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            ServerWorld world = (ServerWorld) event.world;
            MinecraftServer server = world.getServer();
            for (ServerWorld serverWorld : server.getAllLevels()) {
                IEternalDay.get(serverWorld).ifPresent(eternalDay -> eternalDay.serverTick(world));
            }
        }
    }
}
