package com.gildedgames.aether.event.listeners;

import com.gildedgames.aether.event.events.AetherBannedItemEvent;
import com.gildedgames.aether.event.hooks.DimensionHooks;
import com.gildedgames.aether.world.AetherDimensions;
import com.gildedgames.aether.world.AetherLevelData;
import com.gildedgames.aether.capability.time.AetherTime;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.SleepFinishedTimeEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.core.Direction;

@Mod.EventBusSubscriber
public class DimensionListener {
    @SubscribeEvent
    public static void checkBanned(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        Level level = event.getWorld();
        BlockPos blockPos = event.getPos();
        Direction direction = event.getFace();
        ItemStack itemStack = event.getItemStack();
        BlockState blockState = level.getBlockState(blockPos);
        event.setCanceled(DimensionHooks.checkPlacementBanned(player, level, blockPos, direction, itemStack, blockState));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBanned(AetherBannedItemEvent.SpawnParticles event) {
        LevelAccessor levelAccessor = event.getWorld();
        BlockPos blockPos = event.getPos();
        DimensionHooks.onPlacementBanned(levelAccessor, blockPos);
    }

    @SubscribeEvent
    public static void onNeighborNotified(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor levelAccessor = event.getWorld();
        BlockPos blockPos = event.getPos();
        event.setCanceled(DimensionHooks.freezeToAerogel(levelAccessor, blockPos));
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        Level level = event.world;
        if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END) {
            DimensionHooks.tickTime(level);
            DimensionHooks.fallFromAether(level);
        }
    }

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> dimension = event.getDimension();
        DimensionHooks.dimensionTravel(entity, dimension);
    }

    @SubscribeEvent
    public static void onPlayerTraveling(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        DimensionHooks.travelling(player);
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        DimensionHooks.syncTrackersFromServer(player);
    }

    /**
     * Initializes the Aether level data for time separate from the overworld.
     * serverLevelData and levelData are access transformed.
     */
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerLevel level && level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            AetherTime.get(level).ifPresent(cap -> {
                AetherLevelData levelData = new AetherLevelData(level.getServer().getWorldData(), level.getServer().getWorldData().overworldData(), cap.getDayTime());
                level.serverLevelData = levelData;
                level.levelData = levelData;
            });
        }
    }

    /**
     * Resets the weather cycle if players finish sleeping in an Aether dimension.
     */
    @SubscribeEvent
    public static void onSleepFinish(SleepFinishedTimeEvent event) {
        ServerLevel level = (ServerLevel) event.getWorld();
        if (level.dimensionType().effectsLocation().equals(AetherDimensions.AETHER_DIMENSION_TYPE.location())) {
            level.serverLevelData.setRainTime(0);
            level.serverLevelData.setRaining(false);
            level.serverLevelData.setThunderTime(0);
            level.serverLevelData.setThundering(false);
        }
    }
}
