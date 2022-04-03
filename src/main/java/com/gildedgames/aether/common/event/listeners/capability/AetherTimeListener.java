package com.gildedgames.aether.common.event.listeners.capability;

import com.gildedgames.aether.common.registry.worldgen.AetherDimensions;
import com.gildedgames.aether.core.capability.time.AetherTime;
import com.gildedgames.aether.core.util.AetherSleepStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AetherTimeListener
{
    // TODO Rebuild into a Level set
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
                if (serverworld.dimension() == AetherDimensions.AETHER_LEVEL) {
                    AetherTime.get(world).ifPresent(AetherTime::syncToClient);
                }
            }
        }
    }

    /**
     *
     */
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (!(event.getWorld() instanceof Level level) || level.dimension() != AetherDimensions.AETHER_LEVEL) return;

        world = level;

        if(!level.isClientSide && level instanceof ServerLevel serverLevel)
            serverLevel.sleepStatus = new AetherSleepStatus();
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event) {
        if (event.getWorld() instanceof Level level && level.dimension() == AetherDimensions.AETHER_LEVEL) {
            world = null;
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.world.dimension() == AetherDimensions.AETHER_LEVEL && event.phase == TickEvent.Phase.END) {
            AetherTime.get(event.world).ifPresent(aetherTime -> aetherTime.serverTick((ServerLevel) event.world));
        }
    }
}
