package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.capability.interfaces.IEternalDay;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EternalDayListener
{
    public static Level world;

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() != null && event.getPlayer().level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getPlayer().level;
            MinecraftServer server = world.getServer();
            for (ServerLevel serverworld : server.getAllLevels()) {
                if (serverworld.dimension() == AetherDimensions.AETHER_WORLD) {
                    IEternalDay.get(world).ifPresent(IEternalDay::syncToClient);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer() != null && event.getPlayer().level instanceof ServerLevel) {
            ServerLevel world = (ServerLevel) event.getPlayer().level;
            MinecraftServer server = world.getServer();
            for (ServerLevel serverworld : server.getAllLevels()) {
                if (serverworld.dimension() == AetherDimensions.AETHER_WORLD) {
                    IEternalDay.get(world).ifPresent(IEternalDay::syncToClient);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        world = event.world;
        if (event.side == LogicalSide.SERVER) {
            ServerLevel world = (ServerLevel) event.world;
            MinecraftServer server = world.getServer();
            for (ServerLevel serverworld : server.getAllLevels()) {
                IEternalDay.get(serverworld).ifPresent(eternalDay -> eternalDay.serverTick(serverworld));
            }
        }
    }
}
