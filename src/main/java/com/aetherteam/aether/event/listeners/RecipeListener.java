package com.aetherteam.aether.event.listeners;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.event.FreezeEvent;
import com.aetherteam.aether.event.PlacementBanEvent;
import com.aetherteam.aether.event.PlacementConvertEvent;
import com.aetherteam.aether.event.hooks.RecipeHooks;
import com.aetherteam.nitrogen.fabric.events.BlockEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class RecipeListener {

    public static final ResourceLocation LOWER_PRIORITY = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "lower_priority");

    /**
     * @see Aether#eventSetup()
     */
    public static void listen() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> RecipeListener.checkBanned(player, world, hitResult.getBlockPos(), hitResult.getDirection(), hand));
        BlockEvents.NEIGHBOR_UPDATE.register((level, pos, state, notifiedSides, forceRedstoneUpdate, isCancelled) -> onNeighborNotified(level, pos));
        FreezeEvent.BLOCK_FREEZE.register(RecipeListener::onBlockFreeze);
        PlacementConvertEvent.EVENT.addPhaseOrdering(Event.DEFAULT_PHASE, LOWER_PRIORITY);
        PlacementConvertEvent.EVENT.register(LOWER_PRIORITY, RecipeListener::onConvert);
        PlacementBanEvent.SPAWN_PARTICLES.addPhaseOrdering(Event.DEFAULT_PHASE, LOWER_PRIORITY);
        PlacementBanEvent.SPAWN_PARTICLES.register(LOWER_PRIORITY, RecipeListener::onBanned);
    }

    /**
     * @see RecipeHooks#checkInteractionBanned(Player, Level, BlockPos, Direction, ItemStack, BlockState, boolean)
     */
    public static InteractionResult checkBanned(Player player, Level level, BlockPos blockPos, Direction direction, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.isEmpty()) {
            itemStack = player.getItemInHand(interactionHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        }
        BlockState blockState = level.getBlockState(blockPos);
        return RecipeHooks.checkInteractionBanned(player, level, blockPos, direction, itemStack, blockState, !player.getItemInHand(interactionHand).isEmpty());
    }

    /**
     * @see RecipeHooks#checkExistenceBanned(LevelAccessor, BlockPos)
     */
    public static void onNeighborNotified(Level levelAccessor, BlockPos blockPos) {
        RecipeHooks.checkExistenceBanned(levelAccessor, blockPos);
        RecipeHooks.sendIcestoneFreezableUpdateEvent(levelAccessor, blockPos);
    }

    /**
     * @see RecipeHooks#preventBlockFreezing(LevelAccessor, BlockPos, BlockPos)
     */
    public static void onBlockFreeze(FreezeEvent.FreezeFromBlock event) {
        LevelAccessor level = event.getLevel();
        BlockPos sourcePos = event.getSourcePos();
        BlockPos pos = event.getPos();
        if (RecipeHooks.preventBlockFreezing(level, sourcePos, pos)) {
            event.setCanceled(true);
        }
    }

    /**
     * @see RecipeHooks#banOrConvert(LevelAccessor, BlockPos)
     */
    public static void onConvert(PlacementConvertEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        if (!event.isCanceled()) {
            RecipeHooks.banOrConvert(levelAccessor, blockPos);
        }
    }

    /**
     * @see RecipeHooks#banOrConvert(LevelAccessor, BlockPos)
     */
    public static void onBanned(PlacementBanEvent.SpawnParticles event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos blockPos = event.getPos();
        if (!event.isCanceled()) {
            RecipeHooks.banOrConvert(levelAccessor, blockPos);
        }
    }
}
