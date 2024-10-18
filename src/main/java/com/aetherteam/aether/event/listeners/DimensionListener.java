package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.event.hooks.DimensionHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.AlterGroundEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.concurrent.atomic.AtomicReference;

public class DimensionListener {
    /**
     * @see Aether#eventSetup(IEventBus)
     */
    public static void listen(IEventBus bus) {
        bus.addListener(DimensionListener::onPlayerLogin);
        bus.addListener(DimensionListener::onInteractWithPortalFrame);
        bus.addListener(DimensionListener::onWaterExistsInsidePortalFrame);
        bus.addListener(DimensionListener::onWorldTick);
        bus.addListener(DimensionListener::onEntityTravelToDimension);
        bus.addListener(DimensionListener::onPlayerTraveling);
        bus.addListener(DimensionListener::onWorldLoad);
        bus.addListener(DimensionListener::onSleepFinish);
        bus.addListener(DimensionListener::onTriedToSleep);
        bus.addListener(DimensionListener::onAlterGround);
    }

    /**
     * @see DimensionHooks#startInAether(Player)
     */
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        DimensionHooks.startInAether(player);
    }

    /**
     * @see DimensionHooks#createPortal(Player, Level, BlockPos, Direction, ItemStack, InteractionHand)
     */
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
     * @see DimensionHooks#checkEternalDayConfig(Level)
     */
    public static void onWorldTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) {
            DimensionHooks.tickTime(level);
            DimensionHooks.checkEternalDayConfig(level);
        }
    }

    /**
     * @see DimensionHooks#dimensionTravel(Entity, ResourceKey)
     */
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> dimension = event.getDimension();
        DimensionHooks.dimensionTravel(entity, dimension);
    }

    /**
     * @see DimensionHooks#travelling(Player)
     */
    public static void onPlayerTraveling(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        DimensionHooks.travelling(player);
    }

    /**
     * @see DimensionHooks#initializeLevelData(LevelAccessor)
     */
    public static void onWorldLoad(LevelEvent.Load event) {
        LevelAccessor level = event.getLevel();
        DimensionHooks.initializeLevelData(level);
    }

    /**
     * @see DimensionHooks#finishSleep(LevelAccessor, long)
     */
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
    public static void onTriedToSleep(CanPlayerSleepEvent event) {
        Player player = event.getEntity();
        if (DimensionHooks.isEternalDay(player)) {
            event.setProblem(Player.BedSleepingProblem.NOT_POSSIBLE_NOW);
        }
    }

    /**
     * Prevents Aether Dirt from being replaced by Podzol.
     */
    public static void onAlterGround(AlterGroundEvent event) {
        TreeDecorator.Context context = event.getContext();
        AlterGroundEvent.StateProvider provider = event.getStateProvider();
        event.setStateProvider((rand, pos) -> {
            AtomicReference<BlockState> oldState = new AtomicReference<>(); // Ground to replace.
            BlockState attemptedState = provider.getState(rand, pos); // Ground to maybe replace with.
            if (context.level().isStateAtPosition(pos, state -> {
                if (state.is(AetherTags.Blocks.AETHER_DIRT)) {
                    oldState.set(state);
                    return true;
                } else {
                    return false;
                }
            })) {
                return attemptedState.is(Blocks.PODZOL) ? oldState.get() : attemptedState; // Ground to actually replace with.
            } else {
                return attemptedState;
            }
        });
    }
}
