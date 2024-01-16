package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.PlayerTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aether.MODID)
public class DimensionListener {
    /**
     * @see DimensionHooks#startInAether(Player)
     */
    public static void onPlayerLogin(Player player) {
        DimensionHooks.startInAether(player);
    }

    /**
     * @see DimensionHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
    @SubscribeEvent
    public static void onInteractWithPortalFrame(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        BlockPos blockPos = event.getPos();
        Direction direction = event.getFace();
        ItemStack itemStack = event.getItemStack();
        InteractionHand interactionHand = event.getHand();
        if (DimensionHooks.createPortal(player, level, blockPos, direction, itemStack, interactionHand)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see DimensionHooks#detectWaterInFrame(LevelAccessor, BlockPos, BlockState, FluidState)
     */
    @SubscribeEvent
    public static void onWaterExistsInsidePortalFrame(BlockEvent.NeighborNotifyEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        FluidState fluidState = level.getFluidState(blockPos);
        if (DimensionHooks.detectWaterInFrame(level, blockPos, blockState, fluidState)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see DimensionHooks#tickTime(Level)
     * @see DimensionHooks#fallFromAether(Level)
     * @see DimensionHooks#checkEternalDayConfig(Level)
     */
    public static void onWorldTick(ServerLevel level) {
        DimensionHooks.tickTime(level);
        DimensionHooks.fallFromAether(level);
        DimensionHooks.checkEternalDayConfig(level);
    }

    /**
     * @see DimensionHooks#dimensionTravel(Entity, ResourceKey)
     */
    public static void onEntityTravelToDimension(ServerPlayer player, ServerLevel origin, ServerLevel destination) {
        ResourceKey<Level> dimension = destination.dimension();
        DimensionHooks.dimensionTravel(player, dimension);
    }

    /**
     * @see DimensionHooks#travelling(Player)
     */
    public static void onPlayerTraveling(Player player) {
        DimensionHooks.travelling(player);
    }

    /**
     * @see DimensionHooks#initializeLevelData(MinecraftServer, ServerLevel)
     */
    public static void onWorldLoad(MinecraftServer server, ServerLevel level) {
        DimensionHooks.initializeLevelData(server, level);
    }

    /**
     * @see DimensionHooks#finishSleep(LevelAccessor, long)
     */
    @SubscribeEvent
    public static void onSleepFinish(SleepFinishedTimeEvent event) {
        LevelAccessor level = event.getLevel();
        Long time = DimensionHooks.finishSleep(level, event.getNewTime());
        if (time != null) {
            event.setTimeAddition(time);
        }
    }

    /**
     * @see DimensionHooks#isEternalDay(Player)
     */
    public static InteractionResult onTriedToSleep(Player player, BlockPos sleepingPos, boolean vanillaResult) {
        if (DimensionHooks.isEternalDay(player)) {
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

    public static void init() {
        PlayerEvents.LOGGED_IN.register(DimensionListener::onPlayerLogin);
        ServerTickEvents.END_WORLD_TICK.register(DimensionListener::onWorldTick);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register(DimensionListener::onEntityTravelToDimension);
        PlayerTickEvents.START.register(DimensionListener::onPlayerTraveling);
        PlayerTickEvents.END.register(DimensionListener::onPlayerTraveling);
        ServerWorldEvents.LOAD.register(DimensionListener::onWorldLoad);
        EntitySleepEvents.ALLOW_SLEEP_TIME.register(DimensionListener::onTriedToSleep);
    }
}
