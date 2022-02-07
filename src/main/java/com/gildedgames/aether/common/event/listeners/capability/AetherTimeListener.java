package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.registry.AetherDimensions;
import com.gildedgames.aether.core.capability.interfaces.IAetherTime;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherTimeListener
{
    public static Level world;

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        syncAetherTime(event.getPlayer());
    }

    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        syncAetherTime(event.getPlayer());
    }

    private static void syncAetherTime(Player player) {
        if (player != null && player.level instanceof ServerLevel world) {
            MinecraftServer server = world.getServer();
            for (ServerLevel serverworld : server.getAllLevels()) {
                if (serverworld.dimension() == AetherDimensions.AETHER_WORLD) {
                    IAetherTime.get(world).ifPresent(IAetherTime::syncToClient);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if(event.world.dimension() == AetherDimensions.AETHER_WORLD) {
            world = event.world;
            IAetherTime.get(event.world).ifPresent(aetherTime -> aetherTime.tick(event.world));
        }
    }
}
